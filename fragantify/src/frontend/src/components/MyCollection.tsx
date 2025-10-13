import { useMemo } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Badge } from "./ui/badge";
import { Progress } from "./ui/progress";
import { Package, TrendingUp, Award, GitCompareArrows, Heart } from "lucide-react";
import { ImageWithFallback } from "./figma/ImageWithFallback";
import { useFragrances } from "../context/FragranceContext";
import { normalizePrice } from "../utils/prices";

export function MyCollection() {
  const { fragrances, favorites, toggleFavorite, addToCompare, isInCompare, getById } = useFragrances();

  const ownedPerfumes = useMemo(() => favorites
    .map((id) => getById(id))
    .filter((item): item is NonNullable<ReturnType<typeof getById>> => Boolean(item)),
  [favorites, getById]);

  const suggestions = useMemo(() => {
    const candidates = fragrances.filter((item) => !favorites.includes(item.id));
    return candidates.slice(0, 3);
  }, [fragrances, favorites]);

  const stats = useMemo(() => {
    const priceValues = ownedPerfumes
      .map((item) => normalizePrice(item.price))
      .filter((value): value is number => value !== null);

    const totalValue = priceValues.reduce((sum, value) => sum + value, 0);
    const averagePrice = priceValues.length > 0 ? Math.round(totalValue / priceValues.length) : 0;

    const familyCounts = new Map<string, number>();
    ownedPerfumes.forEach((item) => {
      (item.mainAccords ?? item.generalNotes ?? []).slice(0, 1).forEach((accord) => {
        const current = familyCounts.get(accord) ?? 0;
        familyCounts.set(accord, current + 1);
      });
    });

    return {
      total: ownedPerfumes.length,
      averagePrice,
      totalValue: Math.round(totalValue),
      families: Array.from(familyCounts.entries()).sort((a, b) => b[1] - a[1]),
    };
  }, [ownedPerfumes]);

  return (
    <div className="space-y-8">
      <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold">Mi coleccion</h1>
          <p className="text-muted-foreground">Fragancias que has marcado como favoritas.</p>
        </div>
        <Badge variant="outline" className="text-white border-[#d4af37]">
          <Package className="h-4 w-4 mr-2" /> {stats.total} fragancias
        </Badge>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card>
          <CardHeader>
            <CardTitle>Total de fragancias</CardTitle>
            <CardDescription>Numero de perfumes guardados</CardDescription>
          </CardHeader>
          <CardContent className="text-3xl font-bold">{stats.total}</CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>Precio promedio</CardTitle>
            <CardDescription>Basado en datos disponibles</CardDescription>
          </CardHeader>
          <CardContent className="text-3xl font-bold">${stats.averagePrice}</CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>Valor aproximado</CardTitle>
            <CardDescription>Suma estimada</CardDescription>
          </CardHeader>
          <CardContent className="text-3xl font-bold">${stats.totalValue}</CardContent>
        </Card>
      </div>

      {stats.families.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <TrendingUp className="h-5 w-5" /> Acordes mas repetidos
            </CardTitle>
            <CardDescription>Identifica la tendencia de tu coleccion</CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            {stats.families.map(([family, count]) => {
              const progress = Math.min(100, Math.round((count / stats.total) * 100));
              return (
                <div key={family} className="space-y-2">
                  <div className="flex items-center justify-between">
                    <span>{family}</span>
                    <span className="text-sm text-muted-foreground">{count} fragancias</span>
                  </div>
                  <Progress value={progress} className="h-2" />
                </div>
              );
            })}
          </CardContent>
        </Card>
      )}

      {ownedPerfumes.length === 0 ? (
        <Card>
          <CardHeader>
            <CardTitle>No hay fragancias en tu coleccion</CardTitle>
            <CardDescription>Marca algunas fragancias como favoritas para verlas aqui.</CardDescription>
          </CardHeader>
          <CardContent>
            <Button variant="outline">
              <Heart className="h-4 w-4 mr-2" /> Explorar catalogo
            </Button>
          </CardContent>
        </Card>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
          {ownedPerfumes.map((perfume) => (
            <Card key={perfume.id} className="bg-black border border-[#d4af37]/20">
              <CardContent className="p-6 space-y-4">
                <div className="aspect-square rounded-lg overflow-hidden border border-[#d4af37]/30">
                  <ImageWithFallback
                    src={perfume.imageUrl}
                    fallbackUrls={perfume.imageFallbacks}
                    alt={perfume.name}
                    className="w-full h-full object-cover"
                  />
                </div>
                <div>
                  <h3 className="text-xl font-semibold text-white">{perfume.name}</h3>
                  <p className="text-sm text-muted-foreground">{perfume.brand}</p>
                </div>
                <div className="flex flex-wrap gap-2">
                  {(perfume.mainAccords ?? perfume.generalNotes ?? []).slice(0, 4).map((accord) => (
                    <Badge key={accord} variant="secondary" className="bg-[#1e293b] text-white">
                      {accord}
                    </Badge>
                  ))}
                </div>
                <div className="flex items-center justify-between">
                  <span className="text-lg font-semibold text-[#d4af37]">{perfume.price ?? "Precio no disponible"}</span>
                  <div className="flex gap-2">
                    <Button variant="outline" onClick={() => addToCompare(perfume.id)}>
                      <GitCompareArrows className="h-4 w-4 mr-2" />
                      {isInCompare(perfume.id) ? "En comparacion" : "Comparar"}
                    </Button>
                    <Button variant="outline" onClick={() => toggleFavorite(perfume.id)}>
                      <Heart className="h-4 w-4 mr-2" /> Quitar
                    </Button>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}

      {suggestions.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Award className="h-5 w-5" /> Recomendado para tu coleccion
            </CardTitle>
            <CardDescription>Basado en fragancias que aun no guardaste.</CardDescription>
          </CardHeader>
          <CardContent className="grid grid-cols-1 md:grid-cols-3 gap-4">
            {suggestions.map((perfume) => (
              <div key={perfume.id} className="p-4 rounded-lg border border-[#d4af37]/20 bg-black space-y-4">
                <div className="aspect-square rounded-lg overflow-hidden border border-[#d4af37]/20">
                  <ImageWithFallback
                    src={perfume.imageUrl}
                    fallbackUrls={perfume.imageFallbacks}
                    alt={perfume.name}
                    className="w-full h-full object-cover"
                  />
                </div>
                <div>
                  <h4 className="font-semibold text-white">{perfume.name}</h4>
                  <p className="text-sm text-muted-foreground">{perfume.brand}</p>
                </div>
                <Button onClick={() => toggleFavorite(perfume.id)}>
                  <Heart className="h-4 w-4 mr-2" /> Agregar a favoritos
                </Button>
              </div>
            ))}
          </CardContent>
        </Card>
      )}
    </div>
  );
}
