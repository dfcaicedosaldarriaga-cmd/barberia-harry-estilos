import React from "react";

/**
 * Componente Header - Encabezado principal de la aplicación.
 * Proporciona identidad visual (Barbería Harry Estilos) y enlaces
 * de navegación semántica accesibles para el módulo de agendamiento.
 * 
 * @component
 * @returns {JSX.Element} Estructura del encabezado con logo y navegación.
 */
export default function Header() {
  return (
    <header className="site-header">
      <a className="brand" href="#inicio" aria-label="Ir al inicio de Barbería Harry Estilos">
        <span className="brand-mark" aria-hidden="true">✂</span>
        <span><strong>Harry</strong> Estilos</span>
      </a>
      <nav aria-label="Navegación principal del módulo">
        <a href="#inicio">Inicio</a>
        <a href="#servicios-destacados">Servicios</a>
        <a href="#agendamiento">Agendar cita</a>
        <a href="#mis-citas">Mis citas</a>
        <a href="#nosotros">Nosotros</a>
        <a href="#contacto">Contacto</a>
      </nav>
    </header>
  );
}
