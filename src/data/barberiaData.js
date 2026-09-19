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

export const BARBERS = [
  { id: "harry", name: "Harry" },
  { id: "andres", name: "Andrés" },
  { id: "camilo", name: "Camilo" },
];

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
