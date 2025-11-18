import { Facebook, Instagram, Twitter, Mail, Phone, MapPin } from "lucide-react";
import type { PageKey } from "../types/navigation";

interface FooterProps {
  onNavigate?: (page: PageKey) => void;
}

export function Footer({ onNavigate }: FooterProps) {
  return (
    <footer className="bg-black text-white py-12 lg:py-16 border-t border-[#d4af37]/30">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8 lg:gap-12">
          <div>
            <h3 className="text-2xl font-serif text-[#d4af37] mb-4">Fragrantify</h3>
            <p className="text-gray-300 mb-6 leading-relaxed">
              Descubre fragancias exclusivas creadas con pasión, elegancia y belleza atemporal.
            </p>
            <div className="flex space-x-4">
              <a href="https://facebook.com" target="_blank" rel="noreferrer" className="text-[#d4af37] hover:text-white transition-colors duration-300">
                <Facebook className="h-5 w-5" />
              </a>
              <a href="https://instagram.com" target="_blank" rel="noreferrer" className="text-[#d4af37] hover:text-white transition-colors duration-300">
                <Instagram className="h-5 w-5" />
              </a>
              <a href="https://twitter.com" target="_blank" rel="noreferrer" className="text-[#d4af37] hover:text-white transition-colors duration-300">
                <Twitter className="h-5 w-5" />
              </a>
            </div>
          </div>

          <div>
            <h4 className="text-lg text-white mb-4">Navegación</h4>
            <ul className="space-y-2 text-gray-300">
              <li>
                <button onClick={() => onNavigate?.("home")} className="hover:text-[#d4af37] transition-colors duration-300">
                  Inicio
                </button>
              </li>
              <li>
                <button onClick={() => onNavigate?.("catalog")} className="hover:text-[#d4af37] transition-colors duration-300">
                  Catálogo
                </button>
              </li>
              <li>
                <button onClick={() => onNavigate?.("favorites")} className="hover:text-[#d4af37] transition-colors duración-300">
                  Favoritos
                </button>
              </li>
              <li>
                <button onClick={() => onNavigate?.("compare")} className="hover:text-[#d4af37] transition-colors duración-300">
                  Comparar
                </button>
              </li>
              <li>
                <button onClick={() => onNavigate?.("collection")} className="hover:text-[#d4af37] transition-colors duration-300">
                  Colección
                </button>
              </li>
            </ul>
          </div>

          <div>
            <h4 className="text-lg text-white mb-4">Soporte</h4>
            <ul className="space-y-2 text-gray-300">
              <li><a href="https://support.fragrantify.com/faq" target="_blank" rel="noreferrer" className="hover:text-[#d4af37] transition-colors duration-300">Preguntas frecuentes</a></li>
              <li><a href="mailto:soporte@fragrantify.com" className="hover:text-[#d4af37] transition-colors duration-300">Contáctanos</a></li>
              <li><a href="https://support.fragrantify.com/terms" target="_blank" rel="noreferrer" className="hover:text-[#d4af37] transition-colors duración-300">Términos y condiciones</a></li>
              <li><a href="https://support.fragrantify.com/privacy" target="_blank" rel="noreferrer" className="hover:text-[#d4af37] transition-colors duración-300">Política de privacidad</a></li>
            </ul>
          </div>

          <div>
            <h4 className="text-lg text-white mb-4">Contacto</h4>
            <ul className="space-y-3 text-gray-300">
              <li className="flex items-center gap-2">
                <Mail className="w-4 h-4 text-[#d4af37]" />
                <a href="mailto:soporte@fragrantify.com" className="hover:text-[#d4af37] transition-colors duration-300">
                  soporte@fragrantify.com
                </a>
              </li>
              <li className="flex items-center gap-2">
                <Phone className="w-4 h-4 text-[#d4af37]" />
                <a href="tel:+15551234567" className="hover:text-[#d4af37] transition-colors duration-300">
                  +1 (555) 123-4567
                </a>
              </li>
              <li className="flex items-center gap-2">
                <MapPin className="w-4 h-4 text-[#d4af37]" />
                Buenos Aires, Argentina
              </li>
            </ul>
          </div>
        </div>

        <div className="mt-8 lg:mt-12 border-t border-[#d4af37]/20 pt-4 lg:pt-6 text-center text-xs sm:text-sm text-gray-400">
          © {new Date().getFullYear()} Fragrantify. Todos los derechos reservados.
        </div>
      </div>
    </footer>
  );
}
