/* =========================================================
   Barberia Harry Estilos — app.js
   Adaptado para trabajar con JSP/Servlets (Jakarta EE).
   - El resumen de reserva se actualiza en tiempo real.
   - El formulario hace POST al servlet (no usa localStorage).
   - Los datos de servicios se leen de los atributos data-*
     de los radio buttons generados por el JSP.
   ========================================================= */

const currency = new Intl.NumberFormat("es-CO", {
  style: "currency",
  currency: "COP",
  maximumFractionDigits: 0
});

document.addEventListener("DOMContentLoaded", () => {
  initNavigation();
  initBookingForm();
  initEditForm();
});

/* ---------- Navegacion movil ---------- */
function initNavigation() {
  const button = document.querySelector(".nav-toggle");
  const nav = document.querySelector(".site-nav");

  if (!button || !nav) return;

  button.addEventListener("click", () => {
    const isOpen = nav.classList.toggle("open");
    button.setAttribute("aria-expanded", String(isOpen));
  });
}

/* ---------- Formulario de agendamiento (bookingForm) ---------- */
function initBookingForm() {
  const form = document.querySelector("#bookingForm");
  if (!form) return;

  // Establecer fecha minima como hoy
  const today = new Date().toISOString().split("T")[0];
  const fecha = form.querySelector("#fecha");
  if (fecha) fecha.min = today;

  const message = form.querySelector("#formMessage");

  // Escuchar cambios para actualizar el resumen
  form.addEventListener("input", () => updateSummary(form));
  form.addEventListener("change", () => updateSummary(form));

  // Validacion antes de enviar
  form.addEventListener("submit", (event) => {
    if (message) message.textContent = "";

    if (!form.checkValidity()) {
      event.preventDefault();
      if (message) message.textContent = "Completa todos los campos para confirmar la reserva.";
      form.reportValidity();
      return;
    }
    // Si la validacion pasa, el formulario se envia normalmente al servlet via POST
  });

  // Actualizar resumen inicial
  updateSummary(form);
}

/* ---------- Formulario de edicion (editForm) ---------- */
function initEditForm() {
  const form = document.querySelector("#editForm");
  if (!form) return;

  const message = form.querySelector("#formMessage");

  form.addEventListener("input", () => updateSummary(form));
  form.addEventListener("change", () => updateSummary(form));

  form.addEventListener("submit", (event) => {
    if (message) message.textContent = "";

    if (!form.checkValidity()) {
      event.preventDefault();
      if (message) message.textContent = "Completa todos los campos para guardar los cambios.";
      form.reportValidity();
      return;
    }
  });

  // Actualizar resumen inicial con datos pre-poblados
  updateSummary(form);
}

/* ---------- Actualizacion del resumen de reserva ---------- */
function updateSummary(form) {
  const data = getFormData(form);
  setSummary("cliente", data.cliente || "Pendiente");
  setSummary("servicio", data.servicio || "Pendiente");
  setSummary("barbero", data.barbero || "Pendiente");
  setSummary("fecha", data.fecha || "Pendiente");
  setSummary("hora", data.hora || "Pendiente");
  setSummary("duracion", `${data.duracion} min`);
  setSummary("total", currency.format(data.total));
}

/* ---------- Obtener datos del formulario ---------- */
function getFormData(form) {
  // Leer nombre del cliente
  const clienteInput = form.querySelector("#cliente");
  const cliente = clienteInput ? clienteInput.value.trim() : "";

  // Leer barbero seleccionado (del select)
  const barberoSelect = form.querySelector("#barbero_id") || form.querySelector("[name='barbero_id']");
  let barbero = "";
  if (barberoSelect && barberoSelect.selectedIndex > 0) {
    barbero = barberoSelect.options[barberoSelect.selectedIndex].text;
  }

  // Leer servicio seleccionado (del radio con data attributes)
  const servicioRadio = form.querySelector("input[name='servicio_id']:checked");
  let servicio = "";
  let precio = 0;
  let duracion = 0;
  if (servicioRadio) {
    servicio = servicioRadio.getAttribute("data-nombre") || "";
    precio = parseInt(servicioRadio.getAttribute("data-precio") || "0", 10);
    duracion = parseInt(servicioRadio.getAttribute("data-duracion") || "0", 10);
  }

  // Leer fecha y hora
  const fechaInput = form.querySelector("#fecha");
  const horaInput = form.querySelector("#hora");
  const fecha = fechaInput ? fechaInput.value : "";
  const hora = horaInput ? horaInput.value : "";

  return {
    cliente: cliente,
    barbero: barbero,
    servicio: servicio,
    fecha: fecha,
    hora: hora,
    duracion: duracion,
    total: precio
  };
}

/* ---------- Escribir en la tarjeta de resumen ---------- */
function setSummary(key, value) {
  const target = document.querySelector(`[data-summary="${key}"]`);
  if (target) target.textContent = value;
}
