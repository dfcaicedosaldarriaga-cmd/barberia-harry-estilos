import React from "react";
import { formatCurrency, formatDate } from "../../utils/appointmentValidation";

export default function AppointmentSummary({ barbers, formData, services }) {
  const service = services.find(({ id }) => id === formData.serviceId);
  const barber = barbers.find(({ id }) => id === formData.barberId);

  return (
    <aside className="summary-card" aria-label="Resumen de reserva">
      <p className="eyebrow">Tu reserva</p>
      <h2>Resumen</h2>
      <dl>
        <div><dt>Cliente</dt><dd>{formData.customerName || "Pendiente"}</dd></div>
        <div><dt>Servicio</dt><dd>{service?.name || "Pendiente"}</dd></div>
        <div><dt>Barbero</dt><dd>{barber?.name || "Pendiente"}</dd></div>
        <div><dt>Fecha</dt><dd>{formatDate(formData.date)}</dd></div>
        <div><dt>Hora</dt><dd>{formData.time || "Pendiente"}</dd></div>
        <div><dt>Duración</dt><dd>{service ? `${service.duration} min` : "Pendiente"}</dd></div>
      </dl>
      <div className="summary-card__total">
        <span>Total</span>
        <strong>{service ? formatCurrency(service.price) : "$0"}</strong>
      </div>
    </aside>
  );
}
