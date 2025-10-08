import { Card, CardContent } from "./ui/card";
import { Button } from "./ui/button";
import { Star, ShoppingCart } from "lucide-react";
import { ImageWithFallback } from "./figma/ImageWithFallback";

const featuredProducts = [
  {
    id: 1,
    name: "Golden Elegance",
    price: "$185",
    rating: 4.9,
    reviews: 124,
    image: "https://images.unsplash.com/photo-1733660227163-01bc46e0d7d7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxlbGVnYW50JTIwcGVyZnVtZSUyMGJvdHRsZSUyMGdvbGR8ZW58MXx8fHwxNzU4MDk4NDE0fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    description: "A sophisticated blend of bergamot, jasmine, and sandalwood",
    category: "Floral"
  },
  {
    id: 2,
    name: "Midnight Noir",
    price: "$165",
    rating: 4.8,
    reviews: 89,
    image: "https://images.unsplash.com/photo-1640869116016-93c00ba94b28?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJmdW1lJTIwYm90dGxlJTIwbWluaW1hbGlzdCUyMGJsYWNrfGVufDF8fHx8MTc1ODEyMjcwMnww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    description: "An intense woody fragrance with notes of cedar and amber",
    category: "Woody"
  },
  {
    id: 3,
    name: "Crystal Collection",
    price: "$220",
    rating: 4.9,
    reviews: 156,
    image: "https://images.unsplash.com/photo-1654448079061-46d219efffde?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJmdW1lJTIwYm90dGxlJTIwY29sbGVjdGlvbiUyMHdoaXRlJTIwYmFja2dyb3VuZHxlbnwxfHx8fDE3NTgxMjI3MDB8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    description: "A fresh aquatic scent with marine accords and white musk",
    category: "Fresh"
  }
];

export function FeaturedProducts() {
  return (
    <section id="featured" className="py-20 bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h2 className="text-4xl font-serif text-gray-900 mb-4">Featured Fragrances</h2>
          <p className="text-lg text-gray-600 max-w-2xl mx-auto">
            Discover our most beloved scents, carefully selected for their exceptional quality and timeless appeal
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {featuredProducts.map((product) => (
            <Card key={product.id} className="group hover:shadow-xl transition-shadow duration-300 bg-white">
              <CardContent className="p-6">
                <div className="aspect-square mb-6 overflow-hidden rounded-lg bg-gray-100">
                  <ImageWithFallback
                    src={product.image}
                    alt={product.name}
                    className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                  />
                </div>
                
                <div className="space-y-4">
                  <div>
                    <div className="flex items-center justify-between mb-2">
                      <span className="text-sm text-gray-500 bg-gray-100 px-2 py-1 rounded-full">
                        {product.category}
                      </span>
                      <div className="flex items-center text-sm text-gray-600">
                        <Star className="h-4 w-4 fill-yellow-400 text-yellow-400 mr-1" />
                        <span>{product.rating}</span>
                        <span className="ml-1">({product.reviews})</span>
                      </div>
                    </div>
                    <h3 className="text-xl font-semibold text-gray-900 mb-2">{product.name}</h3>
                    <p className="text-gray-600 text-sm leading-relaxed">{product.description}</p>
                  </div>
                  
                  <div className="flex items-center justify-between pt-4 border-t border-gray-100">
                    <span className="text-2xl font-semibold text-gray-900">{product.price}</span>
                    <Button size="sm" className="flex items-center gap-2">
                      <ShoppingCart className="h-4 w-4" />
                      Add to Cart
                    </Button>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>

        <div className="text-center mt-12">
          <Button variant="outline" size="lg" className="px-8">
            View All Fragrances
          </Button>
        </div>
      </div>
    </section>
  );
}