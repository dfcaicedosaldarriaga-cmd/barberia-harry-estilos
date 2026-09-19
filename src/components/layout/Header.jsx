import React from "react";

export default function Header() {
  return (
    <header className="site-header">
      <a className="brand" href="#inicio" aria-label="Ir al inicio de Barber System">
        <span className="brand-mark" aria-hidden="true">✂</span>
        <span><strong>Barber</strong> System</span>
      </a>
      <nav aria-label="Navegación principal">
        <a href="#servicios">Servicios</a>
        <a href="#agendamiento">Agendamiento</a>
        <a href="#mis-citas">Mis citas</a>
      </nav>
    </header>
  );
}
