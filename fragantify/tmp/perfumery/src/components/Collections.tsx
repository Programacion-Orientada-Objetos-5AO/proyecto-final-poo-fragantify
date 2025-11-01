import { Card, CardContent } from "./ui/card";
import { Button } from "./ui/button";
import { ImageWithFallback } from "./figma/ImageWithFallback";

const collections = [
  {
    id: 1,
    name: "Luxury Collection",
    description: "Premium fragrances crafted with the rarest ingredients",
    image: "https://images.unsplash.com/photo-1655500061669-1f8ac338a319?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsdXh1cnklMjBwZXJmdW1lJTIwYm90dGxlcyUyMGVsZWdhbnR8ZW58MXx8fHwxNzU4MDQ2MDIzfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    productCount: "12 Products"
  },
  {
    id: 2,
    name: "Botanical Series",
    description: "Natural scents inspired by gardens and flowers",
    image: "https://images.unsplash.com/photo-1542461606-241f9dc580dc?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJmdW1lJTIwaW5ncmVkaWVudHMlMjBmbG93ZXJzfGVufDF8fHx8MTc1ODExNjM4MHww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    productCount: "8 Products"
  }
];

export function Collections() {
  return (
    <section id="collections" className="py-20 bg-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h2 className="text-4xl font-serif text-gray-900 mb-4">Our Collections</h2>
          <p className="text-lg text-gray-600 max-w-2xl mx-auto">
            Explore our carefully curated collections, each telling a unique olfactory story
          </p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {collections.map((collection) => (
            <Card key={collection.id} className="group overflow-hidden hover:shadow-2xl transition-shadow duration-300">
              <CardContent className="p-0">
                <div className="relative h-80">
                  <ImageWithFallback
                    src={collection.image}
                    alt={collection.name}
                    className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
                  />
                  <div className="absolute inset-0 bg-black bg-opacity-40 group-hover:bg-opacity-50 transition-opacity duration-300"></div>
                  
                  <div className="absolute inset-0 flex items-end p-8">
                    <div className="text-white">
                      <p className="text-sm text-gray-200 mb-2">{collection.productCount}</p>
                      <h3 className="text-3xl font-serif mb-3">{collection.name}</h3>
                      <p className="text-gray-200 mb-6 leading-relaxed">{collection.description}</p>
                      <Button 
                        variant="outline" 
                        className="border-white text-white hover:bg-white hover:text-gray-900"
                      >
                        Explore Collection
                      </Button>
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  );
}