import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Badge } from "./ui/badge";
import { Progress } from "./ui/progress";
import { 
  Package, 
  Star, 
  TrendingUp, 
  Calendar,
  Target,
  Plus,
  Eye,
  ShoppingCart,
  BarChart3,
  Lightbulb,
  Award
} from "lucide-react";
import { ImageWithFallback } from "./figma/ImageWithFallback";

const myPerfumes = [
  {
    id: 1,
    name: "Dior Sauvage",
    brand: "Dior",
    type: "Eau de Parfum",
    family: "Amaderada",
    purchaseDate: "2024-01-15",
    rating: 5,
    notes: "Perfect para uso diario",
    image: "https://images.unsplash.com/photo-1647507653704-bde7f2d6dbf0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJmdW1lJTIwYm90dGxlJTIwbW9kZXJuJTIwbWluaW1hbGlzdHxlbnwxfHx8fDE3NTgxMjMzMDB8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    usageLevel: 75
  },
  {
    id: 2,
    name: "Tom Ford Black Orchid",
    brand: "Tom Ford",
    type: "Eau de Parfum",
    family: "Oriental",
    purchaseDate: "2023-11-20",
    rating: 4,
    notes: "Intenso para ocasiones especiales",
    image: "https://images.unsplash.com/photo-1620057958829-615f3ddbe922?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHhwZXJmdW1lJTIwaW5ncmVkaWVudHMlMjBib3RhbmljYWx8ZW58MXx8fHwxNzU4MTIzMzAxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    usageLevel: 40
  },
  {
    id: 3,
    name: "Versace Eros",
    brand: "Versace",
    type: "Eau de Toilette",
    family: "Fresca",
    purchaseDate: "2024-02-10",
    rating: 4,
    notes: "Excelente para verano",
    image: "https://images.unsplash.com/photo-1734647543247-5ee8bf6f2f3f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsdXh1cnklMjBwZXJmdW1lJTIwY29sbGVjdGlvbiUyMGRpc3BsYXl8ZW58MXx8fHwxNzU4MTIzMzAxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    usageLevel: 90
  }
];

const familyProgress = {
  "Amaderada": { owned: 1, recommended: 3, progress: 33 },
  "Oriental": { owned: 1, recommended: 2, progress: 50 },
  "Fresca": { owned: 1, recommended: 4, progress: 25 },
  "Floral": { owned: 0, recommended: 3, progress: 0 },
  "Frutal": { owned: 0, recommended: 2, progress: 0 },
  "Especiada": { owned: 0, recommended: 2, progress: 0 }
};

const recommendedForCollection = [
  {
    id: 10,
    name: "Chanel Bleu de Chanel",
    brand: "Chanel",
    family: "Amaderada",
    reason: "Completa tu colección amaderada con elegancia",
    match: 92,
    price: 130,
    image: "https://images.unsplash.com/photo-1647507653704-bde7f2d6dbf0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJmdW1lJTIwYm90dGxlJTIwbW9kZXJuJTIwbWluaW1hbGlzdHxlbnwxfHx8fDE3NTgxMjMzMDB8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
  },
  {
    id: 11,
    name: "YSL Libre",
    brand: "Yves Saint Laurent",
    family: "Floral",
    reason: "Te falta explorar las florales modernas",
    match: 88,
    price: 120,
    image: "https://images.unsplash.com/photo-1734647543247-5ee8bf6f2f3f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsdXh1cnklMjBwZXJmdW1lJTIwY29sbGVjdGlvbiUyMGRpc3BsYXl8ZW58MXx8fHwxNzU4MTIzMzAxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
  },
  {
    id: 12,
    name: "Creed Aventus",
    brand: "Creed",
    family: "Frutal",
    reason: "El rey de las frutales que necesitas",
    match: 95,
    price: 350,
    image: "https://images.unsplash.com/photo-1620057958829-615f3ddbe922?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHhwZXJmdW1lJTIwaW5ncmVkaWVudHMlMjBib3RhbmljYWx8ZW58MXx8fHwxNzU4MTIzMzAxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
  }
];

const achievements = [
  { name: "Coleccionista Principiante", description: "Tienes 3+ perfumes", earned: true },
  { name: "Explorador Olfativo", description: "3+ familias diferentes", earned: true },
  { name: "Crítico Activo", description: "10+ reseñas escritas", earned: false },
  { name: "Maestro de Aromas", description: "5+ familias olfativas", earned: false }
];

export function MyCollection() {
  const [selectedTab, setSelectedTab] = useState<"collection" | "recommendations" | "progress">("collection");

  const collectionStats = {
    totalPerfumes: myPerfumes.length,
    familiesExplored: Object.values(familyProgress).filter(f => f.owned > 0).length,
    averageRating: myPerfumes.reduce((sum, p) => sum + p.rating, 0) / myPerfumes.length,
    totalInvestment: 284 // Mock data
  };

  return (
    <div className="space-y-8">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">Mi Colección</h1>
          <p className="text-muted-foreground">
            Gestiona tus perfumes y descubre lo que te falta
          </p>
        </div>
        <Button>
          <Plus className="h-4 w-4 mr-2" />
          Agregar Perfume
        </Button>
      </div>

      {/* Stats Overview */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <Card className="bg-gradient-to-br from-primary to-chart-2 text-white">
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-primary-foreground/80 text-sm">Total Perfumes</p>
                <p className="text-2xl font-bold">{collectionStats.totalPerfumes}</p>
              </div>
              <Package className="h-8 w-8 text-primary-foreground/80" />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-muted-foreground text-sm">Familias Exploradas</p>
                <p className="text-2xl font-bold">{collectionStats.familiesExplored}/6</p>
              </div>
              <Target className="h-8 w-8 text-muted-foreground" />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-muted-foreground text-sm">Rating Promedio</p>
                <p className="text-2xl font-bold">{collectionStats.averageRating.toFixed(1)}</p>
              </div>
              <Star className="h-8 w-8 text-yellow-500" />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-muted-foreground text-sm">Inversión Total</p>
                <p className="text-2xl font-bold">${collectionStats.totalInvestment}</p>
              </div>
              <TrendingUp className="h-8 w-8 text-green-500" />
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Navigation Tabs */}
      <div className="flex space-x-1 bg-muted p-1 rounded-lg w-fit">
        <button
          onClick={() => setSelectedTab("collection")}
          className={`px-4 py-2 rounded-md text-sm font-medium transition-colors ${
            selectedTab === "collection" 
              ? "bg-background text-foreground shadow-sm" 
              : "text-muted-foreground hover:text-foreground"
          }`}
        >
          Mi Colección
        </button>
        <button
          onClick={() => setSelectedTab("recommendations")}
          className={`px-4 py-2 rounded-md text-sm font-medium transition-colors ${
            selectedTab === "recommendations" 
              ? "bg-background text-foreground shadow-sm" 
              : "text-muted-foreground hover:text-foreground"
          }`}
        >
          Recomendaciones
        </button>
        <button
          onClick={() => setSelectedTab("progress")}
          className={`px-4 py-2 rounded-md text-sm font-medium transition-colors ${
            selectedTab === "progress" 
              ? "bg-background text-foreground shadow-sm" 
              : "text-muted-foreground hover:text-foreground"
          }`}
        >
          Progreso
        </button>
      </div>

      {/* Content based on selected tab */}
      {selectedTab === "collection" && (
        <div className="grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 gap-6">
          {myPerfumes.map((perfume) => (
            <Card key={perfume.id} className="group hover:shadow-lg transition-shadow">
              <CardContent className="p-6">
                <div className="space-y-4">
                  <div className="flex gap-4">
                    <div className="w-16 h-16 overflow-hidden rounded-lg bg-muted flex-shrink-0">
                      <ImageWithFallback
                        src={perfume.image}
                        alt={perfume.name}
                        className="w-full h-full object-cover"
                      />
                    </div>
                    <div className="flex-1">
                      <h3 className="font-semibold">{perfume.name}</h3>
                      <p className="text-sm text-muted-foreground">{perfume.brand}</p>
                      <Badge variant="secondary" className="mt-1">{perfume.family}</Badge>
                    </div>
                  </div>

                  <div className="space-y-2">
                    <div className="flex justify-between text-sm">
                      <span>Nivel de uso</span>
                      <span>{perfume.usageLevel}%</span>
                    </div>
                    <Progress value={perfume.usageLevel} className="h-2" />
                  </div>

                  <div className="flex items-center gap-1">
                    <span className="text-sm text-muted-foreground">Mi rating:</span>
                    {[...Array(5)].map((_, i) => (
                      <Star 
                        key={i} 
                        className={`h-4 w-4 ${
                          i < perfume.rating 
                            ? 'fill-yellow-400 text-yellow-400' 
                            : 'text-gray-300'
                        }`} 
                      />
                    ))}
                  </div>

                  {perfume.notes && (
                    <div className="bg-muted/50 p-3 rounded-lg">
                      <p className="text-sm">{perfume.notes}</p>
                    </div>
                  )}

                  <div className="flex items-center justify-between text-xs text-muted-foreground">
                    <span>Agregado: {new Date(perfume.purchaseDate).toLocaleDateString('es-ES')}</span>
                    <Button variant="ghost" size="sm">
                      <Eye className="h-4 w-4" />
                    </Button>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}

      {selectedTab === "recommendations" && (
        <div className="space-y-6">
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Lightbulb className="h-5 w-5 text-yellow-500" />
                Recomendaciones Inteligentes
              </CardTitle>
              <CardDescription>
                Basadas en los gaps en tu colección y tus preferencias
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              {recommendedForCollection.map((rec) => (
                <div key={rec.id} className="flex items-center gap-4 p-4 rounded-lg border bg-muted/30">
                  <div className="w-16 h-16 rounded-lg overflow-hidden">
                    <ImageWithFallback
                      src={rec.image}
                      alt={rec.name}
                      className="w-full h-full object-cover"
                    />
                  </div>
                  <div className="flex-1">
                    <div className="flex items-center justify-between mb-2">
                      <h4 className="font-medium">{rec.name}</h4>
                      <Badge variant="secondary" className="bg-green-100 text-green-800">
                        {rec.match}% Match
                      </Badge>
                    </div>
                    <p className="text-sm text-muted-foreground mb-1">{rec.brand} • ${rec.price}</p>
                    <p className="text-xs text-muted-foreground">{rec.reason}</p>
                  </div>
                  <div className="flex gap-2">
                    <Button size="sm" variant="outline">
                      <Eye className="h-4 w-4" />
                    </Button>
                    <Button size="sm">
                      <ShoppingCart className="h-4 w-4" />
                    </Button>
                  </div>
                </div>
              ))}
            </CardContent>
          </Card>
        </div>
      )}

      {selectedTab === "progress" && (
        <div className="space-y-6">
          {/* Family Progress */}
          <Card>
            <CardHeader>
              <CardTitle>Progreso por Familia Olfativa</CardTitle>
              <CardDescription>
                Diversifica tu colección explorando diferentes familias
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              {Object.entries(familyProgress).map(([family, data]) => (
                <div key={family} className="space-y-2">
                  <div className="flex items-center justify-between">
                    <span className="font-medium">{family}</span>
                    <span className="text-sm text-muted-foreground">
                      {data.owned}/{data.recommended}
                    </span>
                  </div>
                  <Progress value={data.progress} className="h-2" />
                </div>
              ))}
            </CardContent>
          </Card>

          {/* Achievements */}
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Award className="h-5 w-5 text-yellow-500" />
                Logros y Metas
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {achievements.map((achievement, index) => (
                  <div 
                    key={index}
                    className="p-4 rounded-lg border bg-black border-gray-700"
                  >
                    <div className="flex items-center gap-3">
                      <div className={`w-8 h-8 rounded-full flex items-center justify-center ${
                        achievement.earned 
                          ? 'bg-[#d4af37] text-black' 
                          : 'bg-[#1e293b] text-gray-400'
                      }`}>
                        <Award className="h-4 w-4" />
                      </div>
                      <div>
                        <p className="font-medium text-white">{achievement.name}</p>
                        <p className="text-sm text-gray-300">{achievement.description}</p>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>

          {/* Quick Actions */}
          <Card>
            <CardHeader>
              <CardTitle>Acciones Recomendadas</CardTitle>
            </CardHeader>
            <CardContent className="flex gap-4">
              <Button>
                <Target className="h-4 w-4 mr-2" />
                Explorar Familias Faltantes
              </Button>
              <Button variant="outline">
                <BarChart3 className="h-4 w-4 mr-2" />
                Comparar con mi Colección
              </Button>
              <Button variant="outline">
                <Plus className="h-4 w-4 mr-2" />
                Agregar Wishlist
              </Button>
            </CardContent>
          </Card>
        </div>
      )}
    </div>
  );
}