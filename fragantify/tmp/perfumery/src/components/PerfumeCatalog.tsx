import { useState } from "react";
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
import { Search, Heart, Star } from "lucide-react";
import { ImageWithFallback } from "./figma/ImageWithFallback";

const mockPerfumes = [
  {
    id: 1,
    name: "Dior Sauvage",
    brand: "Dior",
    type: "Eau de Parfum",
    price: 120,
    rating: 4.8,
    reviews: 324,
    family: "Amaderada",
    notes: ["Bergamota", "Pimienta", "Sándalo"],
    image:
      "https://images.unsplash.com/photo-1647507653704-bde7f2d6dbf0?ixlib=rb-4.1.0&auto=format&fit=crop&w=400&q=80"
  },
  {
    id: 2,
    name: "Bleu de Chanel",
    brand: "Chanel",
    type: "Eau de Toilette",
    price: 110,
    rating: 4.7,
    reviews: 280,
    family: "Cítrica",
    notes: ["Limón", "Cedro", "Menta"],
    image:
      "https://images.unsplash.com/photo-1617114857835-62d12a42e4e8?ixlib=rb-4.1.0&auto=format&fit=crop&w=400&q=80"
  }
  // 👉 podés seguir agregando perfumes mock acá
];

export function PerfumeCatalog() {
  const [search, setSearch] = useState("");
  const [priceRange, setPriceRange] = useState([50, 200]);

  return (
    <section id="catalog" className="bg-[#0f172a] py-16">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header + Search */}
        <div className="flex flex-col lg:flex-row justify-between items-center mb-8 lg:mb-10 gap-4">
          <h2 className="text-2xl sm:text-3xl font-serif text-white text-center lg:text-left">Catálogo de Perfumes</h2>
          <div className="flex items-center gap-2 sm:gap-3 w-full sm:w-auto max-w-md lg:max-w-none">
            <Input
              placeholder="Buscar perfumes..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="bg-black text-white border-[#d4af37] placeholder-gray-400 shadow-md text-sm sm:text-base"
            />
            <Button className="bg-[#d4af37] text-black hover:bg-[#fbbf24] shadow-md px-3 sm:px-4">
              <Search className="w-4 h-4 sm:w-5 sm:h-5" />
            </Button>
          </div>
        </div>

        {/* Filters */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 lg:gap-6 mb-8 lg:mb-12">
          <div>
            <label className="block text-sm text-white mb-2">
              Marca
            </label>
            <Select>
              <SelectTrigger className="bg-black text-white border-[#d4af37] shadow-md">
                <SelectValue placeholder="Todas las Marcas" />
              </SelectTrigger>
              <SelectContent className="bg-black text-white border border-[#d4af37]">
                <SelectItem value="all">Todas</SelectItem>
                <SelectItem value="dior">Dior</SelectItem>
                <SelectItem value="chanel">Chanel</SelectItem>
                <SelectItem value="ysl">YSL</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div>
            <label className="block text-sm text-white mb-2">
              Familia
            </label>
            <Select>
              <SelectTrigger className="bg-black text-white border-[#d4af37] shadow-md">
                <SelectValue placeholder="Todas las Familias" />
              </SelectTrigger>
              <SelectContent className="bg-black text-white border border-[#d4af37]">
                <SelectItem value="all">Todas</SelectItem>
                <SelectItem value="citrica">Cítrica</SelectItem>
                <SelectItem value="amaderada">Amaderada</SelectItem>
                <SelectItem value="floral">Floral</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div>
            <label className="block text-sm text-white mb-2">
              Tipo
            </label>
            <Select>
              <SelectTrigger className="bg-black text-white border-[#d4af37] shadow-md">
                <SelectValue placeholder="Todos los Tipos" />
              </SelectTrigger>
              <SelectContent className="bg-black text-white border border-[#d4af37]">
                <SelectItem value="all">Todos</SelectItem>
                <SelectItem value="eau-de-parfum">Eau de Parfum</SelectItem>
                <SelectItem value="eau-de-toilette">Eau de Toilette</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div>
            <label className="block text-sm text-white mb-2">
              Rango de Precio
            </label>
            <Slider
              defaultValue={[50, 200]}
              min={0}
              max={300}
              step={10}
              value={priceRange}
              onValueChange={setPriceRange}
              className="text-[#d4af37]"
            />
            <div className="flex justify-between text-sm text-white mt-2">
              <span>${priceRange[0]}</span>
              <span>${priceRange[1]}</span>
            </div>
          </div>
        </div>

        {/* Grid perfumes */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4 sm:gap-6 lg:gap-8">
          {mockPerfumes.map((perfume) => (
            <Card
              key={perfume.id}
              className="bg-black border border-[#d4af37] rounded-lg shadow-xl hover:shadow-2xl transition-all duration-300 transform hover:scale-105"
            >
              <CardContent className="p-4 sm:p-6 flex flex-col items-center text-center">
                <div className="w-32 h-40 sm:w-36 sm:h-44 lg:w-40 lg:h-48 mb-3 sm:mb-4 overflow-hidden rounded-md border border-[#d4af37]">
                  <ImageWithFallback
                    src={perfume.image}
                    alt={perfume.name}
                    className="w-full h-full object-cover"
                  />
                </div>
                <h3 className="text-lg sm:text-xl font-serif text-white leading-tight">
                  {perfume.name}
                </h3>
                <p className="text-gray-300 text-sm sm:text-base">{perfume.brand}</p>
                <p className="text-[#d4af37] text-lg sm:text-xl mt-1 sm:mt-2">
                  ${perfume.price}
                </p>

                {/* Rating */}
                <div className="flex items-center justify-center gap-1 mt-2">
                  <Star className="w-4 h-4 text-[#d4af37] fill-current" />
                  <span className="text-white text-sm">
                    {perfume.rating} ({perfume.reviews})
                  </span>
                </div>

                {/* Badges notas */}
                <div className="flex flex-wrap gap-2 justify-center mt-3">
                  {perfume.notes.map((note, idx) => (
                    <Badge
                      key={idx}
                      className="bg-[#1e293b] text-white border border-[#d4af37] hover:bg-[#d4af37] hover:text-black transition-colors"
                    >
                      {note}
                    </Badge>
                  ))}
                </div>

                {/* Actions */}
                <div className="flex gap-3 mt-6">
                  <Button
                    variant="outline"
                    className="border-[#d4af37] text-[#d4af37] hover:bg-[#d4af37] hover:text-black transition-all duration-300"
                  >
                    <Heart className="w-4 h-4 mr-1" /> Favorito
                  </Button>
                  <Button className="bg-[#d4af37] text-black hover:bg-[#fbbf24] transition-all duration-300">
                    Detalles
                  </Button>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  );
}
