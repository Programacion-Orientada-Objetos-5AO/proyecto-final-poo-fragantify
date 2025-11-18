import { FormEvent, useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Input } from "./ui/input";
import { Button } from "./ui/button";
import { Alert, AlertDescription, AlertTitle } from "./ui/alert";
import { useAuth } from "../context/AuthContext";

interface LoginProps {
  onSuccess?: () => void;
}

export function Login({ onSuccess }: LoginProps) {
  const { login } = useAuth();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError(null);
    setLoading(true);
    try {
      await login({ username, password });
      onSuccess?.();
    } catch (err) {
      console.error("Login failed", err);
      setError("No pudimos iniciar sesion. Verifica tus credenciales e intenta nuevamente.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="bg-[#0f172a] min-h-[calc(100vh-64px)] flex items-center py-16">
      <div className="max-w-md w-full mx-auto px-4">
        <Card className="bg-black border border-[#d4af37]/40 text-white shadow-2xl">
          <CardHeader>
            <CardTitle className="text-3xl font-serif text-[#d4af37]">Bienvenido de nuevo</CardTitle>
            <CardDescription className="text-gray-300">
              Accede a tu cuenta para gestionar tus fragancias favoritas.
            </CardDescription>
          </CardHeader>
          <CardContent>
            {error && (
              <Alert variant="destructive" className="mb-6">
                <AlertTitle>Error</AlertTitle>
                <AlertDescription>{error}</AlertDescription>
              </Alert>
            )}

            <form className="space-y-6" onSubmit={handleSubmit}>
              <div className="space-y-2">
                <label htmlFor="username" className="text-sm text-gray-200">
                  Email
                </label>
                <Input
                  id="username"
                  type="email"
                  placeholder="usuario@fragantify.app"
                  value={username}
                  onChange={(event) => setUsername(event.target.value)}
                  required
                  className="bg-[#0f172a] border-[#d4af37]/40 text-white placeholder:text-gray-500"
                />
              </div>

              <div className="space-y-2">
                <label htmlFor="password" className="text-sm text-gray-200">
                  Contrasena
                </label>
                <Input
                  id="password"
                  type="password"
                  placeholder="Ingresa tu contrasena"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                  required
                  className="bg-[#0f172a] border-[#d4af37]/40 text-white placeholder:text-gray-500"
                />
                <p className="text-xs text-gray-400">
                  Debe tener al menos 16 caracteres, mayusculas, minusculas, numeros y un simbolo.
                </p>
              </div>

              <Button
                type="submit"
                disabled={loading}
                className="w-full bg-[#d4af37] text-black hover:bg-[#fbbf24] transition-colors"
              >
                {loading ? "Iniciando sesion..." : "Iniciar sesion"}
              </Button>
            </form>
          </CardContent>
        </Card>
      </div>
    </section>
  );
}
