import { useMemo, useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Badge } from "./ui/badge";
import { Input } from "./ui/input";
import { Label } from "./ui/label";
import { Slider } from "./ui/slider";
import { Checkbox } from "./ui/checkbox";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/select";
import { ImageWithFallback } from "./figma/ImageWithFallback";
import { useFragrances } from "../context/FragranceContext";
import { normalizePrice } from "../utils/prices";
import { Package, Sparkles, Gift } from "lucide-react";

interface PackBuilderForm {
  budget: number;
  quantity: number;
  occasion: string;
  preferences: string[];
}

const OCCASIONS = [
  { value: "daily", label: "Uso diario" },
  { value: "work", label: "Trabajo" },
  { value: "evening", label: "Noches" },
  { value: "special", label: "Eventos especiales" },
  { value: "gifting", label: "Regalos" },
];

export function PackBuilder() {
  const { fragrances } = useFragrances();
  const [form, setForm] = useState<PackBuilderForm>({
    budget: 300,
    quantity: 3,
    occasion: "daily",
    preferences: [],
  });

  const accordOptions = useMemo(() => {
    const set = new Set<string>();
    fragrances.forEach((item) => {
      (item.mainAccords ?? item.generalNotes ?? []).forEach((accord) => {
        if (accord) {
          set.add(accord);
        }
      });
    });
    return Array.from(set).sort((a, b) => a.localeCompare(b)).slice(0, 12);
  }, [fragrances]);

  const suggestions = useMemo(() => {
    const budgetPerItem = form.budget / Math.max(1, form.quantity);

    return fragrances
      .filter((item) => {
        const price = normalizePrice(item.price);
        if (price !== null && price > budgetPerItem * 1.2) {
          return false;
        }
        if (form.preferences.length === 0) {
          return true;
        }
        const accords = item.mainAccords ?? item.generalNotes ?? [];
        return form.preferences.some((pref) => accords.includes(pref));
      })
      .slice(0, form.quantity);
  }, [fragrances, form]);

  const handlePreferenceToggle = (value: string) => {
    setForm((prev) => ({
      ...prev,
      preferences: prev.preferences.includes(value)
        ? prev.preferences.filter((item) => item !== value)
        : [...prev.preferences, value],
    }));
  };

  return (
    <div className="space-y-8">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">Pack builder</h1>
          <p className="text-muted-foreground">Diseña combinaciones usando datos del catalogo.</p>
        </div>
        <Badge variant="outline" className="text-white border-[#d4af37]">
          <Package className="h-4 w-4 mr-2" /> {form.quantity} fragancias
        </Badge>
      </div>

      <Card className="bg-black border border-[#d4af37]/30">
        <CardHeader>
          <CardTitle>Preferencias</CardTitle>
          <CardDescription>Selecciona tus limites y acordes preferidos.</CardDescription>
        </CardHeader>
        <CardContent className="space-y-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="space-y-3">
              <Label>Presupuesto total (USD aproximado)</Label>
              <Slider
                min={100}
                max={800}
                step={25}
                value={[form.budget]}
                onValueChange={([value]) => setForm((prev) => ({ ...prev, budget: value }))}
              />
              <div className="flex justify-between text-sm text-muted-foreground">
                <span>$100</span>
                <span>${form.budget}</span>
                <span>$800</span>
              </div>
            </div>
            <div className="space-y-3">
              <Label>Cantidad de fragancias</Label>
              <Input
                type="number"
                min={1}
                max={5}
                value={form.quantity}
                onChange={(event) => setForm((prev) => ({ ...prev, quantity: Number(event.target.value) }))}
              />
            </div>
          </div>

          <div className="space-y-3">
            <Label>Ocasion principal</Label>
            <Select
              value={form.occasion}
              onValueChange={(value) => setForm((prev) => ({ ...prev, occasion: value }))}
            >
              <SelectTrigger className="bg-[#0f172a] border border-[#d4af37]/40">
                <SelectValue placeholder="Selecciona una ocasion" />
              </SelectTrigger>
              <SelectContent className="bg-[#0f172a] border border-[#d4af37]/40 text-white">
                {OCCASIONS.map((item) => (
                  <SelectItem key={item.value} value={item.value}>
                    {item.label}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div className="space-y-3">
            <Label>Acordes que te gustan</Label>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
              {accordOptions.map((accord) => {
                const checked = form.preferences.includes(accord);
                return (
                  <label key={accord} className="flex items-center gap-2 p-2 rounded border border-[#d4af37]/20 hover:border-[#d4af37]/60">
                    <Checkbox checked={checked} onCheckedChange={() => handlePreferenceToggle(accord)} />
                    <span className="text-sm">{accord}</span>
                  </label>
                );
              })}
            </div>
          </div>
        </CardContent>
      </Card>

      <Card className="bg-[#0f172a] border border-[#d4af37]/30">
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <Sparkles className="h-5 w-5 text-[#d4af37]" /> Pack sugerido
          </CardTitle>
          <CardDescription>
            Basado en tus preferencias y presupuesto aproximado de ${form.budget}.
          </CardDescription>
        </CardHeader>
        <CardContent className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {suggestions.length === 0 && (
            <div className="col-span-full text-center text-muted-foreground py-6">
              No encontramos coincidencias con los filtros seleccionados. Ajusta las preferencias para ver opciones.
            </div>
          )}

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
                <h3 className="font-semibold text-white">{perfume.name}</h3>
                <p className="text-sm text-muted-foreground">{perfume.brand}</p>
              </div>
              <div className="flex flex-wrap gap-2">
                {(perfume.mainAccords ?? perfume.generalNotes ?? []).slice(0, 3).map((accord) => (
                  <Badge key={accord} variant="secondary" className="bg-[#1e293b] text-white">
                    {accord}
                  </Badge>
                ))}
              </div>
              <p className="text-sm text-[#d4af37] font-medium">{perfume.price ?? "Precio no disponible"}</p>
            </div>
          ))}
        </CardContent>
      </Card>

      <Card className="bg-black border border-[#d4af37]/30">
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <Gift className="h-5 w-5 text-[#d4af37]" /> Consejos rapidos
          </CardTitle>
        </CardHeader>
        <CardContent className="grid grid-cols-1 md:grid-cols-3 gap-4 text-sm text-muted-foreground">
          <div>
            <p className="font-semibold text-white mb-2">Balancea tus acordes</p>
            <p>Combina un acorde dominante con opciones complementarias para cubrir diferentes ocasiones.</p>
          </div>
          <div>
            <p className="font-semibold text-white mb-2">Considera la longevidad</p>
            <p>Si buscas uso diario, elige fragancias con longevidad media o alta.</p>
          </div>
          <div>
            <p className="font-semibold text-white mb-2">Presupuesto flexible</p>
            <p>Reserva parte del presupuesto para una fragancia estrella que destaque el pack.</p>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
