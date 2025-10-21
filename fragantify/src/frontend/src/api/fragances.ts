import { apiFetch } from "./http";
import type { FragranceApiResponse } from "../types/fragrance";

export interface FragranceFilters {
  brand?: string;
  name?: string;
  gender?: string;
}

function buildQuery(params?: FragranceFilters): string {
  if (!params) {
    return "";
  }

  const query = new URLSearchParams();
  Object.entries(params).forEach(([key, value]) => {
    if (value) {
      query.append(key, value);
    }
  });

  const queryString = query.toString();
  return queryString ? `?${queryString}` : "";
}

export function getFragrances(filters?: FragranceFilters) {
  const query = buildQuery(filters);
  return apiFetch<FragranceApiResponse[]>(`/api/perfumes/externos${query}`);
}
