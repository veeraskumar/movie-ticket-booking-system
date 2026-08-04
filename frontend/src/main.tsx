import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import ThemeProvider from "./provider/ThemeProvider.tsx";
import { Toaster } from "./components/ui/toast.tsx";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <ThemeProvider
      attribute={"class"}
      defaultTheme="system"
      enableSystem
      enableColorScheme
    >
      <App />
      <Toaster />
    </ThemeProvider>
  </StrictMode>,
);
