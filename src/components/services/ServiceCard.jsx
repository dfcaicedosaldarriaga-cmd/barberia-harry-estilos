import React from "react";
import { formatCurrency } from "../../utils/appointmentValidation";

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
