import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Badge } from "./ui/badge";
import { Input } from "./ui/input";
import { Label } from "./ui/label";
import { Slider } from "./ui/slider";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/select";
import { Checkbox } from "./ui/checkbox";
import { 
  Target, 
  DollarSign, 
  Package, 
  Star,
  Sparkles,
  ShoppingCart,
  RefreshCw,
  Heart,
  TrendingUp,
  Gift
} from "lucide-react";
import { ImageWithFallback } from "./figma/ImageWithFallback";

interface PackBuilderForm {
  budget: number;
  quantity: number;
  occasion: string;
  preferences: string[];
  priceRange: "budget" | "mid" | "luxury" | "mixed";
  includeOwned: boolean;
}

const occasions = [
  { value: "daily", label: "Uso Diario" },
  { value: "work", label: "Trabajo/Oficina" },
  { value: "evening", label: "Noche/Salidas" },
  { value: "special", label: "Ocasiones Especiales" },
  { value: "seasonal", label: "Estacional" },
  { value: "versatile", label: "Versátil" }
];

const preferenceOptions = [
  "Amaderada", "Floral", "Oriental", "Fresca", "Frutal", "Especiada"
];

const samplePacks = [
  {
    id: 1,
    name: "Pack Esencial Diario",
    description: "Perfecto para comenzar tu colección",
    totalPrice: 245,
    savings: 55,
    perfumes: [
      {
        name: "Dior Sauvage",
        brand: "Dior",
        price: 89,
        family: "Amaderada",
        rating: 4.8,
        image: "https://images.unsplash.com/photo-1647507653704-bde7f2d6dbf0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJmdW1lJTIwYm90dGxlJTIwbW9kZXJuJTIwbWluaW1hbGlzdHxlbnwxfHx8fDE3NTgxMjMzMDB8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
      },
      {
        name: "Versace Eros",
        brand: "Versace",
        price: 65,
        family: "Fresca",
        rating: 4.4,
        image: "https://images.unsplash.com/photo-1734647543247-5ee8bf6f2f3f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsdXh1cnklMjBwZXJmdW1lJTIwY29sbGVjdGlvbiUyMGRpc3BsYXl8ZW58MXx8fHwxNzU4MTIzMzAxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
      },
      {
        name: "YSL Libre",
        brand: "Yves Saint Laurent",
        price: 120,
        family: "Floral",
        rating: 4.7,
        image: "https://images.unsplash.com/photo-1620057958829-615f3ddbe922?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHhwZXJmdW1lJTIwaW5ncmVkaWVudHMlMjBib3RhbmljYWx8ZW58MXx8fHwxNzU4MTIzMzAxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
      }
    ],
    match: 95,
    badges: ["Mejor Valor", "Versatilidad"]
  },
  {
    id: 2,
    name: "Pack Premium Luxury",
    description: "Selección de lujo para el conocedor",
    totalPrice: 450,
    savings: 100,
    perfumes: [
      {
        name: "Creed Aventus",
        brand: "Creed",
        price: 350,
        family: "Frutal",
        rating: 4.9,
        image: "https://images.unsplash.com/photo-1647507653704-bde7f2d6dbf0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJmdW1lJTIwYm90dGxlJTIwbW9kZXJuJTIwbWluaW1hbGlzdHxlbnwxfHx8fDE3NTgxMjMzMDB8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
      },
      {
        name: "Tom Ford Black Orchid",
        brand: "Tom Ford",
        price: 175,
        family: "Oriental",
        rating: 4.6,
        image: "https://images.unsplash.com/photo-1620057958829-615f3ddbe922?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHhwZXJmdW1lJTIwaW5ncmVkaWVudHMlMjBib3RhbmljYWx8ZW58MXx8fHwxNzU4MTIzMzAxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
      }
    ],
    match: 88,
    badges: ["Premium", "Exclusivo"]
  }
];

export function PackBuilder() {
  const [formData, setFormData] = useState<PackBuilderForm>({
    budget: 500,
    quantity: 4,
    occasion: "",
    preferences: [],
    priceRange: "mixed",
    includeOwned: false
  });

  const [showResults, setShowResults] = useState(false);
  const [generatedPacks, setGeneratedPacks] = useState(samplePacks);

  const handlePreferenceChange = (preference: string, checked: boolean) => {
    setFormData(prev => ({
      ...prev,
      preferences: checked 
        ? [...prev.preferences, preference]
        : prev.preferences.filter(p => p !== preference)
    }));
  };

  const generatePacks = () => {
    setShowResults(true);
    // Aquí simularíamos la generación de packs basada en los criterios
    console.log("Generating packs with criteria:", formData);
  };

  const resetForm = () => {
    setFormData({
      budget: 500,
      quantity: 4,
      occasion: "",
      preferences: [],
      priceRange: "mixed",
      includeOwned: false
    });
    setShowResults(false);
  };

  return (
    <div className="space-y-8">
      {/* Header */}
      <div className="text-center space-y-4">
        <div className="inline-flex items-center gap-2 bg-gradient-to-r from-primary to-chart-2 text-white px-4 py-2 rounded-lg">
          <Sparkles className="h-5 w-5" />
          <span className="font-medium">Pack Builder Inteligente</span>
        </div>
        <h1 className="text-2xl font-bold">Construye tu Pack Personalizado</h1>
        <p className="text-muted-foreground max-w-2xl mx-auto">
          Ingresa tu presupuesto y preferencias, y nuestro algoritmo creará el pack perfecto de perfumes para ti, 
          optimizando calidad, precio y diversidad olfativa.
        </p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Form Section */}
        <div className="lg:col-span-1 space-y-6">
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Target className="h-5 w-5" />
                Criterios del Pack
              </CardTitle>
              <CardDescription>
                Define tus preferencias para el pack perfecto
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-6">
              {/* Budget */}
              <div className="space-y-3">
                <Label className="flex items-center gap-2">
                  <DollarSign className="h-4 w-4" />
                  Presupuesto: ${formData.budget}
                </Label>
                <Slider
                  value={[formData.budget]}
                  onValueChange={(value) => setFormData(prev => ({ ...prev, budget: value[0] }))}
                  max={2500}
                  min={50}
                  step={25}
                  className="w-full"
                />
                <div className="flex justify-between text-xs text-muted-foreground">
                  <span>$50</span>
                  <span>$2500</span>
                </div>
              </div>

              {/* Quantity */}
              <div className="space-y-3">
                <Label className="flex items-center gap-2">
                  <Package className="h-4 w-4" />
                  Cantidad de Perfumes: {formData.quantity}
                </Label>
                <Slider
                  value={[formData.quantity]}
                  onValueChange={(value) => setFormData(prev => ({ ...prev, quantity: value[0] }))}
                  max={10}
                  min={1}
                  step={1}
                  className="w-full"
                />
                <div className="flex justify-between text-xs text-muted-foreground">
                  <span>1</span>
                  <span>10</span>
                </div>
              </div>

              {/* Occasion */}
              <div className="space-y-2">
                <Label>Ocasión Principal</Label>
                <Select value={formData.occasion} onValueChange={(value) => setFormData(prev => ({ ...prev, occasion: value }))}>
                  <SelectTrigger>
                    <SelectValue placeholder="Selecciona una ocasión" />
                  </SelectTrigger>
                  <SelectContent>
                    {occasions.map(occasion => (
                      <SelectItem key={occasion.value} value={occasion.value}>
                        {occasion.label}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>

              {/* Price Range */}
              <div className="space-y-2">
                <Label>Rango de Precios</Label>
                <Select 
                  value={formData.priceRange} 
                  onValueChange={(value: "budget" | "mid" | "luxury" | "mixed") => 
                    setFormData(prev => ({ ...prev, priceRange: value }))
                  }
                >
                  <SelectTrigger>
                    <SelectValue />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="budget">Budget ($50-100)</SelectItem>
                    <SelectItem value="mid">Medio ($100-200)</SelectItem>
                    <SelectItem value="luxury">Lujo ($200+)</SelectItem>
                    <SelectItem value="mixed">Mixto</SelectItem>
                  </SelectContent>
                </Select>
              </div>

              {/* Preferences */}
              <div className="space-y-3">
                <Label>Familias Olfativas Preferidas</Label>
                <div className="grid grid-cols-2 gap-2">
                  {preferenceOptions.map(preference => (
                    <div key={preference} className="flex items-center space-x-2">
                      <Checkbox
                        id={preference}
                        checked={formData.preferences.includes(preference)}
                        onCheckedChange={(checked) => 
                          handlePreferenceChange(preference, checked as boolean)
                        }
                      />
                      <Label htmlFor={preference} className="text-sm">
                        {preference}
                      </Label>
                    </div>
                  ))}
                </div>
              </div>

              {/* Include Owned */}
              <div className="flex items-center space-x-2">
                <Checkbox
                  id="includeOwned"
                  checked={formData.includeOwned}
                  onCheckedChange={(checked) => 
                    setFormData(prev => ({ ...prev, includeOwned: checked as boolean }))
                  }
                />
                <Label htmlFor="includeOwned" className="text-sm">
                  Considerar perfumes que ya tengo
                </Label>
              </div>

              {/* Actions */}
              <div className="space-y-3 pt-4">
                <Button onClick={generatePacks} className="w-full" size="lg">
                  <Sparkles className="h-4 w-4 mr-2" />
                  Generar Packs
                </Button>
                <Button onClick={resetForm} variant="outline" className="w-full">
                  <RefreshCw className="h-4 w-4 mr-2" />
                  Limpiar
                </Button>
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Results Section */}
        <div className="lg:col-span-2">
          {!showResults ? (
            <Card className="h-full flex items-center justify-center">
              <CardContent className="text-center py-12">
                <Gift className="h-16 w-16 mx-auto text-muted-foreground mb-4" />
                <h3 className="text-lg font-medium mb-2">¡Descubre tu Pack Perfecto!</h3>
                <p className="text-muted-foreground">
                  Completa los criterios en el panel izquierdo y genera packs personalizados 
                  que se ajusten perfectamente a tu presupuesto y preferencias.
                </p>
              </CardContent>
            </Card>
          ) : (
            <div className="space-y-6">
              <div className="flex items-center justify-between">
                <h2 className="text-xl font-semibold">Packs Recomendados</h2>
                <Badge variant="secondary" className="bg-green-100 text-green-800">
                  {generatedPacks.length} opciones encontradas
                </Badge>
              </div>

              {generatedPacks.map((pack) => (
                <Card key={pack.id} className="overflow-hidden">
                  <CardHeader className="bg-gradient-to-r from-muted to-muted/50">
                    <div className="flex items-center justify-between">
                      <div>
                        <CardTitle className="flex items-center gap-2">
                          {pack.name}
                          <Badge variant="secondary" className="bg-blue-100 text-blue-800">
                            {pack.match}% Match
                          </Badge>
                        </CardTitle>
                        <CardDescription>{pack.description}</CardDescription>
                      </div>
                      <div className="text-right">
                        <p className="text-2xl font-bold">${pack.totalPrice}</p>
                        <p className="text-sm text-green-600">Ahorras ${pack.savings}</p>
                      </div>
                    </div>
                    <div className="flex gap-2 mt-2">
                      {pack.badges.map((badge, index) => (
                        <Badge key={index} variant="outline" className="bg-white">
                          {badge}
                        </Badge>
                      ))}
                    </div>
                  </CardHeader>
                  <CardContent className="p-6">
                    <div className="space-y-4">
                      <h4 className="font-medium">Perfumes incluidos:</h4>
                      <div className="grid gap-4">
                        {pack.perfumes.map((perfume, index) => (
                          <div key={index} className="flex items-center gap-4 p-3 rounded-lg bg-muted/30">
                            <div className="w-12 h-12 rounded-lg overflow-hidden">
                              <ImageWithFallback
                                src={perfume.image}
                                alt={perfume.name}
                                className="w-full h-full object-cover"
                              />
                            </div>
                            <div className="flex-1">
                              <h5 className="font-medium">{perfume.name}</h5>
                              <p className="text-sm text-muted-foreground">
                                {perfume.brand} • {perfume.family}
                              </p>
                            </div>
                            <div className="text-right">
                              <p className="font-medium">${perfume.price}</p>
                              <div className="flex items-center gap-1">
                                <Star className="h-3 w-3 fill-yellow-400 text-yellow-400" />
                                <span className="text-xs">{perfume.rating}</span>
                              </div>
                            </div>
                          </div>
                        ))}
                      </div>

                      <div className="flex gap-3 pt-4">
                        <Button className="flex-1">
                          <ShoppingCart className="h-4 w-4 mr-2" />
                          Comprar Pack Completo
                        </Button>
                        <Button variant="outline">
                          <Heart className="h-4 w-4 mr-2" />
                          Guardar
                        </Button>
                        <Button variant="outline">
                          Ver Detalles
                        </Button>
                      </div>
                    </div>
                  </CardContent>
                </Card>
              ))}

              {/* Pack Analysis */}
              <Card>
                <CardHeader>
                  <CardTitle className="flex items-center gap-2">
                    <TrendingUp className="h-5 w-5" />
                    Análisis de Recomendaciones
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-center">
                    <div className="p-4 bg-muted/30 rounded-lg">
                      <h4 className="font-medium text-green-600">Mejor Valor</h4>
                      <p className="text-sm text-muted-foreground mt-1">
                        {generatedPacks[0].name} ofrece el mejor balance calidad-precio
                      </p>
                    </div>
                    <div className="p-4 bg-muted/30 rounded-lg">
                      <h4 className="font-medium text-blue-600">Más Versátil</h4>
                      <p className="text-sm text-muted-foreground mt-1">
                        Cubre diferentes ocasiones y momentos del día
                      </p>
                    </div>
                    <div className="p-4 bg-muted/30 rounded-lg">
                      <h4 className="font-medium text-purple-600">Diversidad</h4>
                      <p className="text-sm text-muted-foreground mt-1">
                        Explora {new Set(generatedPacks.flatMap(p => p.perfumes.map(perf => perf.family))).size} familias olfativas
                      </p>
                    </div>
                  </div>
                </CardContent>
              </Card>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}