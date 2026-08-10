const services = {
  corte: {
    name: "Corte Clasico",
    price: 25000,
    duration: 45
  },
  barba: {
    name: "Barba",
    price: 14000,
    duration: 25
  },
  paquete: {
    name: "Paquete Completo",
    price: 35000,
    duration: 70
  }
};

const currency = new Intl.NumberFormat("es-CO", {
  style: "currency",
  currency: "COP",
  maximumFractionDigits: 0
});

document.addEventListener("DOMContentLoaded", () => {
  initNavigation();
  initBookingForm();
  initConfirmation();
});

function initNavigation() {
  const button = document.querySelector(".nav-toggle");
  const nav = document.querySelector(".site-nav");

  if (!button || !nav) return;

  button.addEventListener("click", () => {
    const isOpen = nav.classList.toggle("open");
    button.setAttribute("aria-expanded", String(isOpen));
  });
}

function initBookingForm() {
  const form = document.querySelector("#bookingForm");
  if (!form) return;

  const today = new Date().toISOString().split("T")[0];
  const fecha = form.querySelector("#fecha");
  const message = form.querySelector("#formMessage");
  fecha.min = today;

  const selectedFromUrl = new URLSearchParams(window.location.search).get("servicio");
  if (services[selectedFromUrl]) {
    const input = form.querySelector(`input[name="servicio"][value="${selectedFromUrl}"]`);
    if (input) input.checked = true;
  }

  form.addEventListener("input", updateSummary);
  form.addEventListener("change", updateSummary);
  form.addEventListener("submit", (event) => {
    event.preventDefault();
    message.textContent = "";

    if (!form.checkValidity()) {
      message.textContent = "Completa todos los campos para confirmar la reserva.";
      form.reportValidity();
      return;
    }

    const data = getBookingData(form);
    localStorage.setItem("harryReserva", JSON.stringify(data));
    window.location.href = "confirmacion.html";
  });

  updateSummary();

  function updateSummary() {
    const data = getBookingData(form);
    setSummary("cliente", data.cliente || "Pendiente");
    setSummary("servicio", data.servicio || "Pendiente");
    setSummary("barbero", data.barbero || "Pendiente");
    setSummary("fecha", data.fecha || "Pendiente");
    setSummary("hora", data.hora || "Pendiente");
    setSummary("duracion", `${data.duracion} min`);
    setSummary("total", currency.format(data.total));
  }
}

function getBookingData(form) {
  const formData = new FormData(form);
  const serviceKey = formData.get("servicio");
  const selectedService = services[serviceKey] || { name: "", price: 0, duration: 0 };

  return {
    cliente: String(formData.get("cliente") || "").trim(),
    barbero: String(formData.get("barbero") || ""),
    servicio: selectedService.name,
    fecha: String(formData.get("fecha") || ""),
    hora: String(formData.get("hora") || ""),
    duracion: selectedService.duration,
    total: selectedService.price
  };
}

function setSummary(key, value) {
  const target = document.querySelector(`[data-summary="${key}"]`);
  if (target) target.textContent = value;
}

function initConfirmation() {
  const container = document.querySelector("#confirmationDetails");
  if (!container) return;

  const saved = localStorage.getItem("harryReserva");
  if (!saved) return;

  const data = JSON.parse(saved);
  container.innerHTML = `
    <p><strong>Cliente:</strong> ${escapeHtml(data.cliente)}</p>
    <p><strong>Servicio:</strong> ${escapeHtml(data.servicio)}</p>
    <p><strong>Barbero:</strong> ${escapeHtml(data.barbero)}</p>
    <p><strong>Fecha y hora:</strong> ${escapeHtml(data.fecha)} - ${escapeHtml(data.hora)}</p>
    <p><strong>Duracion:</strong> ${Number(data.duracion)} minutos</p>
    <p><strong>Total:</strong> ${currency.format(Number(data.total))}</p>
  `;
}

function escapeHtml(value) {
  return String(value)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");
}
