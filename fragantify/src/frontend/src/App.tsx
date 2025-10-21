import { useMemo, useState } from "react";
import { Header } from "./components/Header";
import { Hero } from "./components/Hero";
import { PerfumeCatalog } from "./components/PerfumeCatalog";
import { FeaturedProducts } from "./components/FeaturedProducts";
import { Favorites } from "./components/Favorites";
import { Compare } from "./components/Compare";
import { MyCollection } from "./components/MyCollection";
import { PackBuilder } from "./components/PackBuilder";
import { Footer } from "./components/Footer";
import { Login } from "./components/Login";
import { Register } from "./components/Register";
import { useAuth } from "./context/AuthContext";
import type { PageKey } from "./types/navigation";

function App() {
  const { isAuthenticated, user, logout } = useAuth();
  const [currentPage, setCurrentPage] = useState<PageKey>("home");

  const handleAuthSuccess = () => {
    setCurrentPage("home");
  };

  const requireAuth = useMemo(() => (
    <section className="bg-[#0f172a] min-h-[calc(100vh-64px)] flex items-center">
      <div className="max-w-2xl mx-auto text-center px-6">
        <h2 className="text-3xl font-serif text-[#d4af37] mb-4">Inicia sesion para continuar</h2>
        <p className="text-gray-300 mb-6">
          Esta seccion esta disponible para usuarios registrados. Inicia sesion o crea tu cuenta para acceder a tus datos.
        </p>
        <div className="flex justify-center gap-4">
          <button
            onClick={() => setCurrentPage("login")}
            className="bg-[#d4af37] text-black px-6 py-2 rounded-md hover:bg-[#fbbf24] transition-colors"
          >
            Iniciar sesion
          </button>
          <button
            onClick={() => setCurrentPage("register")}
            className="border border-[#d4af37] text-[#d4af37] px-6 py-2 rounded-md hover:bg-[#d4af37]/10 transition-colors"
          >
            Crear cuenta
          </button>
        </div>
      </div>
    </section>
  ), []);

  const focusCatalogSearch = () => {
    window.dispatchEvent(new Event("fragantify:focus-search"));
  };

  const goToCatalog = () => {
    setCurrentPage("catalog");
    focusCatalogSearch();
  };

  const renderPage = () => {
    switch (currentPage) {
      case "home":
        return (
          <>
            <Hero
              onGetStarted={() => setCurrentPage(isAuthenticated ? "catalog" : "register")}
              onLearnMore={goToCatalog}
            />
            <FeaturedProducts />
            <PerfumeCatalog />
          </>
        );
      case "catalog":
        return (
          <section className="bg-[#0f172a] py-16 min-h-[calc(100vh-64px)]">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <PerfumeCatalog />
            </div>
          </section>
        );
      case "favorites":
        return isAuthenticated ? (
          <section className="bg-[#0f172a] py-16 min-h-[calc(100vh-64px)]">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <Favorites />
            </div>
          </section>
        ) : requireAuth;
      case "compare":
        return isAuthenticated ? (
          <section className="bg-[#0f172a] py-16 min-h-[calc(100vh-64px)]">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <Compare />
            </div>
          </section>
        ) : requireAuth;
      case "collection":
        return isAuthenticated ? (
          <section className="bg-[#0f172a] py-16 min-h-[calc(100vh-64px)]">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <MyCollection />
            </div>
          </section>
        ) : requireAuth;
      case "pack-builder":
        return isAuthenticated ? (
          <section className="bg-black py-16 min-h-[calc(100vh-64px)]">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <PackBuilder />
            </div>
          </section>
        ) : requireAuth;
      case "login":
        return <Login onSuccess={handleAuthSuccess} />;
      case "register":
        return <Register onSuccess={handleAuthSuccess} />;
      default:
        return (
          <>
            <Hero
              onGetStarted={() => setCurrentPage(isAuthenticated ? "catalog" : "register")}
              onLearnMore={goToCatalog}
            />
            <FeaturedProducts />
            <PerfumeCatalog />
          </>
        );
    }
  };

  return (
    <div className="bg-[#0f172a] text-white min-h-screen flex flex-col">
      <Header
        currentPage={currentPage}
        onNavigate={setCurrentPage}
        isAuthenticated={isAuthenticated}
        userEmail={user?.username}
        onLogout={logout}
      />
      <main className="flex-grow">
        {renderPage()}
      </main>
      <Footer />
    </div>
  );
}

export default App;
