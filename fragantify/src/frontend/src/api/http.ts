const DEFAULT_API_BASE_URL = "http://localhost:8080";

export interface ApiRequestOptions extends RequestInit {
  token?: string;
}

export async function apiFetch<T>(path: string, options: ApiRequestOptions = {}): Promise<T> {
  const { token, headers, ...rest } = options;
  const baseUrl = import.meta.env.VITE_API_BASE_URL ?? DEFAULT_API_BASE_URL;
  const url = path.startsWith("http") ? path : `${baseUrl}${path}`;

  const response = await fetch(url, {
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      ...(headers ?? {}),
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    },
    ...rest,
  });

  if (!response.ok) {
    const contentType = response.headers.get("content-type");
    let errorBody: unknown = null;
    if (contentType && contentType.includes("application/json")) {
      errorBody = await response.json().catch(() => null);
    } else {
      errorBody = await response.text().catch(() => null);
    }

    const error = new Error(`API request failed with status ${response.status}`);
    (error as Error & { status?: number; body?: unknown }).status = response.status;
    (error as Error & { status?: number; body?: unknown }).body = errorBody;
    throw error;
  }

  if (response.status === 204) {
    return undefined as T;
  }

  const contentType = response.headers.get("content-type");
  if (contentType && contentType.includes("application/json")) {
    return response.json() as Promise<T>;
  }

  return response.text() as unknown as T;
}
