import { createContext, useCallback, useContext, useEffect, useMemo, useState } from "react";
import type { AuthResponse, User } from "../types/auth";
import { login as loginRequest, register as registerRequest } from "../api/auth";

const STORAGE_KEY = "fragantify-auth";

interface AuthState {
  user: User | null;
  token: string | null;
}

interface AuthContextValue extends AuthState {
  isAuthenticated: boolean;
  login: (payload: { username: string; password: string }) => Promise<void>;
  register: (payload: { username: string; password: string; verificacionPassword: string }) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

function readStoredAuth(): AuthState {
  try {
    const stored = localStorage.getItem(STORAGE_KEY);
    if (!stored) {
      return { user: null, token: null };
    }
    const parsed = JSON.parse(stored) as AuthState;
    return parsed;
  } catch (error) {
    console.warn("Unable to read stored auth state", error);
    return { user: null, token: null };
  }
}

function persistAuth(state: AuthState) {
  try {
    if (!state.user || !state.token) {
      localStorage.removeItem(STORAGE_KEY);
      return;
    }
    localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
  } catch (error) {
    console.warn("Unable to persist auth state", error);
  }
}

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [state, setState] = useState<AuthState>(() => readStoredAuth());

  useEffect(() => {
    persistAuth(state);
  }, [state]);

  const applyAuthResponse = useCallback((response: AuthResponse) => {
    setState({ token: response.token, user: response.user });
  }, []);

  const login = useCallback(async (payload: { username: string; password: string }) => {
    const response = await loginRequest(payload);
    applyAuthResponse(response);
  }, [applyAuthResponse]);

  const register = useCallback(async (payload: { username: string; password: string; verificacionPassword: string }) => {
    const response = await registerRequest(payload);
    applyAuthResponse(response);
  }, [applyAuthResponse]);

  const logout = useCallback(() => {
    setState({ token: null, user: null });
  }, []);

  const value = useMemo<AuthContextValue>(() => ({
    ...state,
    isAuthenticated: Boolean(state.token && state.user),
    login,
    register,
    logout,
  }), [state, login, register, logout]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}
