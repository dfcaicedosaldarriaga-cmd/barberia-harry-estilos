/**
 * Capa de Servicios de Agendamiento - Barbería Harry Estilos.
 * Encapsula la comunicación con la fuente de datos. Diseñada bajo el principio
 * de inversión de dependencias para desacoplar los componentes visuales de la
 * capa de transporte (fetch/axios hacia la API REST en Java Servlet / Spring).
 */

/**
 * Función auxiliar para emular la latencia de red en solicitudes HTTP asíncronas.
 * @param {number} milliseconds Tiempo de espera.
 * @returns {Promise<void>}
 */
const wait = (milliseconds) => new Promise((resolve) => setTimeout(resolve, milliseconds));

/**
 * Registra una nueva cita en el sistema.
 * Simula el endpoint POST `/api/citas` del backend Java.
 * 
 * @param {Object} appointment Datos de la cita a crear.
 * @returns {Promise<Object>} Cita registrada con su ID y estado "Confirmada".
 */
export async function createAppointment(appointment) {
  await wait(350);

  return {
    ...appointment,
    id: `CIT-${Date.now().toString().slice(-6)}`,
    status: "Confirmada",
  };
}

/**
 * Cancela una cita registrada por su identificador.
 * Simula el endpoint DELETE / PUT `/api/citas/:id/cancelar` del backend.
 * 
 * @param {string} id Identificador único de la cita.
 * @returns {Promise<string>} Identificador de la cita cancelada.
 */
export async function cancelAppointment(id) {
  await wait(200);
  return id;
}
