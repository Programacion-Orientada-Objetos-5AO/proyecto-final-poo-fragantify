export function normalizePrice(value?: string): number | null {
  if (!value) {
    return null;
  }
  const cleaned = value.replace(/[^0-9.,]/g, "").replace(/,/g, ".");
  const parsed = parseFloat(cleaned);
  return Number.isFinite(parsed) ? parsed : null;
}

export function formatPrice(value?: string): string {
  if (!value || value.trim().length === 0) {
    return "Precio no disponible";
  }
  return value;
}
