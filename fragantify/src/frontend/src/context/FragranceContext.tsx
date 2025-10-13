import { createContext, useCallback, useContext, useEffect, useMemo, useState } from "react";
import type { Fragrance, FragranceApiResponse } from "../types/fragrance";
import { getFragrances } from "../api/fragances";

interface FragranceContextValue {
  fragrances: Fragrance[];
  loading: boolean;
  error: string | null;
  refresh: () => Promise<void>;
  favorites: string[];
  toggleFavorite: (id: string) => void;
  isFavorite: (id: string) => boolean;
  compareList: string[];
  addToCompare: (id: string) => void;
  removeFromCompare: (id: string) => void;
  isInCompare: (id: string) => boolean;
  getById: (id: string) => Fragrance | undefined;
}

const FragranceContext = createContext<FragranceContextValue | undefined>(undefined);

const MAX_COMPARE_ITEMS = 3;

function slugify(value: string) {
  return value
    .toLowerCase()
    .replace(/[^a-z0-9]+/g, "-")
    .replace(/^-+|-+$/g, "");
}

function normalizeFragrances(data: FragranceApiResponse[]): Fragrance[] {
  const seen = new Map<string, number>();

  return data.map((item) => {
    const baseSlug = slugify(`${item.brand ?? "unknown"}-${item.name ?? "fragrance"}`);
    const count = seen.get(baseSlug) ?? 0;
    seen.set(baseSlug, count + 1);
    const id = count === 0 ? baseSlug : `${baseSlug}-${count + 1}`;

    return {
      id,
      name: item.name ?? "Fragrance",
      brand: item.brand ?? "",
      price: item.price,
      imageUrl: item.imageUrl,
      imageFallbacks: item.imageFallbacks,
      gender: item.gender,
      longevity: item.longevity,
      sillage: item.sillage,
      generalNotes: item.generalNotes,
      mainAccords: item.mainAccords,
      mainAccordsPercentage: item.mainAccordsPercentage,
      notes: item.notes,
      purchaseUrl: item.purchaseUrl,
      seasonRanking: item.seasonRanking,
      occasionRanking: item.occasionRanking,
    } satisfies Fragrance;
  });
}

export function FragranceProvider({ children }: { children: React.ReactNode }) {
  const [fragrances, setFragrances] = useState<Fragrance[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [favorites, setFavorites] = useState<string[]>([]);
  const [compareList, setCompareList] = useState<string[]>([]);

  const fetchData = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getFragrances();
      setFragrances(normalizeFragrances(data ?? []));
    } catch (err) {
      console.error("Unable to load fragrances", err);
      setError("No pudimos cargar las fragancias. Intenta nuevamente mas tarde.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const toggleFavorite = useCallback((id: string) => {
    setFavorites((prev) =>
      prev.includes(id) ? prev.filter((value) => value !== id) : [...prev, id]
    );
  }, []);

  const addToCompare = useCallback((id: string) => {
    setCompareList((prev) => {
      if (prev.includes(id)) {
        return prev;
      }
      const next = [...prev, id];
      if (next.length > MAX_COMPARE_ITEMS) {
        next.shift();
      }
      return next;
    });
  }, []);

  const removeFromCompare = useCallback((id: string) => {
    setCompareList((prev) => prev.filter((value) => value !== id));
  }, []);

  const value = useMemo<FragranceContextValue>(() => ({
    fragrances,
    loading,
    error,
    refresh: fetchData,
    favorites,
    toggleFavorite,
    isFavorite: (id: string) => favorites.includes(id),
    compareList,
    addToCompare,
    removeFromCompare,
    isInCompare: (id: string) => compareList.includes(id),
    getById: (id: string) => fragrances.find((item) => item.id === id),
  }), [fragrances, loading, error, fetchData, favorites, compareList, toggleFavorite, addToCompare, removeFromCompare]);

  return <FragranceContext.Provider value={value}>{children}</FragranceContext.Provider>;
}

export function useFragrances() {
  const context = useContext(FragranceContext);
  if (!context) {
    throw new Error("useFragrances must be used within a FragranceProvider");
  }
  return context;
}
