import React from "react";
import ServiceCard from "../services/ServiceCard";

export default function BookingForm({
  barbers,
  errors,
  formData,
  onChange,
  onSelectService,
  onSubmit,
  services,
}) {
  return (
    <form className="booking-form" onSubmit={onSubmit} noValidate>
      <div className="form-field">
        <label htmlFor="customerName">Nombre del cliente</label>
        <input
          id="customerName"
          name="customerName"
          value={formData.customerName}
          onChange={onChange}
          aria-describedby="customerName-error"
          aria-invalid={Boolean(errors.customerName)}
          placeholder="Ej. Juan Pérez"
        />
        <span id="customerName-error" className="field-error">{errors.customerName}</span>
      </div>

      <fieldset className="form-field service-field">
        <legend>Servicio</legend>
        <div className="services-grid">
          {services.map((service) => (
            <ServiceCard
              key={service.id}
              service={service}
              selected={formData.serviceId === service.id}
              onSelect={onSelectService}
            />
          ))}
        </div>
        <span className="field-error">{errors.serviceId}</span>
      </fieldset>

      <div className="form-grid">
        <div className="form-field">
          <label htmlFor="barberId">Barbero</label>
          <select
            id="barberId"
            name="barberId"
            value={formData.barberId}
            onChange={onChange}
            aria-invalid={Boolean(errors.barberId)}
          >
            <option value="">Seleccione una opción</option>
            {barbers.map((barber) => <option key={barber.id} value={barber.id}>{barber.name}</option>)}
          </select>
          <span className="field-error">{errors.barberId}</span>
        </div>

        <div className="form-field">
          <label htmlFor="date">Fecha</label>
          <input id="date" name="date" type="date" value={formData.date} onChange={onChange} aria-invalid={Boolean(errors.date)} />
          <span className="field-error">{errors.date}</span>
        </div>

        <div className="form-field">
          <label htmlFor="time">Hora</label>
          <input id="time" name="time" type="time" min="09:00" max="19:00" value={formData.time} onChange={onChange} aria-invalid={Boolean(errors.time)} />
          <span className="field-error">{errors.time}</span>
        </div>
      </div>

      <button className="button button--primary" type="submit">Confirmar reserva</button>
    </form>
  );
}
