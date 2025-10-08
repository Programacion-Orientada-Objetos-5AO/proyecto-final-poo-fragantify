import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Badge } from "./ui/badge";
import { 
  Heart, 
  Star, 
  Trash2, 
  ShoppingCart,
  BarChart3,
  Calendar
} from "lucide-react";
import { ImageWithFallback } from "./figma/ImageWithFallback";

const favoritePerfumes = [];

const favoriteStats = {
  totalFavorites: favoritePerfumes.length,
  averagePrice: Math.round(favoritePerfumes.reduce((sum, p) => sum + p.price, 0) / favoritePerfumes.length),
  mostLikedFamily: "Amaderada",
  totalValue: favoritePerfumes.reduce((sum, p) => sum + p.price, 0)
};

export function Favorites() {
  const removeFavorite = (perfumeId: number) => {
    // En una app real, esto removería el perfume de favoritos
    console.log(`Remove perfume ${perfumeId} from favorites`);
  };

  const addToCompare = (perfumeId: number) => {
    // En una app real, esto agregaría el perfume a la comparación
    console.log(`Add perfume ${perfumeId} to compare`);
  };

  return (
    <div className="space-y-8">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-bold">Mis Favoritos</h1>
        <p className="text-muted-foreground">
          Tus fragancias favoritas en un solo lugar
        </p>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Favoritos</CardTitle>
            <Heart className="h-4 w-4 text-red-500" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{favoriteStats.totalFavorites}</div>
            <p className="text-xs text-muted-foreground">Fragancias guardadas</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Precio Promedio</CardTitle>
            <ShoppingCart className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">${favoriteStats.averagePrice}</div>
            <p className="text-xs text-muted-foreground">Por fragancia</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Familia Favorita</CardTitle>
            <Star className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{favoriteStats.mostLikedFamily}</div>
            <p className="text-xs text-muted-foreground">Más seleccionada</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Valor Total</CardTitle>
            <BarChart3 className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">${favoriteStats.totalValue}</div>
            <p className="text-xs text-muted-foreground">Inversión en favoritos</p>
          </CardContent>
        </Card>
      </div>

      {/* Favorites List */}
      {favoritePerfumes.length > 0 ? (
        <div className="space-y-4">
          <div className="flex items-center justify-between">
            <h2 className="text-lg font-semibold">Tus Fragancias Favoritas</h2>
            <Button variant="outline" size="sm">
              <BarChart3 className="h-4 w-4 mr-2" />
              Comparar Todos
            </Button>
          </div>

          <div className="grid gap-6">
            {favoritePerfumes.map((perfume) => (
              <Card key={perfume.id} className="hover:shadow-md transition-shadow">
                <CardContent className="p-6">
                  <div className="flex gap-6">
                    {/* Image */}
                    <div className="w-32 h-32 overflow-hidden rounded-lg bg-muted flex-shrink-0">
                      <ImageWithFallback
                        src={perfume.image}
                        alt={perfume.name}
                        className="w-full h-full object-cover"
                      />
                    </div>

                    {/* Content */}
                    <div className="flex-1 space-y-4">
                      <div className="flex items-start justify-between">
                        <div>
                          <h3 className="text-xl font-semibold">{perfume.name}</h3>
                          <p className="text-muted-foreground">{perfume.brand} • {perfume.type}</p>
                        </div>
                        <div className="flex items-center gap-2">
                          <Button
                            variant="ghost"
                            size="sm"
                            onClick={() => addToCompare(perfume.id)}
                          >
                            <BarChart3 className="h-4 w-4" />
                          </Button>
                          <Button
                            variant="ghost"
                            size="sm"
                            onClick={() => removeFavorite(perfume.id)}
                          >
                            <Trash2 className="h-4 w-4 text-red-500" />
                          </Button>
                        </div>
                      </div>

                      <div className="flex items-center gap-4">
                        <Badge variant="secondary">{perfume.family}</Badge>
                        {!perfume.inStock && (
                          <Badge variant="destructive">Agotado</Badge>
                        )}
                        <div className="flex items-center gap-1">
                          <Calendar className="h-4 w-4 text-muted-foreground" />
                          <span className="text-sm text-muted-foreground">
                            Agregado el {new Date(perfume.dateAdded).toLocaleDateString('es-ES')}
                          </span>
                        </div>
                      </div>

                      <div className="flex items-center gap-6">
                        <div className="flex items-center gap-1">
                          <Star className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                          <span className="font-medium">{perfume.rating}</span>
                          <span className="text-sm text-muted-foreground">
                            ({perfume.reviews} reseñas)
                          </span>
                        </div>
                        
                        <div className="flex items-center gap-1">
                          <span className="text-sm text-muted-foreground">Tu calificación:</span>
                          <div className="flex">
                            {[...Array(5)].map((_, i) => (
                              <Star 
                                key={i} 
                                className={`h-4 w-4 ${
                                  i < perfume.yourRating 
                                    ? 'fill-yellow-400 text-yellow-400' 
                                    : 'text-gray-300'
                                }`} 
                              />
                            ))}
                          </div>
                        </div>
                      </div>

                      {perfume.notes && (
                        <div className="bg-muted p-3 rounded-lg">
                          <p className="text-sm">
                            <span className="font-medium">Tus notas:</span> {perfume.notes}
                          </p>
                        </div>
                      )}

                      <div className="flex items-center justify-between pt-2">
                        <span className="text-2xl font-bold">${perfume.price}</span>
                        <div className="flex gap-3">
                          <Button variant="outline" size="sm">
                            Ver Detalles
                          </Button>
                          <Button size="sm" disabled={!perfume.inStock}>
                            <ShoppingCart className="h-4 w-4 mr-2" />
                            {perfume.inStock ? 'Comprar' : 'Agotado'}
                          </Button>
                        </div>
                      </div>
                    </div>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      ) : (
        <Card>
          <CardHeader>
            <CardTitle>No tienes favoritos aún</CardTitle>
            <CardDescription>
              Comienza a explorar nuestro catálogo y agrega tus fragancias favoritas
            </CardDescription>
          </CardHeader>
          <CardContent>
            <Button>
              <Heart className="h-4 w-4 mr-2" />
              Explorar Catálogo
            </Button>
          </CardContent>
        </Card>
      )}

      {/* Quick Actions */}
      <Card>
        <CardHeader>
          <CardTitle>Acciones Rápidas</CardTitle>
        </CardHeader>
        <CardContent className="flex gap-4">
          <Button variant="outline">
            <BarChart3 className="h-4 w-4 mr-2" />
            Comparar Favoritos
          </Button>
          <Button variant="outline">
            <Star className="h-4 w-4 mr-2" />
            Reseñar Favoritos
          </Button>
          <Button variant="outline">
            <ShoppingCart className="h-4 w-4 mr-2" />
            Lista de Compras
          </Button>
        </CardContent>
      </Card>
    </div>
  );
}