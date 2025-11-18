import { Button } from "./ui/button";
import { ImageWithFallback } from "./figma/ImageWithFallback";

interface HeroProps {
  onGetStarted?: () => void;
}

export function Hero({ onGetStarted }: HeroProps) {
  return (
    <section
      id="home"
      className="relative min-h-screen flex items-center bg-gradient-to-br from-[#0f172a] via-black to-[#1e293b]"
    >
      <div className="absolute inset-0 z-0">
        <ImageWithFallback
          src="https://images.unsplash.com/photo-1594035910387-fea47794261f?auto=format&fit=crop&w=1600&q=80"
          alt="Luxury perfume background"
          className="w-full h-full object-cover opacity-15"
        />
        <div className="absolute inset-0 bg-gradient-to-b from-black/95 via-[#0f172a]/90 to-[#0f172a]/85"></div>
      </div>

      <div className="relative z-10 max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h1 className="text-4xl sm:text-5xl lg:text-6xl font-serif text-white drop-shadow-2xl leading-tight">
          Descubre el arte de la
          <span className="text-[#d4af37] block mt-2">perfumeria</span>
        </h1>
        <p className="mt-4 sm:mt-6 text-base sm:text-lg text-gray-200 max-w-2xl mx-auto px-4">
          Explora fragancias exclusivas creadas con pasion y elegancia.
        </p>
        <div className="mt-6 sm:mt-8 flex flex-col sm:flex-row justify-center gap-3 sm:gap-4 px-4">
          <Button
            onClick={onGetStarted}
            className="bg-[#d4af37] text-black px-6 sm:px-8 py-3 sm:py-4 text-base sm:text-lg rounded-lg hover:bg-[#fbbf24] transition-all duration-300 shadow-lg hover:shadow-xl transform hover:scale-105"
          >
            Explorar catalogo
          </Button>
          <Button className="bg-transparent border-2 border-[#d4af37] text-[#d4af37] px-6 sm:px-8 py-3 sm:py-4 text-base sm:text-lg rounded-lg hover:bg-[#d4af37] hover:text-black transition-all duration-300 shadow-lg">
            Conocer mas
          </Button>
        </div>
      </div>
    </section>
  );
}
