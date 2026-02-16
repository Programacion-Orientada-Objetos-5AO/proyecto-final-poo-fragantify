import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Badge } from "./ui/badge";
import { Separator } from "./ui/separator";
import { 
  X, 
  Star, 
  Heart, 
  ShoppingCart,
  Plus,
  BarChart3,
  TrendingUp,
  Palette
} from "lucide-react";
import { ImageWithFallback } from "./figma/ImageWithFallback";

const comparedPerfumes = [
  {
    id: 1,
    name: "Dior Sauvage",
    brand: "Dior",
    type: "Eau de Parfum",
    price: 89,
    rating: 4.8,
    reviews: 324,
    family: "Amaderada",
    longevity: "8-10 horas",
    sillage: "Moderado",
    seasonality: "Otoño/Invierno",
    occasion: "Casual/Formal",
    topNotes: ["Bergamota", "Pimienta"],
    middleNotes: ["Geranio", "Lavanda", "Elemi"],
    baseNotes: ["Sándalo", "Cedro", "Patchouli"],
    image: "https://images.unsplash.com/photo-1647507653704-bde7f2d6dbf0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJmdW1lJTIwYm90dGxlJTIwbW9kZXJuJTIwbWluaW1hbGlzdHxlbnwxfHx8fDE3NTgxMjMzMDB8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    isFavorite: true,
    pros: ["Versatilidad", "Longevidad", "Proyección"],
    cons: ["Precio", "Muy común"]
  },
  {
    id: 2,
    name: "Versace Eros",
    brand: "Versace",
    type: "Eau de Toilette", 
    price: 65,
    rating: 4.4,
    reviews: 298,
    family: "Fresca",
    longevity: "4-6 horas",
    sillage: "Fuerte",
    seasonality: "Primavera/Verano",
    occasion: "Casual/Nocturno",
    topNotes: ["Menta", "Manzana verde", "Limón"],
    middleNotes: ["Ambroxan", "Geranio"],
    baseNotes: ["Vainilla", "Cedro", "Sándalo"],
    image: "https://images.unsplash.com/photo-1734647543247-5ee8bf6f2f3f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsdXh1cnklMjBwZXJmdW1lJTIwY29sbGVjdGlvbiUyMGRpc3BsYXl8ZW58MXx8fHwxNzU4MTIzMzAxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    isFavorite: false,
    pros: ["Precio accesible", "Proyección fuerte", "Atractivo"],
    cons: ["Poca longevidad", "Muy sintético"]
  }
];

const comparisonCategories = [
  { key: "price", label: "Precio", type: "price" },
  { key: "rating", label: "Calificación", type: "rating" },
  { key: "family", label: "Familia Olfativa", type: "text" },
  { key: "longevity", label: "Longevidad", type: "text" },
  { key: "sillage", label: "Sillage", type: "text" },
  { key: "seasonality", label: "Estacionalidad", type: "text" },
  { key: "occasion", label: "Ocasión", type: "text" }
];

export function Compare() {
  const [selectedPerfumes, setSelectedPerfumes] = useState(comparedPerfumes);

  const removePerfume = (perfumeId: number) => {
    setSelectedPerfumes(prev => prev.filter(p => p.id !== perfumeId));
  };

  const addToFavorites = (perfumeId: number) => {
    console.log(`Add perfume ${perfumeId} to favorites`);
  };

  const Winner = ({ children }: { children: React.ReactNode }) => (
    <div className="relative">
      {children}
      <div className="absolute -top-2 -right-2">
        <Badge className="bg-green-500 text-white">
          Mejor
        </Badge>
      </div>
    </div>
  );

  const getBetterValue = (perfume1: any, perfume2: any, key: string) => {
    if (key === 'price') return perfume1.price < perfume2.price ? perfume1.id : perfume2.id;
    if (key === 'rating') return perfume1.rating > perfume2.rating ? perfume1.id : perfume2.id;
    return null;
  };

  return (
    <div className="space-y-8">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-bold">Comparar Perfumes</h1>
        <p className="text-muted-foreground">
          Compara hasta 3 fragancias lado a lado para encontrar la perfecta para ti
        </p>
      </div>

      {selectedPerfumes.length === 0 ? (
        <Card>
          <CardHeader>
            <CardTitle>No hay perfumes para comparar</CardTitle>
            <CardDescription>
              Agrega perfumes desde el catálogo para comenzar a compararlos
            </CardDescription>
          </CardHeader>
          <CardContent>
            <Button>
              <Plus className="h-4 w-4 mr-2" />
              Agregar Perfumes
            </Button>
          </CardContent>
        </Card>
      ) : (
        <div className="space-y-6">
          {/* Add More Button */}
          {selectedPerfumes.length < 3 && (
            <Card className="border-dashed">
              <CardContent className="flex items-center justify-center py-8">
                <Button variant="outline">
                  <Plus className="h-4 w-4 mr-2" />
                  Agregar Otro Perfume ({selectedPerfumes.length}/3)
                </Button>
              </CardContent>
            </Card>
          )}

          {/* Comparison Grid */}
          <div className="grid gap-6" style={{ gridTemplateColumns: `250px repeat(${selectedPerfumes.length}, 1fr)` }}>
            {/* Headers */}
            <div className="font-medium text-muted-foreground"></div>
            {selectedPerfumes.map((perfume) => (
              <Card key={perfume.id} className="relative">
                <CardContent className="p-6 text-center">
                  <Button
                    variant="ghost"
                    size="sm"
                    onClick={() => removePerfume(perfume.id)}
                    className="absolute top-2 right-2 p-1 h-auto"
                  >
                    <X className="h-4 w-4" />
                  </Button>
                  
                  <div className="w-24 h-24 mx-auto mb-4 overflow-hidden rounded-lg bg-muted">
                    <ImageWithFallback
                      src={perfume.image}
                      alt={perfume.name}
                      className="w-full h-full object-cover"
                    />
                  </div>
                  
                  <h3 className="font-semibold mb-1">{perfume.name}</h3>
                  <p className="text-sm text-muted-foreground mb-3">{perfume.brand}</p>
                  
                  <div className="flex justify-center gap-2">
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={() => addToFavorites(perfume.id)}
                    >
                      <Heart 
                        className={`h-4 w-4 ${
                          perfume.isFavorite 
                            ? 'fill-red-500 text-red-500' 
                            : 'text-muted-foreground'
                        }`} 
                      />
                    </Button>
                    <Button size="sm" variant="outline">
                      <ShoppingCart className="h-4 w-4" />
                    </Button>
                  </div>
                </CardContent>
              </Card>
            ))}

            {/* Comparison Rows */}
            {comparisonCategories.map((category) => (
              <>
                <div key={`label-${category.key}`} className="font-medium py-4 border-t">
                  {category.label}
                </div>
                {selectedPerfumes.map((perfume) => {
                  const isWinner = selectedPerfumes.length === 2 && 
                    getBetterValue(selectedPerfumes[0], selectedPerfumes[1], category.key) === perfume.id;
                  
                  const content = (
                    <div className="py-4 border-t">
                      {category.type === "price" && `$${perfume[category.key as keyof typeof perfume]}`}
                      {category.type === "rating" && (
                        <div className="flex items-center justify-center gap-1">
                          <Star className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                          <span>{perfume[category.key as keyof typeof perfume]}</span>
                        </div>
                      )}
                      {category.type === "text" && perfume[category.key as keyof typeof perfume]}
                    </div>
                  );
                  
                  return isWinner ? (
                    <Winner key={`${perfume.id}-${category.key}`}>
                      {content}
                    </Winner>
                  ) : (
                    <div key={`${perfume.id}-${category.key}`}>
                      {content}
                    </div>
                  );
                })}
              </>
            ))}
          </div>

          {/* Detailed Comparison */}
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
            {selectedPerfumes.map((perfume) => (
              <Card key={`detail-${perfume.id}`}>
                <CardHeader>
                  <CardTitle className="flex items-center gap-3">
                    <div className="w-12 h-12 overflow-hidden rounded-lg bg-muted">
                      <ImageWithFallback
                        src={perfume.image}
                        alt={perfume.name}
                        className="w-full h-full object-cover"
                      />
                    </div>
                    <div>
                      <h3>{perfume.name}</h3>
                      <p className="text-sm text-muted-foreground font-normal">{perfume.brand}</p>
                    </div>
                  </CardTitle>
                </CardHeader>
                <CardContent className="space-y-4">
                  {/* Notes Pyramid */}
                  <div>
                    <h4 className="font-medium mb-3 flex items-center gap-2">
                      <Palette className="h-4 w-4" />
                      Pirámide Olfativa
                    </h4>
                    <div className="space-y-3">
                      <div>
                        <span className="text-sm font-medium">Notas de Salida:</span>
                        <div className="flex flex-wrap gap-1 mt-1">
                          {perfume.topNotes.map((note, i) => (
                            <Badge key={i} variant="outline" className="text-xs">
                              {note}
                            </Badge>
                          ))}
                        </div>
                      </div>
                      <div>
                        <span className="text-sm font-medium">Notas de Corazón:</span>
                        <div className="flex flex-wrap gap-1 mt-1">
                          {perfume.middleNotes.map((note, i) => (
                            <Badge key={i} variant="outline" className="text-xs">
                              {note}
                            </Badge>
                          ))}
                        </div>
                      </div>
                      <div>
                        <span className="text-sm font-medium">Notas de Fondo:</span>
                        <div className="flex flex-wrap gap-1 mt-1">
                          {perfume.baseNotes.map((note, i) => (
                            <Badge key={i} variant="outline" className="text-xs">
                              {note}
                            </Badge>
                          ))}
                        </div>
                      </div>
                    </div>
                  </div>

                  <Separator />

                  {/* Pros and Cons */}
                  <div className="grid grid-cols-2 gap-4">
                    <div>
                      <h5 className="font-medium text-green-600 mb-2">Pros</h5>
                      <ul className="space-y-1">
                        {perfume.pros.map((pro, i) => (
                          <li key={i} className="text-sm text-muted-foreground">
                            + {pro}
                          </li>
                        ))}
                      </ul>
                    </div>
                    <div>
                      <h5 className="font-medium text-red-600 mb-2">Contras</h5>
                      <ul className="space-y-1">
                        {perfume.cons.map((con, i) => (
                          <li key={i} className="text-sm text-muted-foreground">
                            - {con}
                          </li>
                        ))}
                      </ul>
                    </div>
                  </div>

                  <Separator />

                  <div className="flex items-center justify-between">
                    <span className="text-2xl font-bold">${perfume.price}</span>
                    <div className="flex gap-2">
                      <Button variant="outline" size="sm">
                        Ver Reseñas
                      </Button>
                      <Button size="sm">
                        Comprar
                      </Button>
                    </div>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>

          {/* Summary */}
          {selectedPerfumes.length === 2 && (
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center gap-2">
                  <TrendingUp className="h-5 w-5" />
                  Resumen de Comparación
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-center">
                  <div className="p-4 bg-muted rounded-lg">
                    <BarChart3 className="h-8 w-8 mx-auto mb-2 text-blue-500" />
                    <h4 className="font-medium">Mejor Valor</h4>
                    <p className="text-sm text-muted-foreground">
                      {selectedPerfumes[0].price < selectedPerfumes[1].price 
                        ? selectedPerfumes[0].name 
                        : selectedPerfumes[1].name}
                    </p>
                  </div>
                  <div className="p-4 bg-muted rounded-lg">
                    <Star className="h-8 w-8 mx-auto mb-2 text-yellow-500" />
                    <h4 className="font-medium">Mejor Calificado</h4>
                    <p className="text-sm text-muted-foreground">
                      {selectedPerfumes[0].rating > selectedPerfumes[1].rating 
                        ? selectedPerfumes[0].name 
                        : selectedPerfumes[1].name}
                    </p>
                  </div>
                  <div className="p-4 bg-muted rounded-lg">
                    <Heart className="h-8 w-8 mx-auto mb-2 text-red-500" />
                    <h4 className="font-medium">Más Popular</h4>
                    <p className="text-sm text-muted-foreground">
                      {selectedPerfumes[0].reviews > selectedPerfumes[1].reviews 
                        ? selectedPerfumes[0].name 
                        : selectedPerfumes[1].name}
                    </p>
                  </div>
                </div>
              </CardContent>
            </Card>
          )}
        </div>
      )}
    </div>
  );
}