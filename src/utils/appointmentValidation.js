/**
 * Módulo de utilidades y validación de reglas de negocio para Barbería Harry Estilos.
 * Centraliza la lógica de validación del formulario y formateo localizado (es-CO).
 */

/**
 * Expresión regular para validar nombres de clientes:
 * Permite caracteres alfabéticos en español (con tildes, diéresis y ñ),
 * con longitud mínima de 3 caracteres y máxima de 100.
 */
const NAME_PATTERN = /^[A-Za-zÁÉÍÓÚáéíóúÑñÜü\s]{3,100}$/;

/**
 * Valida los campos requeridos de una cita antes de procesar el agendamiento.
 * Aplica las restricciones definidas en los casos de uso del sistema.
 * 
 * @param {Object} appointment Objeto con los datos diligenciados.
 * @param {string} appointment.customerName Nombre completo del cliente.
 * @param {string} appointment.serviceId Identificador del servicio.
 * @param {string} appointment.barberId Identificador del barbero.
 * @param {string} appointment.date Fecha seleccionada (YYYY-MM-DD).
 * @param {string} appointment.time Hora seleccionada (HH:MM).
 * @returns {Object<string, string>} Mapa de errores donde la clave es el campo y el valor es el mensaje.
 */
export function validateAppointment(appointment) {
  const errors = {};

  if (!appointment.customerName || !NAME_PATTERN.test(appointment.customerName.trim())) {
    errors.customerName = "Ingrese un nombre válido de al menos 3 caracteres.";
  }

  if (!appointment.serviceId) errors.serviceId = "Seleccione un servicio.";
  if (!appointment.barberId) errors.barberId = "Seleccione un barbero.";
  if (!appointment.date) errors.date = "Seleccione una fecha.";
  if (!appointment.time) errors.time = "Seleccione una hora.";

  return errors;
}

/**
 * Formatea valores numéricos al estándar monetario de pesos colombianos (COP).
 * Ejemplo: 25000 -> "$ 25.000"
 * 
 * @param {number} value Monto numérico.
 * @returns {string} Cadena formateada según la configuración regional es-CO.
 */
export function formatCurrency(value) {
  return new Intl.NumberFormat("es-CO", {
    style: "currency",
    currency: "COP",
    maximumFractionDigits: 0,
  }).format(value);
}

/**
 * Convierte fechas ISO (YYYY-MM-DD) a formato legible en español.
 * Ejemplo: "2026-09-24" -> "24 de septiembre de 2026"
 * 
 * @param {string} value Fecha en formato ISO.
 * @returns {string} Fecha formateada o "Pendiente" si no se ha definido.
 */
export function formatDate(value) {
  if (!value) return "Pendiente";

  return new Intl.DateTimeFormat("es-CO", {
    day: "2-digit",
    month: "long",
    year: "numeric",
  }).format(new Date(`${value}T12:00:00`));
}
