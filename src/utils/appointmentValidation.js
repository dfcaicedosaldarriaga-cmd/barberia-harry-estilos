const NAME_PATTERN = /^[A-Za-zÁÉÍÓÚáéíóúÑñÜü\s]{3,100}$/;

export function validateAppointment(appointment) {
  const errors = {};

  if (!NAME_PATTERN.test(appointment.customerName.trim())) {
    errors.customerName = "Ingrese un nombre válido de al menos 3 caracteres.";
  }

  if (!appointment.serviceId) errors.serviceId = "Seleccione un servicio.";
  if (!appointment.barberId) errors.barberId = "Seleccione un barbero.";
  if (!appointment.date) errors.date = "Seleccione una fecha.";
  if (!appointment.time) errors.time = "Seleccione una hora.";

  return errors;
}

export function formatCurrency(value) {
  return new Intl.NumberFormat("es-CO", {
    style: "currency",
    currency: "COP",
    maximumFractionDigits: 0,
  }).format(value);
}

export function formatDate(value) {
  if (!value) return "Pendiente";

  return new Intl.DateTimeFormat("es-CO", {
    day: "2-digit",
    month: "long",
    year: "numeric",
  }).format(new Date(`${value}T12:00:00`));
}
