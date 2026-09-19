import React from "react";
import { formatCurrency, formatDate } from "../../utils/appointmentValidation";

/**
 * Componente AppointmentSummary - Resumen reactivo de la reserva.
 * Calcula y proyecta en tiempo real el precio total, la duración estimada y los detalles
 * de la cita mientras el usuario diligencia los campos del formulario.
 * 
 * @component
 * @param {Object} props
 * @param {Array<{id: string, name: string}>} props.barbers Lista de barberos.
 * @param {Object} props.formData Datos capturados actualmente en el formulario.
 * @param {Array<{id: string, name: string, price: number, duration: number}>} props.services Catálogo de servicios.
 * @returns {JSX.Element} Panel lateral con desglose de la reserva.
 */
export default function AppointmentSummary({ barbers, formData, services }) {
  // Búsqueda de la entidad de servicio y barbero seleccionados para extraer metadatos
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
