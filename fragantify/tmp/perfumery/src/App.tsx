import { useState } from "react";
import { Header } from "./components/Header";
import { Hero } from "./components/Hero";
import { PerfumeCatalog } from "./components/PerfumeCatalog";
import { Favorites } from "./components/Favorites";
import { Compare } from "./components/Compare";
import { MyCollection } from "./components/MyCollection";
import { PackBuilder } from "./components/PackBuilder";
import { Footer } from "./components/Footer";

function App() {
  const [currentPage, setCurrentPage] = useState('home');

  const renderPage = () => {
    switch (currentPage) {
      case 'home':
        return (
          <>
            <Hero />
            <PerfumeCatalog />
          </>
        );
      case 'catalog':
        return (
          <section className="bg-[#0f172a] py-16 min-h-[calc(100vh-64px)]">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <PerfumeCatalog />
            </div>
          </section>
        );
      case 'favorites':
        return (
          <section className="bg-[#0f172a] py-16 min-h-[calc(100vh-64px)]">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <Favorites />
            </div>
          </section>
        );
      case 'compare':
        return (
          <section className="bg-[#0f172a] py-16 min-h-[calc(100vh-64px)]">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <Compare />
            </div>
          </section>
        );
      case 'collection':
        return (
          <section className="bg-[#0f172a] py-16 min-h-[calc(100vh-64px)]">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <MyCollection />
            </div>
          </section>
        );
      case 'pack-builder':
        return (
          <section className="bg-black py-16 min-h-[calc(100vh-64px)]">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <PackBuilder />
            </div>
          </section>
        );
      default:
        return (
          <>
            <Hero />
            <PerfumeCatalog />
          </>
        );
    }
  };

  return (
    <div className="bg-[#0f172a] text-white min-h-screen flex flex-col">
      {/* Header - Negro con navegación */}
      <Header currentPage={currentPage} onNavigate={setCurrentPage} />

      {/* Main Content - Página actual */}
      <main className="flex-grow">
        {renderPage()}
      </main>

      {/* Footer */}
      <Footer />
    </div>
  );
}

export default App;
