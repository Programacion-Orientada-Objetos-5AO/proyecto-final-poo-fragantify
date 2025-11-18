import { createRoot } from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { AuthProvider } from "./context/AuthContext";
import { FragranceProvider } from "./context/FragranceContext";

createRoot(document.getElementById("root")!).render(
  <AuthProvider>
    <FragranceProvider>
      <App />
    </FragranceProvider>
  </AuthProvider>
);
