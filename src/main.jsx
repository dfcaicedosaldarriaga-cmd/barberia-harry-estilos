/**
 * Punto de entrada principal de la aplicación React.
 * Inicializa el árbol de componentes en el contenedor #root del DOM
 * y activa el modo estricto (StrictMode) para detección temprana de efectos secundarios.
 * 
 * Evidencia: GA7-220501096-AA4-EV03
 * Proyecto: Barbería Harry Estilos
 * Aprendiz: Diego Fernando Caicedo Saldarriaga
 */
import React, { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App";
import "./styles/index.css";

const rootElement = document.getElementById("root");
if (!rootElement) {
  throw new Error("No se encontró el elemento contenedor con id 'root'");
}

createRoot(rootElement).render(
  <StrictMode>
    <App />
  </StrictMode>,
);
