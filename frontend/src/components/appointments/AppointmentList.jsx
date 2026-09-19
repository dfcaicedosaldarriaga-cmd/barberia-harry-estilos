import React from "react";
import { formatCurrency, formatDate } from "../../utils/appointmentValidation";

/**
 * Componente AppointmentList - Visualizador y gestor de citas agendadas.
 * Renderiza el listado histórico de citas, su estado actual (Confirmada / Cancelada)
 * y expone la acción de cancelar cita para el usuario.
 * 
 * @component
 * @param {Object} props
 * @param {Array<Object>} props.appointments Colección de citas registradas.
 * @param {Array<{id: string, name: string}>} props.barbers Lista de barberos.
 * @param {Function} props.onCancel Función de devolución para cancelar una cita.
 * @param {Array<Object>} props.services Catálogo de servicios.
 * @returns {JSX.Element} Cuadrícula responsiva con las tarjetas de citas.
 */
export default function AppointmentList({ appointments, barbers, onCancel, services }) {
  // Función auxiliar pura para recuperar el nombre del barbero a partir de su ID
  const findName = (collection, id) => collection.find((item) => item.id === id)?.name ?? "No disponible";

  return (
    <div className="appointment-list">
      {appointments.map((appointment) => {
        const service = services.find(({ id }) => id === appointment.serviceId);
        const isCancelled = appointment.status === "Cancelada";

        return (
          <article className="appointment-card" key={appointment.id}>
            <div>
              <span className={`status ${isCancelled ? "status--cancelled" : ""}`}>{appointment.status}</span>
              <h3>{appointment.customerName}</h3>
              <p>{service?.name} · {findName(barbers, appointment.barberId)}</p>
              <p>{formatDate(appointment.date)} · {appointment.time}</p>
              <strong>{service ? formatCurrency(service.price) : ""}</strong>
            </div>
            {!isCancelled && (
              <button className="button button--secondary" type="button" onClick={() => onCancel(appointment.id)}>
                Cancelar
              </button>
            )}
          </article>
        );
      })}
    </div>
  );
}
