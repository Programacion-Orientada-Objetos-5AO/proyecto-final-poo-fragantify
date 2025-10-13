export interface FragranceNote {
  name: string;
  imageUrl?: string;
}

export interface FragranceRankingItem {
  name: string;
  score?: number;
}

export interface FragranceNotes {
  top?: FragranceNote[];
  middle?: FragranceNote[];
  base?: FragranceNote[];
}

export interface Fragrance {
  id: string;
  name: string;
  brand: string;
  price?: string;
  imageUrl?: string;
  imageFallbacks?: string[];
  gender?: string;
  longevity?: string;
  sillage?: string;
  generalNotes?: string[];
  mainAccords?: string[];
  mainAccordsPercentage?: Record<string, string | number>;
  notes?: FragranceNotes;
  purchaseUrl?: string;
  seasonRanking?: FragranceRankingItem[];
  occasionRanking?: FragranceRankingItem[];
}

export type FragranceApiResponse = Omit<Fragrance, 'id'> & {
  name: string;
  brand: string;
};
