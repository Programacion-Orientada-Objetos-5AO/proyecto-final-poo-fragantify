import { apiFetch } from "./http";
import type { AuthResponse } from "../types/auth";

interface Credentials {
  username: string;
  password: string;
}

interface RegisterPayload extends Credentials {
  verificacionPassword: string;
}

export function login(payload: Credentials): Promise<AuthResponse> {
  return apiFetch<AuthResponse>("/api/auth/login", {
    method: "POST",
    body: JSON.stringify(payload),
  });
}

export function register(payload: RegisterPayload): Promise<AuthResponse> {
  return apiFetch<AuthResponse>("/api/auth/register", {
    method: "POST",
    body: JSON.stringify(payload),
  });
}
