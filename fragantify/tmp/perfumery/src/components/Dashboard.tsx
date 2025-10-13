import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Badge } from "./ui/badge";
import { Button } from "./ui/button";
import { Progress } from "./ui/progress";
import { 
  TrendingUp, 
  Heart, 
  Star, 
  ShoppingBag, 
  Target,
  Sparkles,
  Calendar,
  Eye
} from "lucide-react";
import { ImageWithFallback } from "./figma/ImageWithFallback";

const userStats = {
  perfumesReviewed: 24,
  averageRating: 4.2,
  favoriteFragrances: 12,
  totalSpent: 850,
  daysSinceJoined: 145
};

const recentRecommendations = [
  {
    id: 1,
    name: "Dior Sauvage",
    brand: "Dior",
    match: 95,
    reason: "Basado en tu amor por las notas amaderadas",
    image: "https://images.unsplash.com/photo-1647507653704-bde7f2d6dbf0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJmdW1lJTIwYm90dGxlJTIwbW9kZXJuJTIwbWluaW1hbGlzdHxlbnwxfHx8fDE3NTgxMjMzMDB8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    price: "$89"
  },
  {
    id: 2,
    name: "Chanel No. 5",
    brand: "Chanel",
    match: 87,
    reason: "Perfecto para ocasiones elegantes",
    image: "https://images.unsplash.com/photo-1734647543247-5ee8bf6f2f3f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsdXh1cnklMjBwZXJmdW1lJTIwY29sbGVjdGlvbiUyMGRpc3BsYXl8ZW58MXx8fHwxNzU4MTIzMzAxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    price: "$150"
  }
];

const recentActivity = [
  {
    id: 1,
    action: "Reseñaste",
    perfume: "Tom Ford Black Orchid",
    rating: 5,
    date: "Hace 2 días"
  },
  {
    id: 2,
    action: "Agregaste a favoritos",
    perfume: "Yves Saint Laurent Libre",
    date: "Hace 3 días"
  },
  {
    id: 3,
    action: "Comparaste",
    perfume: "Versace Eros vs Dylan Blue",
    date: "Hace 1 semana"
  }
];

export function Dashboard() {
  return (
    <div className="space-y-8">
      {/* Welcome Section */}
      <div className="bg-gradient-to-r from-primary to-chart-2 rounded-lg p-6 text-white">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-2xl font-bold mb-2">¡Bienvenido de vuelta, Juan!</h1>
            <p className="text-primary-foreground/80">
              Has explorado {userStats.perfumesReviewed} fragancias en los últimos {userStats.daysSinceJoined} días
            </p>
          </div>
          <div className="hidden md:block">
            <Sparkles className="h-16 w-16 text-primary-foreground/60" />
          </div>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Reseñas Escritas</CardTitle>
            <Star className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{userStats.perfumesReviewed}</div>
            <p className="text-xs text-muted-foreground">+3 este mes</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Calificación Promedio</CardTitle>
            <TrendingUp className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{userStats.averageRating}/5</div>
            <p className="text-xs text-muted-foreground">Muy exigente</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Favoritos</CardTitle>
            <Heart className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{userStats.favoriteFragrances}</div>
            <p className="text-xs text-muted-foreground">+2 esta semana</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Gasto Total</CardTitle>
            <ShoppingBag className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">${userStats.totalSpent}</div>
            <p className="text-xs text-muted-foreground">En perfumes</p>
          </CardContent>
        </Card>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Recommendations */}
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Target className="h-5 w-5" />
              Recomendaciones Personalizadas
            </CardTitle>
            <CardDescription>
              Basadas en tus preferencias y reseñas anteriores
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            {recentRecommendations.map((rec) => (
              <div key={rec.id} className="flex items-center gap-4 p-3 rounded-lg border bg-muted/30">
                <div className="w-12 h-12 rounded-lg overflow-hidden">
                  <ImageWithFallback
                    src={rec.image}
                    alt={rec.name}
                    className="w-full h-full object-cover"
                  />
                </div>
                <div className="flex-1">
                  <div className="flex items-center justify-between mb-1">
                    <h4 className="font-medium">{rec.name}</h4>
                    <Badge variant="secondary">{rec.match}% Match</Badge>
                  </div>
                  <p className="text-sm text-muted-foreground mb-1">{rec.brand} • {rec.price}</p>
                  <p className="text-xs text-muted-foreground">{rec.reason}</p>
                </div>
                <Button size="sm" variant="outline">
                  <Eye className="h-4 w-4" />
                </Button>
              </div>
            ))}
            <Button className="w-full" variant="outline">
              Ver Todas las Recomendaciones
            </Button>
          </CardContent>
        </Card>

        {/* Recent Activity */}
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Calendar className="h-5 w-5" />
              Actividad Reciente
            </CardTitle>
            <CardDescription>
              Tu actividad en Fragrantify
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            {recentActivity.map((activity) => (
              <div key={activity.id} className="flex items-center justify-between p-3 rounded-lg border bg-muted/30">
                <div>
                  <p className="font-medium text-sm">
                    {activity.action} <span className="text-muted-foreground">{activity.perfume}</span>
                  </p>
                  {activity.rating && (
                    <div className="flex items-center gap-1 mt-1">
                      {[...Array(activity.rating)].map((_, i) => (
                        <Star key={i} className="h-3 w-3 fill-yellow-400 text-yellow-400" />
                      ))}
                    </div>
                  )}
                </div>
                <span className="text-xs text-muted-foreground">{activity.date}</span>
              </div>
            ))}
            <Button className="w-full" variant="outline">
              Ver Historial Completo
            </Button>
          </CardContent>
        </Card>
      </div>

      {/* Progress Section */}
      <Card>
        <CardHeader>
          <CardTitle>Progreso del Mes</CardTitle>
          <CardDescription>
            Mantén tu racha de exploración de fragancias
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div>
            <div className="flex justify-between text-sm mb-2">
              <span>Reseñas este mes</span>
              <span>3 / 5</span>
            </div>
            <Progress value={60} className="h-2" />
          </div>
          <div>
            <div className="flex justify-between text-sm mb-2">
              <span>Nuevas fragancias probadas</span>
              <span>7 / 10</span>
            </div>
            <Progress value={70} className="h-2" />
          </div>
          <div className="flex items-center gap-2 text-sm text-muted-foreground">
            <Target className="h-4 w-4" />
            <span>¡Estás a 2 reseñas de desbloquear tu insignia mensual!</span>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}