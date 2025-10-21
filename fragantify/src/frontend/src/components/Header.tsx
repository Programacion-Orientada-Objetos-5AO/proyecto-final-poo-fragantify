import { useState } from "react";
import { Search, ShoppingBag, User, Menu, X, LogOut, LogIn, UserPlus } from "lucide-react";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuSeparator, DropdownMenuTrigger } from "./ui/dropdown-menu";
import { Button } from "./ui/button";
import type { PageKey } from "../types/navigation";

interface HeaderProps {
  currentPage: PageKey;
  onNavigate: (page: PageKey) => void;
  isAuthenticated: boolean;
  userEmail?: string;
  onLogout?: () => void;
}

export function Header({ currentPage, onNavigate, isAuthenticated, userEmail, onLogout }: HeaderProps) {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const navItems: { id: PageKey; label: string }[] = [
    { id: "home", label: "Inicio" },
    { id: "catalog", label: "Catalogo" },
    { id: "favorites", label: "Favoritos" },
    { id: "compare", label: "Comparar" },
    { id: "collection", label: "Coleccion" },
    { id: "pack-builder", label: "Pack Builder" },
  ];

  const handleNavigate = (page: PageKey) => {
    onNavigate(page);
    setMobileMenuOpen(false);
  };

  return (
    <header className="bg-black sticky top-0 z-50 shadow-lg border-b border-gray-800">
      <div className="max-w-7xl mx-auto px-3 sm:px-4 lg:px-6">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center flex-shrink-0">
            <button
              onClick={() => handleNavigate("home")}
              className="text-xl sm:text-2xl font-serif text-[#d4af37] tracking-wide hover:text-[#fbbf24] transition-colors duration-300"
            >
              Fragrantify
            </button>
          </div>

          <nav className="hidden md:flex items-center space-x-3 lg:space-x-4 xl:space-x-6">
            {navItems.map((item) => (
              <button
                key={item.id}
                onClick={() => handleNavigate(item.id)}
                className={`text-xs md:text-sm lg:text-base px-2 py-1 rounded transition-colors duration-300 whitespace-nowrap ${
                  currentPage === item.id
                    ? "text-[#d4af37] bg-[#d4af37]/10"
                    : "text-white hover:text-[#d4af37] hover:bg-[#d4af37]/5"
                }`}
              >
                {item.label}
              </button>
            ))}
          </nav>

          <div className="hidden sm:flex items-center space-x-3 lg:space-x-4">
            <Search
              className="w-4 h-4 lg:w-5 lg:h-5 text-[#d4af37] cursor-pointer hover:scale-110 transition-transform duration-300"
              onClick={() => {
                handleNavigate("catalog");
                window.dispatchEvent(new Event("fragantify:focus-search"));
              }}
            />
            <ShoppingBag
              className="w-4 h-4 lg:w-5 lg:h-5 text-white cursor-pointer hover:text-[#d4af37] transition-colors duration-300"
              onClick={() => handleNavigate("pack-builder")}
            />

            {isAuthenticated ? (
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button variant="ghost" className="text-white hover:text-[#d4af37] px-2">
                    <User className="w-4 h-4 lg:w-5 lg:h-5" />
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="end" className="w-56">
                  <div className="px-2 py-1 text-xs text-muted-foreground">{userEmail}</div>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem onClick={() => handleNavigate("collection")}>Mi coleccion</DropdownMenuItem>
                  <DropdownMenuItem onClick={() => handleNavigate("favorites")}>Mis favoritos</DropdownMenuItem>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem
                    onClick={() => {
                      onLogout?.();
                      handleNavigate("home");
                    }}
                  >
                    <LogOut className="w-4 h-4 mr-2" /> Cerrar sesion
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            ) : (
              <div className="flex items-center gap-2">
                <Button
                  variant="ghost"
                  className="text-white hover:text-[#d4af37]"
                  onClick={() => handleNavigate("login")}
                >
                  <LogIn className="w-4 h-4 mr-1" /> Ingresar
                </Button>
                <Button
                  className="bg-[#d4af37] text-black px-3 py-1.5 text-sm rounded-md hover:bg-[#fbbf24] transition-colors duration-300 shadow-md"
                  onClick={() => handleNavigate("register")}
                >
                  <UserPlus className="w-4 h-4 mr-1" /> Registro
                </Button>
              </div>
            )}
          </div>

          <div className="sm:hidden flex items-center space-x-3">
            <Search
              className="w-5 h-5 text-[#d4af37] cursor-pointer"
              onClick={() => {
                handleNavigate("catalog");
                window.dispatchEvent(new Event("fragantify:focus-search"));
              }}
            />
            <button
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
              className="text-white hover:text-[#d4af37] transition-colors"
            >
              {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
            </button>
          </div>
        </div>

        {mobileMenuOpen && (
          <div className="sm:hidden border-t border-gray-800">
            <div className="px-2 pt-2 pb-3 space-y-1">
              {navItems.map((item) => (
                <button
                  key={item.id}
                  onClick={() => handleNavigate(item.id)}
                  className={`block w-full text-left px-3 py-2 rounded-md text-base transition-colors duration-300 ${
                    currentPage === item.id
                      ? "text-[#d4af37] bg-[#d4af37]/10"
                      : "text-white hover:text-[#d4af37] hover:bg-[#d4af37]/5"
                  }`}
                >
                  {item.label}
                </button>
              ))}

              <div className="border-t border-gray-800 pt-4 mt-4 space-y-2">
                {isAuthenticated ? (
                  <button
                    onClick={() => {
                      onLogout?.();
                      handleNavigate("home");
                    }}
                    className="w-full flex items-center justify-center gap-2 bg-[#d4af37] text-black px-4 py-2 rounded-md"
                  >
                    <LogOut className="w-4 h-4" /> Cerrar sesion
                  </button>
                ) : (
                  <div className="flex flex-col gap-2">
                    <button
                      onClick={() => handleNavigate("login")}
                      className="w-full flex items-center justify-center gap-2 border border-[#d4af37] text-[#d4af37] px-4 py-2 rounded-md"
                    >
                      <LogIn className="w-4 h-4" /> Iniciar sesion
                    </button>
                    <button
                      onClick={() => handleNavigate("register")}
                      className="w-full flex items-center justify-center gap-2 bg-[#d4af37] text-black px-4 py-2 rounded-md"
                    >
                      <UserPlus className="w-4 h-4" /> Registrarse
                    </button>
                  </div>
                )}
              </div>
            </div>
          </div>
        )}
      </div>
    </header>
  );
}

