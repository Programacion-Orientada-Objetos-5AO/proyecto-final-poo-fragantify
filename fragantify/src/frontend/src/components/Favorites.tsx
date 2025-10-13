import { useMemo } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Badge } from "./ui/badge";
import { Heart, Trash2, GitCompareArrows, ShoppingCart, RefreshCcw } from "lucide-react";
import { ImageWithFallback } from "./figma/ImageWithFallback";
import { useFragrances } from "../context/FragranceContext";
import { normalizePrice } from "../utils/prices";

export function Favorites() {
  const { favorites, toggleFavorite, addToCompare, isInCompare, getById, refresh, loading } = useFragrances();

  const favoritePerfumes = useMemo(() => favorites
    .map((id) => getById(id))
    .filter((item): item is NonNullable<ReturnType<typeof getById>> => Boolean(item)),
  [favorites, getById]);

  const stats = useMemo(() => {
    if (favoritePerfumes.length === 0) {
      return { totalFavorites: 0, averagePrice: 0, totalValue: 0 };
    }
    const prices = favoritePerfumes
      .map((item) => normalizePrice(item.price))
      .filter((value): value is number => value !== null);

    const totalValue = prices.reduce((sum, value) => sum + value, 0);
    const averagePrice = prices.length > 0 ? Math.round(totalValue / prices.length) : 0;

    return {
      totalFavorites: favoritePerfumes.length,
      averagePrice,
      totalValue: Math.round(totalValue),
    };
  }, [favoritePerfumes]);

  return (
    <div className="space-y-8">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">Mis favoritos</h1>
          <p className="text-muted-foreground">Tus fragancias guardadas desde la API.</p>
        </div>
        <Button variant="outline" onClick={refresh} disabled={loading}>
          <RefreshCcw className={`h-4 w-4 mr-2 ${loading ? "animate-spin" : ""}`} />
          Actualizar
        </Button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total favoritos</CardTitle>
            <Heart className="h-4 w-4 text-red-500" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{stats.totalFavorites}</div>
            <p className="text-xs text-muted-foreground">Fragancias guardadas</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Precio promedio</CardTitle>
            <ShoppingCart className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">${stats.averagePrice}</div>
            <p className="text-xs text-muted-foreground">Por fragancia</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Valor total estimado</CardTitle>
            <ShoppingCart className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">${stats.totalValue}</div>
            <p className="text-xs text-muted-foreground">Suma de precios</p>
          </CardContent>
        </Card>
      </div>

      {favoritePerfumes.length === 0 ? (
        <Card>
          <CardHeader>
            <CardTitle>No tienes favoritos aun</CardTitle>
            <CardDescription>
              Explora el catalogo y marca las fragancias que te gusten para verlas aqui.
            </CardDescription>
          </CardHeader>
          <CardContent>
            <Button onClick={() => refresh()}>
              <Heart className="h-4 w-4 mr-2" />
              Explorar catalogo
            </Button>
          </CardContent>
        </Card>
      ) : (
        <div className="grid gap-6">
          {favoritePerfumes.map((perfume) => (
            <Card key={perfume.id} className="hover:shadow-md transition-shadow bg-black border border-[#d4af37]/20">
              <CardContent className="p-6">
                <div className="flex flex-col md:flex-row gap-6">
                  <div className="w-32 h-32 overflow-hidden rounded-lg bg-muted flex-shrink-0 mx-auto md:mx-0">
                    <ImageWithFallback
                      src={perfume.imageUrl}
                      fallbackUrls={perfume.imageFallbacks}
                      alt={perfume.name}
                      className="w-full h-full object-cover"
                    />
                  </div>

                  <div className="flex-1 space-y-4">
                    <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
                      <div>
                        <h3 className="text-xl font-semibold text-white">{perfume.name}</h3>
                        <p className="text-muted-foreground">{perfume.brand}</p>
                      </div>
                      <div className="flex items-center gap-2">
                        <Badge variant="outline" className="text-white border-[#d4af37]">
                          {perfume.gender ?? "Unisex"}
                        </Badge>
                        {perfume.longevity && (
                          <Badge variant="outline" className="text-white border-[#d4af37]">
                            Longevidad: {perfume.longevity}
                          </Badge>
                        )}
                      </div>
                    </div>

                    <div className="flex flex-wrap gap-2">
                      {(perfume.mainAccords ?? perfume.generalNotes ?? []).slice(0, 5).map((accord) => (
                        <Badge key={accord} variant="secondary" className="bg-[#1e293b] text-white">
                          {accord}
                        </Badge>
                      ))}
                    </div>

                    <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
                      <span className="text-2xl font-bold text-[#d4af37]">{perfume.price ?? "Precio no disponible"}</span>
                      <div className="flex items-center gap-3">
                        <Button variant="outline" onClick={() => addToCompare(perfume.id)}>
                          <GitCompareArrows className="h-4 w-4 mr-2" />
                          {isInCompare(perfume.id) ? "En comparacion" : "Comparar"}
                        </Button>
                        <Button variant="outline" onClick={() => toggleFavorite(perfume.id)}>
                          <Trash2 className="h-4 w-4 mr-2" />
                          Quitar
                        </Button>
                      </div>
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
}
