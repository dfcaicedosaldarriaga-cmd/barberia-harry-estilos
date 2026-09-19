const wait = (milliseconds) => new Promise((resolve) => setTimeout(resolve, milliseconds));

/**
 * Simula el contrato con la API. Este archivo concentra la comunicación para
 * reemplazarla por fetch/axios cuando el backend esté disponible.
 */
export async function createAppointment(appointment) {
  await wait(350);

  return {
    ...appointment,
    id: `CIT-${Date.now().toString().slice(-6)}`,
    status: "Confirmada",
  };
}

export async function cancelAppointment(id) {
  await wait(200);
  return id;
}
