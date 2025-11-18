import { Card, CardContent } from "./ui/card";
import { Button } from "./ui/button";
import { ImageWithFallback } from "./figma/ImageWithFallback";
import { useFragrances } from "../context/FragranceContext";
import { Gift, Sparkles } from "lucide-react";

export function FeaturedProducts() {
  const { fragrances, loading, error } = useFragrances();
  const featured = fragrances.slice(0, 3);

  return (
    <section id="featured" className="py-20 bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h2 className="text-4xl font-serif text-gray-900 mb-4">Fragancias destacadas</h2>
          <p className="text-lg text-gray-600 max-w-2xl mx-auto">
            Seleccionamos algunas opciones del catalogo en base a la informacion disponible.
          </p>
        </div>

        {loading && (
          <div className="text-center text-gray-500 py-10">Cargando fragancias...</div>
        )}

        {error && !loading && (
          <div className="text-center text-red-500 py-10">{error}</div>
        )}

        {!loading && !error && featured.length > 0 && (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {featured.map((product) => (
              <Card key={product.id} className="group hover:shadow-xl transition-shadow duration-300 bg-white">
                <CardContent className="p-6">
                  <div className="aspect-square mb-6 overflow-hidden rounded-lg bg-gray-100">
                    <ImageWithFallback
                      src={product.imageUrl}
                      fallbackUrls={product.imageFallbacks}
                      alt={product.name}
                      className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                    />
                  </div>

                  <div className="space-y-4">
                    <div>
                      <div className="flex items-center justify-between mb-2">
                        <span className="text-sm text-gray-500 bg-gray-100 px-2 py-1 rounded-full">
                          {product.gender ?? "Unisex"}
                        </span>
                        <div className="flex items-center text-sm text-gray-600 gap-2">
                          <Sparkles className="h-4 w-4 text-yellow-500" />
                          {product.mainAccords?.[0] ?? "Edicion limitada"}
                        </div>
                      </div>
                      <h3 className="text-xl font-semibold text-gray-900 mb-2">{product.name}</h3>
                      <p className="text-gray-600 text-sm leading-relaxed">{product.brand}</p>
                    </div>

                    <div className="flex items-center justify-between pt-4 border-t border-gray-100">
                      <span className="text-2xl font-semibold text-gray-900">{product.price ?? "Precio no disponible"}</span>
                      <Button size="sm" className="flex items-center gap-2">
                        <Gift className="h-4 w-4" /> Ver detalle
                      </Button>
                    </div>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        )}
      </div>
    </section>
  );
}
