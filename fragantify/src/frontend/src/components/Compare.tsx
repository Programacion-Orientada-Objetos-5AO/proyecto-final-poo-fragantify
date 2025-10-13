import { useMemo } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Badge } from "./ui/badge";
import { Separator } from "./ui/separator";
import { X, GitCompareArrows, AlertTriangle } from "lucide-react";
import { ImageWithFallback } from "./figma/ImageWithFallback";
import { useFragrances } from "../context/FragranceContext";
import { normalizePrice } from "../utils/prices";

export function Compare() {
  const { compareList, removeFromCompare, getById, favorites, toggleFavorite } = useFragrances();

  const selectedPerfumes = useMemo(() => compareList
    .map((id) => getById(id))
    .filter((item): item is NonNullable<ReturnType<typeof getById>> => Boolean(item)),
  [compareList, getById]);

  const cheapest = useMemo(() => {
    let minPrice = Number.POSITIVE_INFINITY;
    let cheapestId: string | null = null;
    selectedPerfumes.forEach((item) => {
      const price = normalizePrice(item.price);
      if (price !== null && price < minPrice) {
        minPrice = price;
        cheapestId = item.id;
      }
    });
    return cheapestId;
  }, [selectedPerfumes]);

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-2xl font-bold">Comparar fragancias</h1>
        <p className="text-muted-foreground">
          Selecciona hasta tres fragancias desde el catalogo para compararlas lado a lado.
        </p>
      </div>

      {selectedPerfumes.length === 0 ? (
        <Card>
          <CardHeader>
            <CardTitle>Sin fragancias en comparacion</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <p className="text-muted-foreground">
              Agrega fragancias desde el catalogo utilizando el boton "Comparar" para verlas aqui.
            </p>
          </CardContent>
        </Card>
      ) : (
        <div className="grid gap-6" style={{ gridTemplateColumns: `repeat(${selectedPerfumes.length}, minmax(0, 1fr))` }}>
          {selectedPerfumes.map((perfume) => {
            const accordList = perfume.mainAccords ?? perfume.generalNotes ?? [];
            const noteGroups = perfume.notes ?? {};
            const favorite = favorites.includes(perfume.id);

            return (
              <Card key={perfume.id} className="bg-black border border-[#d4af37]/30">
                <CardContent className="p-6 space-y-4">
                  <div className="flex justify-between items-start">
                    <div>
                      <h3 className="text-xl font-semibold text-white">{perfume.name}</h3>
                      <p className="text-sm text-muted-foreground">{perfume.brand}</p>
                    </div>
                    <Button variant="ghost" size="icon" onClick={() => removeFromCompare(perfume.id)}>
                      <X className="h-5 w-5" />
                    </Button>
                  </div>

                  <div className="w-full aspect-square rounded-lg overflow-hidden border border-[#d4af37]/30">
                    <ImageWithFallback
                      src={perfume.imageUrl}
                      fallbackUrls={perfume.imageFallbacks}
                      alt={perfume.name}
                      className="w-full h-full object-cover"
                    />
                  </div>

                  <div className="flex flex-wrap gap-2">
                    {perfume.gender && (
                      <Badge variant="outline" className="text-white border-[#d4af37]">
                        {perfume.gender}
                      </Badge>
                    )}
                    {perfume.longevity && (
                      <Badge variant="outline" className="text-white border-[#d4af37]">
                        Longevidad: {perfume.longevity}
                      </Badge>
                    )}
                    {perfume.sillage && (
                      <Badge variant="outline" className="text-white border-[#d4af37]">
                        Sillage: {perfume.sillage}
                      </Badge>
                    )}
                  </div>

                  <Separator />

                  <div className="space-y-2">
                    <h4 className="text-sm font-semibold text-white">Acordes clave</h4>
                    <div className="flex flex-wrap gap-2">
                      {accordList.slice(0, 6).map((accord) => (
                        <Badge key={accord} variant="secondary" className="bg-[#1e293b] text-white">
                          {accord}
                        </Badge>
                      ))}
                      {accordList.length === 0 && (
                        <span className="text-xs text-muted-foreground">Sin datos de acordes</span>
                      )}
                    </div>
                  </div>

                  <div className="space-y-3">
                    <h4 className="text-sm font-semibold text-white">Piramide olfativa</h4>
                    {["top", "middle", "base"].map((level) => {
                      const labelMap: Record<string, string> = {
                        top: "Notas de salida",
                        middle: "Notas de corazon",
                        base: "Notas de fondo",
                      };
                      const notes = noteGroups[level as keyof typeof noteGroups];
                      if (!notes || notes.length === 0) {
                        return null;
                      }
                      return (
                        <div key={level}>
                          <p className="text-xs uppercase text-gray-400">{labelMap[level]}</p>
                          <div className="flex flex-wrap gap-2 mt-1">
                            {notes.slice(0, 6).map((note) => (
                              <Badge key={note.name} variant="outline" className="text-white border-[#d4af37]">
                                {note.name}
                              </Badge>
                            ))}
                          </div>
                        </div>
                      );
                    })}
                  </div>

                  <Separator />

                  <div className="flex items-center justify-between">
                    <span className={`text-xl font-semibold ${cheapest === perfume.id ? "text-green-400" : "text-[#d4af37]"}`}>
                      {perfume.price ?? "Precio no disponible"}
                    </span>
                    <div className="flex gap-2">
                      <Button variant="outline" onClick={() => toggleFavorite(perfume.id)}>
                        {favorite ? "Quitar favorito" : "Marcar favorito"}
                      </Button>
                    </div>
                  </div>
                </CardContent>
              </Card>
            );
          })}
        </div>
      )}

      {selectedPerfumes.length > 1 && (
        <Card className="bg-[#111827] border border-[#d4af37]/20">
          <CardHeader>
            <CardTitle className="flex items-center gap-2 text-[#d4af37]">
              <GitCompareArrows className="h-5 w-5" /> Resumen rapido
            </CardTitle>
          </CardHeader>
          <CardContent className="grid grid-cols-1 md:grid-cols-3 gap-4 text-center">
            <div className="p-4 bg-black/40 rounded-lg">
              <p className="text-sm text-muted-foreground">Mejor precio disponible</p>
              {cheapest ? (
                <p className="text-lg font-semibold text-white">
                  {selectedPerfumes.find((item) => item.id === cheapest)?.name}
                </p>
              ) : (
                <p className="text-sm text-gray-400">No hay datos de precio</p>
              )}
            </div>
            <div className="p-4 bg-black/40 rounded-lg">
              <p className="text-sm text-muted-foreground">Total comparadas</p>
              <p className="text-lg font-semibold text-white">{selectedPerfumes.length}</p>
            </div>
            <div className="p-4 bg-black/40 rounded-lg">
              <p className="text-sm text-muted-foreground">Recuerda revisar notas</p>
              <p className="text-xs text-gray-400 flex items-center justify-center gap-1">
                <AlertTriangle className="h-4 w-4" /> Verifica que las notas coincidan con tus preferencias.
              </p>
            </div>
          </CardContent>
        </Card>
      )}
    </div>
  );
}
