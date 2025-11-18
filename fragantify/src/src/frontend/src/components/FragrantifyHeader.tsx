import { Button } from "./ui/button";
import { Badge } from "./ui/badge";
import { Avatar, AvatarFallback, AvatarImage } from "./ui/avatar";
import { 
  Search, 
  Heart, 
  Star, 
  BarChart3, 
  ShoppingBag, 
  Bell,
  Settings,
  Crown,
  Target
} from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "./ui/dropdown-menu";

interface FragrantifyHeaderProps {
  activeSection: string;
  onSectionChange: (section: string) => void;
}

export function FragrantifyHeader({ activeSection, onSectionChange }: FragrantifyHeaderProps) {
  return (
    <header className="bg-white border-b border-border sticky top-0 z-50 shadow-sm">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          {/* Logo */}
          <div className="flex items-center">
            <div className="flex items-center gap-2">
              <div className="w-8 h-8 bg-gradient-to-r from-primary to-chart-2 rounded-lg flex items-center justify-center">
                <span className="text-white font-bold text-sm">F</span>
              </div>
              <h1 className="text-xl font-semibold text-foreground">Fragrantify</h1>
            </div>
          </div>

          {/* Navigation */}
          <nav className="hidden md:flex items-center space-x-6">
            <button
              onClick={() => onSectionChange('dashboard')}
              className={`flex items-center gap-2 px-3 py-2 rounded-md transition-colors ${
                activeSection === 'dashboard' 
                  ? 'bg-primary text-primary-foreground' 
                  : 'text-muted-foreground hover:text-foreground hover:bg-muted'
              }`}
            >
              <BarChart3 className="h-4 w-4" />
              Dashboard
            </button>
            
            <button
              onClick={() => onSectionChange('catalog')}
              className={`flex items-center gap-2 px-3 py-2 rounded-md transition-colors ${
                activeSection === 'catalog' 
                  ? 'bg-primary text-primary-foreground' 
                  : 'text-muted-foreground hover:text-foreground hover:bg-muted'
              }`}
            >
              <Search className="h-4 w-4" />
              Catálogo
            </button>
            
            <button
              onClick={() => onSectionChange('favorites')}
              className={`flex items-center gap-2 px-3 py-2 rounded-md transition-colors ${
                activeSection === 'favorites' 
                  ? 'bg-primary text-primary-foreground' 
                  : 'text-muted-foreground hover:text-foreground hover:bg-muted'
              }`}
            >
              <Heart className="h-4 w-4" />
              Favoritos
            </button>
            
            <button
              onClick={() => onSectionChange('compare')}
              className={`flex items-center gap-2 px-3 py-2 rounded-md transition-colors ${
                activeSection === 'compare' 
                  ? 'bg-primary text-primary-foreground' 
                  : 'text-muted-foreground hover:text-foreground hover:bg-muted'
              }`}
            >
              <BarChart3 className="h-4 w-4" />
              Comparar
            </button>
            
            <button
              onClick={() => onSectionChange('collection')}
              className={`flex items-center gap-2 px-3 py-2 rounded-md transition-colors ${
                activeSection === 'collection' 
                  ? 'bg-primary text-primary-foreground' 
                  : 'text-muted-foreground hover:text-foreground hover:bg-muted'
              }`}
            >
              <ShoppingBag className="h-4 w-4" />
              Mi Colección
            </button>
            
            <button
              onClick={() => onSectionChange('packbuilder')}
              className={`flex items-center gap-2 px-3 py-2 rounded-md transition-colors ${
                activeSection === 'packbuilder' 
                  ? 'bg-primary text-primary-foreground' 
                  : 'text-muted-foreground hover:text-foreground hover:bg-muted'
              }`}
            >
              <Target className="h-4 w-4" />
              Pack Builder
            </button>
          </nav>

          {/* Actions */}
          <div className="flex items-center space-x-4">
            {/* Premium Badge */}
            <Badge variant="secondary" className="bg-gradient-to-r from-chart-2 to-chart-3 text-white border-0">
              <Crown className="h-3 w-3 mr-1" />
              Premium
            </Badge>

            {/* Notifications */}
            <Button variant="ghost" size="sm" className="p-2 relative">
              <Bell className="h-5 w-5" />
              <span className="absolute -top-1 -right-1 h-4 w-4 bg-red-500 text-white text-xs rounded-full flex items-center justify-center">
                3
              </span>
            </Button>

            {/* User Menu */}
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" className="p-0 h-auto">
                  <Avatar className="h-8 w-8">
                    <AvatarImage src="/api/placeholder/32/32" alt="Usuario" />
                    <AvatarFallback>JD</AvatarFallback>
                  </Avatar>
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end" className="w-56">
                <DropdownMenuLabel>Mi Cuenta</DropdownMenuLabel>
                <DropdownMenuSeparator />
                <DropdownMenuItem>
                  <Settings className="mr-2 h-4 w-4" />
                  Configuración
                </DropdownMenuItem>
                <DropdownMenuItem>
                  <Star className="mr-2 h-4 w-4" />
                  Mis Reseñas
                </DropdownMenuItem>
                <DropdownMenuSeparator />
                <DropdownMenuItem>
                  Cerrar Sesión
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </div>
        </div>
      </div>
    </header>
  );
}