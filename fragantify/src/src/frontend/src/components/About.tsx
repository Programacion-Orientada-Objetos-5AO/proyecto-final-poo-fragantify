import { Button } from "./ui/button";
import { Sparkles, Leaf, Award } from "lucide-react";

const features = [
  {
    icon: Sparkles,
    title: "Artisanal Craftsmanship",
    description: "Each fragrance is meticulously crafted by master perfumers with decades of experience"
  },
  {
    icon: Leaf,
    title: "Sustainable Ingredients",
    description: "We source only the finest natural ingredients through ethical and sustainable practices"
  },
  {
    icon: Award,
    title: "Award-Winning Quality",
    description: "Our fragrances have received numerous international awards for their exceptional quality"
  }
];

export function About() {
  return (
    <section id="about" className="py-20 bg-gray-900 text-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-16 items-center">
          {/* Content */}
          <div>
            <h2 className="text-4xl font-serif mb-6">The Art of Fragrance</h2>
            <p className="text-xl text-gray-300 mb-8 leading-relaxed">
              For over three decades, we have been dedicated to creating exceptional fragrances 
              that capture the essence of luxury and sophistication. Our passion for perfumery 
              drives us to explore new olfactory territories while honoring traditional techniques.
            </p>
            
            <div className="space-y-6 mb-8">
              {features.map((feature, index) => (
                <div key={index} className="flex items-start gap-4">
                  <div className="flex-shrink-0 w-12 h-12 bg-gradient-to-r from-yellow-400 to-orange-400 rounded-full flex items-center justify-center">
                    <feature.icon className="h-6 w-6 text-gray-900" />
                  </div>
                  <div>
                    <h3 className="font-semibold mb-2">{feature.title}</h3>
                    <p className="text-gray-400">{feature.description}</p>
                  </div>
                </div>
              ))}
            </div>
            
            <Button size="lg" className="bg-gradient-to-r from-yellow-400 to-orange-400 text-gray-900 hover:from-yellow-500 hover:to-orange-500">
              Our Story
            </Button>
          </div>

          {/* Statistics */}
          <div className="grid grid-cols-2 gap-8">
            <div className="text-center">
              <div className="text-4xl font-serif mb-2 text-gradient bg-gradient-to-r from-yellow-400 to-orange-400 bg-clip-text text-transparent">
                30+
              </div>
              <p className="text-gray-400">Years of Excellence</p>
            </div>
            <div className="text-center">
              <div className="text-4xl font-serif mb-2 text-gradient bg-gradient-to-r from-yellow-400 to-orange-400 bg-clip-text text-transparent">
                150+
              </div>
              <p className="text-gray-400">Unique Fragrances</p>
            </div>
            <div className="text-center">
              <div className="text-4xl font-serif mb-2 text-gradient bg-gradient-to-r from-yellow-400 to-orange-400 bg-clip-text text-transparent">
                50k+
              </div>
              <p className="text-gray-400">Happy Customers</p>
            </div>
            <div className="text-center">
              <div className="text-4xl font-serif mb-2 text-gradient bg-gradient-to-r from-yellow-400 to-orange-400 bg-clip-text text-transparent">
                25+
              </div>
              <p className="text-gray-400">Countries Worldwide</p>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}