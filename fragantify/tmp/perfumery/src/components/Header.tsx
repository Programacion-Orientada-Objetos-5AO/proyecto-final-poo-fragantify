import { Search, ShoppingBag, User, Menu, X } from "lucide-react";
import { useState } from "react";

interface HeaderProps {
  currentPage: string;
  onNavigate: (page: string) => void;
}

export function Header({ currentPage, onNavigate }: HeaderProps) {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  
  const navItems = [
    { id: 'home', label: 'Inicio' },
    { id: 'catalog', label: 'Catálogo' },
    { id: 'favorites', label: 'Favoritos' },
    { id: 'compare', label: 'Comparar' },
    { id: 'collection', label: 'Colección' },
    { id: 'pack-builder', label: 'Pack Builder' }
  ];

  const handleNavigate = (page: string) => {
    onNavigate(page);
    setMobileMenuOpen(false);
  };

  return (
    <header className="bg-black sticky top-0 z-50 shadow-lg border-b border-gray-800">
      <div className="max-w-7xl mx-auto px-3 sm:px-4 lg:px-6">
        <div className="flex items-center justify-between h-16">
          {/* Logo */}
          <div className="flex items-center flex-shrink-0">
            <button 
              onClick={() => handleNavigate('home')}
              className="text-xl sm:text-2xl font-serif text-[#d4af37] tracking-wide hover:text-[#fbbf24] transition-colors duration-300"
            >
              Fragrantify
            </button>
          </div>

          {/* Desktop Navigation */}
          <nav className="hidden md:flex items-center space-x-3 lg:space-x-4 xl:space-x-6">
            {navItems.map((item) => (
              <button
                key={item.id}
                onClick={() => handleNavigate(item.id)}
                className={`text-xs md:text-sm lg:text-base px-2 py-1 rounded transition-colors duration-300 whitespace-nowrap ${
                  currentPage === item.id 
                    ? 'text-[#d4af37] bg-[#d4af37]/10' 
                    : 'text-white hover:text-[#d4af37] hover:bg-[#d4af37]/5'
                }`}
              >
                {item.label}
              </button>
            ))}
          </nav>

          {/* Desktop Icons + Premium */}
          <div className="hidden sm:flex items-center space-x-3 lg:space-x-4">
            <Search className="w-4 h-4 lg:w-5 lg:h-5 text-[#d4af37] cursor-pointer hover:scale-110 transition-transform duration-300" />
            <ShoppingBag className="w-4 h-4 lg:w-5 lg:h-5 text-white cursor-pointer hover:text-[#d4af37] transition-colors duration-300" />
            <User className="w-4 h-4 lg:w-5 lg:h-5 text-white cursor-pointer hover:text-[#d4af37] transition-colors duration-300" />

            {/* Premium Button */}
            <button className="ml-2 bg-[#d4af37] text-black px-2 lg:px-3 py-1.5 text-xs lg:text-sm rounded-md hover:bg-[#fbbf24] transition-colors duration-300 shadow-md">
              Premium
            </button>
          </div>

          {/* Mobile menu button */}
          <div className="sm:hidden flex items-center space-x-3">
            <Search className="w-5 h-5 text-[#d4af37] cursor-pointer" />
            <button
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
              className="text-white hover:text-[#d4af37] transition-colors"
            >
              {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
            </button>
          </div>
        </div>

        {/* Mobile Navigation Menu */}
        {mobileMenuOpen && (
          <div className="sm:hidden border-t border-gray-800">
            <div className="px-2 pt-2 pb-3 space-y-1">
              {navItems.map((item) => (
                <button
                  key={item.id}
                  onClick={() => handleNavigate(item.id)}
                  className={`block w-full text-left px-3 py-2 rounded-md text-base transition-colors duration-300 ${
                    currentPage === item.id 
                      ? 'text-[#d4af37] bg-[#d4af37]/10' 
                      : 'text-white hover:text-[#d4af37] hover:bg-[#d4af37]/5'
                  }`}
                >
                  {item.label}
                </button>
              ))}
              <div className="flex items-center justify-between pt-4 mt-4 border-t border-gray-800">
                <div className="flex items-center space-x-4">
                  <ShoppingBag className="w-5 h-5 text-white cursor-pointer" />
                  <User className="w-5 h-5 text-white cursor-pointer" />
                </div>
                <button className="bg-[#d4af37] text-black px-4 py-2 text-sm rounded-md">
                  Premium
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </header>
  );
}
