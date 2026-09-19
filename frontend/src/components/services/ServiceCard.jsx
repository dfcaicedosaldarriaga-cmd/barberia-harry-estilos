import React from "react";
import { formatCurrency } from "../../utils/appointmentValidation";

/**
 * Componente ServiceCard - Tarjeta interactiva y reutilizable para selección de servicio.
 * Permite al usuario seleccionar un tipo de corte o paquete mediante clic o teclado,
 * reflejando el estado visualmente y mediante el atributo accesible aria-pressed.
 * 
 * @component
 * @param {Object} props
 * @param {Object} props.service Objeto con información del servicio (id, name, description, price, duration).
 * @param {boolean} props.selected Indica si la tarjeta está actualmente seleccionada.
 * @param {Function} props.onSelect Callback disparado con el id del servicio al seleccionarse.
 * @returns {JSX.Element} Botón accesible formateado como tarjeta interactiva.
 */
export default function ServiceCard({ service, selected, onSelect }) {
  return (
    <button
      className={`service-card ${selected ? "service-card--selected" : ""}`}
      type="button"
      aria-pressed={selected}
      onClick={() => onSelect(service.id)}
    >
      <span className="service-card__name">{service.name}</span>
      <span>{service.description}</span>
      <strong>{formatCurrency(service.price)}</strong>
      <small>{service.duration} minutos</small>
    </button>
  );
}
