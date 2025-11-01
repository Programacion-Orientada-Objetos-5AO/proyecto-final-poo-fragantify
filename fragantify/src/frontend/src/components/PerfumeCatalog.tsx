import { useEffect, useMemo, useState } from "react";
import { Card, CardContent } from "./ui/card";
import { Button } from "./ui/button";
import { Badge } from "./ui/badge";
import { Input } from "./ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue
} from "./ui/select";
import { Slider } from "./ui/slider";
import { Heart, GitCompareArrows, RefreshCw } from "lucide-react";
import { ImageWithFallback } from "./figma/ImageWithFallback";
import { useFragrances } from "../context/FragranceContext";
import { normalizePrice, formatPrice } from "../utils/prices";

export function PerfumeCatalog() {
  const {
    fragrances,
    loading,
    error,
    toggleFavorite,
    isFavorite,
    addToCompare,
    isInCompare,
    refresh
  } = useFragrances();
  const [search, setSearch] = useState("");
  const [selectedBrand, setSelectedBrand] = useState("all");
  const [selectedAccord, setSelectedAccord] = useState("all");
  const [selectedGender, setSelectedGender] = useState("all");
  const [priceRange, setPriceRange] = useState<[number, number]>([0, 500]);

  const priceLimits = useMemo(() => {
    const prices = fragrances
      .map((item) => normalizePrice(item.price))
      .filter((value): value is number => value !== null);

    if (prices.length === 0) {
      return { min: 0, max: 500 };
    }

    const min = Math.floor(Math.min(...prices));
    const max = Math.ceil(Math.max(...prices));
    return { min, max };
  }, [fragrances]);

  useEffect(() => {
    setPriceRange([priceLimits.min, priceLimits.max]);
  }, [priceLimits.min, priceLimits.max]);

  const brands = useMemo(() => {
    const set = new Set<string>();
    fragrances.forEach((item) => {
      if (item.brand) {
        set.add(item.brand);
      }
    });
    return Array.from(set).sort((a, b) => a.localeCompare(b));
  }, [fragrances]);

  const accords = useMemo(() => {
    const set = new Set<string>();
    fragrances.forEach((item) => {
      (item.mainAccords ?? item.generalNotes ?? []).forEach((accord) => {
        if (accord) {
          set.add(accord);
        }
      });
    });
    return Array.from(set).sort((a, b) => a.localeCompare(b));
  }, [fragrances]);

  const genders = useMemo(() => {
    const set = new Set<string>();
    fragrances.forEach((item) => {
      if (item.gender) {
        set.add(item.gender);
      }
    });
    return Array.from(set).sort((a, b) => a.localeCompare(b));
  }, [fragrances]);

  const filteredFragrances = useMemo(() => {
    return fragrances.filter((item) => {
      const matchesSearch = [item.name, item.brand]
        .filter(Boolean)
        .some((field) => field!.toLowerCase().includes(search.toLowerCase()));

      const matchesBrand = selectedBrand === "all" || item.brand === selectedBrand;

      const accordsToCheck = item.mainAccords ?? item.generalNotes ?? [];
      const matchesAccord =
        selectedAccord === "all" || accordsToCheck.includes(selectedAccord);

      const matchesGender =
        selectedGender === "all" || item.gender === selectedGender;

      const priceValue = normalizePrice(item.price);
      const matchesPrice =
        priceValue === null || (priceValue >= priceRange[0] && priceValue <= priceRange[1]);

      return matchesSearch && matchesBrand && matchesAccord && matchesGender && matchesPrice;
    });
  }, [fragrances, search, selectedBrand, selectedAccord, selectedGender, priceRange]);

  return (
    <section id="catalog" className="bg-[#0f172a] py-16">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex flex-col lg:flex-row justify-between items-center mb-8 lg:mb-10 gap-4">
          <div>
            <h2 className="text-2xl sm:text-3xl font-serif text-white text-center lg:text-left">
              Catalogo de fragancias
            </h2>
            <p className="text-sm text-gray-400 mt-2">
              Datos obtenidos en tiempo real desde la API de Fragantify.
            </p>
          </div>
          <div className="flex items-center gap-2 sm:gap-3 w-full sm:w-auto max-w-md lg:max-w-none">
            <Input
              id="catalog-search"
              placeholder="Buscar por nombre o marca"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="bg-black text-white border-[#d4af37] placeholder-gray-400 shadow-md text-sm sm:text-base"
            />
            <Button
              className="bg-[#d4af37] text-black hover:bg-[#fbbf24] shadow-md px-3 sm:px-4"
              onClick={() => refresh()}
              disabled={loading}
              title="Actualizar catalogo"
            >
              <RefreshCw className={`w-4 h-4 sm:w-5 sm:h-5 ${loading ? "animate-spin" : ""}`} />
            </Button>
          </div>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 lg:gap-6 mb-8 lg:mb-12">
          <div>
            <label className="block text-sm text-white mb-2">Marca</label>
            <Select value={selectedBrand} onValueChange={setSelectedBrand}>
              <SelectTrigger className="bg-black text-white border-[#d4af37] shadow-md">
                <SelectValue placeholder="Todas las marcas" />
              </SelectTrigger>
              <SelectContent className="bg-black text-white border border-[#d4af37] max-h-60">
                <SelectItem value="all">Todas</SelectItem>
                {brands.map((brand) => (
                  <SelectItem key={brand} value={brand}>
                    {brand}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div>
            <label className="block text-sm text-white mb-2">Acorde principal</label>
            <Select value={selectedAccord} onValueChange={setSelectedAccord}>
              <SelectTrigger className="bg-black text-white border-[#d4af37] shadow-md">
                <SelectValue placeholder="Todos los acordes" />
              </SelectTrigger>
              <SelectContent className="bg-black text-white border border-[#d4af37] max-h-60">
                <SelectItem value="all">Todos</SelectItem>
                {accords.map((accord) => (
                  <SelectItem key={accord} value={accord}>
                    {accord}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div>
            <label className="block text-sm text-white mb-2">Genero</label>
            <Select value={selectedGender} onValueChange={setSelectedGender}>
              <SelectTrigger className="bg-black text-white border-[#d4af37] shadow-md">
                <SelectValue placeholder="Todos los generos" />
              </SelectTrigger>
              <SelectContent className="bg-black text-white border border-[#d4af37] max-h-60">
                <SelectItem value="all">Todos</SelectItem>
                {genders.map((gender) => (
                  <SelectItem key={gender} value={gender}>
                    {gender}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div>
            <label className="block text-sm text-white mb-2">Rango de precio</label>
            <Slider
              defaultValue={[priceLimits.min, priceLimits.max]}
              min={priceLimits.min}
              max={priceLimits.max}
              step={5}
              value={priceRange}
              onValueChange={(value) => setPriceRange([value[0], value[1]])}
              className="text-[#d4af37]"
            />
            <div className="flex justify-between text-sm text-white mt-2">
              <span>${priceRange[0]}</span>
              <span>${priceRange[1]}</span>
            </div>
          </div>
        </div>

        {loading && (
          <div className="text-center text-gray-300 py-10">Cargando fragancias...</div>
        )}

        {error && !loading && (
          <div className="text-center text-red-400 py-10">{error}</div>
        )}

        {!loading && !error && filteredFragrances.length === 0 && (
          <div className="text-center text-gray-300 py-10">
            No encontramos fragancias que coincidan con los filtros seleccionados.
          </div>
        )}

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4 sm:gap-6 lg:gap-8">
          {filteredFragrances.map((perfume) => {
            const notes = (perfume.mainAccords ?? perfume.generalNotes ?? []).slice(0, 4);

            return (
              <Card
                key={perfume.id}
                className="bg-black border border-[#d4af37] rounded-lg shadow-xl hover:shadow-2xl transition-all duration-300 transform hover:scale-105"
              >
                <CardContent className="p-4 sm:p-6 flex flex-col items-center text-center">
                  <div className="w-32 h-40 sm:w-36 sm:h-44 lg:w-40 lg:h-48 mb-3 sm:mb-4 overflow-hidden rounded-md border border-[#d4af37] bg-[#111827]">
                    <ImageWithFallback
                      src={perfume.imageUrl}
                      fallbackUrls={perfume.imageFallbacks}
                      alt={perfume.name}
                      className="w-full h-full object-cover"
                    />
                  </div>
                  <h3 className="text-lg sm:text-xl font-serif text-white leading-tight">
                    {perfume.name}
                  </h3>
                  <p className="text-gray-300 text-sm sm:text-base">{perfume.brand}</p>
                  <p className="text-[#d4af37] text-lg sm:text-xl mt-1 sm:mt-2">
                    {formatPrice(perfume.price)}
                  </p>

                  <div className="flex flex-wrap gap-2 justify-center mt-3">
                    {perfume.gender && (
                      <Badge className="bg-[#1e293b] text-white border border-[#d4af37]">
                        {perfume.gender}
                      </Badge>
                    )}
                    {perfume.longevity && (
                      <Badge className="bg-[#1e293b] text-white border border-[#d4af37]">
                        Longevidad: {perfume.longevity}
                      </Badge>
                    )}
                    {perfume.sillage && (
                      <Badge className="bg-[#1e293b] text-white border border-[#d4af37]">
                        Sillage: {perfume.sillage}
                      </Badge>
                    )}
                  </div>

                  {notes.length > 0 && (
                    <div className="flex flex-wrap gap-2 justify-center mt-3">
                      {notes.map((note) => (
                        <Badge
                          key={note}
                          className="bg-[#1e293b] text-white border border-[#d4af37] hover:bg-[#d4af37] hover:text-black transition-colors"
                        >
                          {note}
                        </Badge>
                      ))}
                    </div>
                  )}

                  <div className="flex gap-3 mt-6">
                    <Button
                      variant="outline"
                      className={`border-[#d4af37] text-[#d4af37] hover:bg-[#d4af37] hover:text-black transition-all duration-300 ${
                        isFavorite(perfume.id) ? "bg-[#d4af37]/10" : ""
                      }`}
                      onClick={() => toggleFavorite(perfume.id)}
                    >
                      <Heart className="w-4 h-4 mr-1" />
                      {isFavorite(perfume.id) ? "Guardado" : "Favorito"}
                    </Button>
                    <Button
                      className={`bg-[#d4af37] text-black hover:bg-[#fbbf24] transition-all duration-300 ${
                        isInCompare(perfume.id) ? "opacity-80" : ""
                      }`}
                      onClick={() => addToCompare(perfume.id)}
                    >
                      <GitCompareArrows className="w-4 h-4 mr-1" />
                      {isInCompare(perfume.id) ? "En comparacion" : "Comparar"}
                    </Button>
                  </div>
                </CardContent>
              </Card>
            );
          })}
        </div>
      </div>
    </section>
  );
}


