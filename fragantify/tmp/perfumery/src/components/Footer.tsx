import { Facebook, Instagram, Twitter, Mail, Phone, MapPin } from "lucide-react";

export function Footer() {
  return (
    <footer className="bg-black text-white py-12 lg:py-16 border-t border-[#d4af37]/30">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8 lg:gap-12">
          {/* Brand */}
          <div>
            <h3 className="text-2xl font-serif text-[#d4af37] mb-4">Fragrantify</h3>
            <p className="text-gray-300 mb-6 leading-relaxed">
              Descubre fragancias exclusivas creadas con pasión, elegancia y belleza atemporal.
            </p>
            <div className="flex space-x-4">
              <a href="#" className="text-[#d4af37] hover:text-white transition-colors duration-300">
                <Facebook className="h-5 w-5" />
              </a>
              <a href="#" className="text-[#d4af37] hover:text-white transition-colors duration-300">
                <Instagram className="h-5 w-5" />
              </a>
              <a href="#" className="text-[#d4af37] hover:text-white transition-colors duration-300">
                <Twitter className="h-5 w-5" />
              </a>
            </div>
          </div>

          {/* Navigation */}
          <div>
            <h4 className="text-lg text-white mb-4">Navegación</h4>
            <ul className="space-y-2 text-gray-300">
              <li><a href="#home" className="hover:text-[#d4af37] transition-colors duration-300">Inicio</a></li>
              <li><a href="#catalog" className="hover:text-[#d4af37] transition-colors duration-300">Catálogo</a></li>
              <li><a href="#favorites" className="hover:text-[#d4af37] transition-colors duration-300">Favoritos</a></li>
              <li><a href="#compare" className="hover:text-[#d4af37] transition-colors duration-300">Comparar</a></li>
              <li><a href="#about" className="hover:text-[#d4af37] transition-colors duration-300">Acerca de</a></li>
            </ul>
          </div>

          {/* Support */}
          <div>
            <h4 className="text-lg text-white mb-4">Soporte</h4>
            <ul className="space-y-2 text-gray-300">
              <li><a href="#faq" className="hover:text-[#d4af37] transition-colors duration-300">Preguntas Frecuentes</a></li>
              <li><a href="#contact" className="hover:text-[#d4af37] transition-colors duration-300">Contáctanos</a></li>
              <li><a href="#terms" className="hover:text-[#d4af37] transition-colors duration-300">Términos y Condiciones</a></li>
              <li><a href="#privacy" className="hover:text-[#d4af37] transition-colors duration-300">Política de Privacidad</a></li>
            </ul>
          </div>

          {/* Contact */}
          <div>
            <h4 className="text-lg text-white mb-4">Contacto</h4>
            <ul className="space-y-3 text-gray-300">
              <li className="flex items-center gap-2">
                <Mail className="w-4 h-4 text-[#d4af37]" />
                support@fragrantify.com
              </li>
              <li className="flex items-center gap-2">
                <Phone className="w-4 h-4 text-[#d4af37]" />
                +1 (555) 123-4567
              </li>
              <li className="flex items-center gap-2">
                <MapPin className="w-4 h-4 text-[#d4af37]" />
                Buenos Aires, Argentina
              </li>
            </ul>
          </div>
        </div>

        {/* Bottom bar */}
        <div className="mt-8 lg:mt-12 border-t border-[#d4af37]/20 pt-4 lg:pt-6 text-center text-xs sm:text-sm text-gray-400">
          © {new Date().getFullYear()} Fragrantify. Todos los derechos reservados.
        </div>
      </div>
    </footer>
  );
}
