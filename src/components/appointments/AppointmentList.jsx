import React from "react";
import { formatCurrency, formatDate } from "../../utils/appointmentValidation";

export default function AppointmentList({ appointments, barbers, onCancel, services }) {
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
