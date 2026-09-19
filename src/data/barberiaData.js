/**
 * Datos semilla y modelos en memoria para Barbería Harry Estilos.
 * Representan las entidades del dominio (Servicios, Barberos y Citas)
 * definidas en los diagramas de clases y casos de uso del proyecto formativo.
 */

/**
 * Catálogo oficial de servicios ofrecidos en Barbería Harry Estilos.
 * Incluye tarifas en COP y tiempos estimados de atención en minutos.
 */
export const SERVICES = [
  {
    id: "corte-clasico",
    name: "Corte clásico",
    description: "Corte, perfilado básico y finalización con producto.",
    price: 25000,
    duration: 45,
  },
  {
    id: "barba",
    name: "Arreglo de barba",
    description: "Perfilado, afeitado y cuidado de barba.",
    price: 14000,
    duration: 25,
  },
  {
    id: "paquete-completo",
    name: "Paquete completo",
    description: "Corte clásico y arreglo de barba en una sola cita.",
    price: 35000,
    duration: 70,
  },
];

/**
 * Equipo de profesionales de barbería disponibles para agendamiento.
 * Se vincula con la tabla `barberos` de la base de datos MySQL del proyecto.
 */
export const BARBERS = [
  { id: "harry", name: "Harry" },
  { id: "andres", name: "Andrés" },
  { id: "camilo", name: "Camilo" },
];

/**
 * Cita inicial de demostración para visualización y pruebas de cancelación.
 */
export const INITIAL_APPOINTMENTS = [
  {
    id: "CIT-1001",
    customerName: "Cliente de ejemplo",
    serviceId: "corte-clasico",
    barberId: "harry",
    date: "2026-09-24",
    time: "10:00",
    status: "Confirmada",
  },
];
