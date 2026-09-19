import React, { useMemo, useState } from "react";
import AppointmentList from "./components/appointments/AppointmentList";
import AppointmentSummary from "./components/booking/AppointmentSummary";
import BookingForm from "./components/booking/BookingForm";
import Alert from "./components/common/Alert";
import Footer from "./components/layout/Footer";
import Header from "./components/layout/Header";
import { BARBERS, INITIAL_APPOINTMENTS, SERVICES } from "./data/barberiaData";
import { cancelAppointment, createAppointment } from "./services/appointmentService";
import { validateAppointment } from "./utils/appointmentValidation";

/**
 * Estado inicial limpio para el formulario de reserva.
 * Representa el modelo de datos de la cita según los artefactos del ciclo de software.
 */
const EMPTY_APPOINTMENT = {
  customerName: "",
  serviceId: "",
  barberId: "",
  date: "",
  time: "",
};

/**
 * Componente principal App - Orquestador del Módulo Front-end de Agendamiento.
 * Controla el estado global de citas, datos del formulario, notificaciones de usuario
 * y coordina las capas de servicios, validación y presentación visual.
 * 
 * Cumple con los estándares de codificación React (Functional Components + Hooks)
 * y requerimientos de la evidencia SENA GA7-220501096-AA4-EV03.
 * 
 * @component
 * @returns {JSX.Element} Vista orquestada del módulo de citas para Barbería Harry Estilos.
 */
export default function App() {
  // Estado local para captura reactiva de campos del formulario
  const [formData, setFormData] = useState(EMPTY_APPOINTMENT);
  // Estado para mensajes de validación por campo
  const [errors, setErrors] = useState({});
  // Mensaje de notificación global (éxito o error de operación)
  const [notice, setNotice] = useState("");
  // Lista de citas en memoria gestionadas en el ciclo actual
  const [appointments, setAppointments] = useState(INITIAL_APPOINTMENTS);

  // Hook useMemo: Optimiza el cálculo de citas activas omitiendo las canceladas
  const activeAppointments = useMemo(
    () => appointments.filter((appointment) => appointment.status !== "Cancelada"),
    [appointments],
  );

  /**
   * Manejador de cambios en inputs estándar (nombre, fecha, hora, barbero).
   * Limpia el mensaje de error del campo tan pronto el usuario empieza a escribir.
   * @param {React.ChangeEvent<HTMLInputElement|HTMLSelectElement>} event
   */
  function handleChange({ target: { name, value } }) {
    setFormData((current) => ({ ...current, [name]: value }));
    setErrors((current) => ({ ...current, [name]: "" }));
  }

  /**
   * Manejador de selección para las tarjetas de servicio reutilizables.
   * @param {string} serviceId Identificador del servicio elegido.
   */
  function handleServiceSelect(serviceId) {
    setFormData((current) => ({ ...current, serviceId }));
    setErrors((current) => ({ ...current, serviceId: "" }));
  }

  /**
   * Procesa el envío del formulario: valida campos obligatorios y registra la cita
   * a través de la capa de servicios desacoplada.
   * @param {React.FormEvent<HTMLFormElement>} event
   */
  async function handleSubmit(event) {
    event.preventDefault();
    const validationErrors = validateAppointment(formData);
    setErrors(validationErrors);

    // Si existen discrepancias o campos vacíos, se detiene el flujo y se notifica al usuario
    if (Object.keys(validationErrors).length > 0) {
      setNotice("Revisa los campos marcados para confirmar tu reserva.");
      return;
    }

    // La capa de servicio permite reemplazar el simulador por la API REST sin alterar la UI
    const createdAppointment = await createAppointment(formData);
    setAppointments((current) => [createdAppointment, ...current]);
    setFormData(EMPTY_APPOINTMENT);
    setNotice(`La cita ${createdAppointment.id} fue registrada correctamente en Barbería Harry Estilos.`);
  }

  /**
   * Solicita la cancelación de una cita previamente confirmada.
   * @param {string} id Identificador único de la cita.
   */
  async function handleCancel(id) {
    const cancelledId = await cancelAppointment(id);
    setAppointments((current) => current.map((appointment) => (
      appointment.id === cancelledId ? { ...appointment, status: "Cancelada" } : appointment
    )));
    setNotice(`La cita ${cancelledId} fue cancelada.`);
  }

  return (
    <div className="app-shell" id="inicio">
      <Header />
      <main>
        {/* SECCIÓN 1: HERO PRINCIPAL */}
        <section className="hero-section" aria-labelledby="hero-title">
          <div className="hero-copy">
            <p className="eyebrow">Clásicos y modernos</p>
            <h1 id="hero-title">Estilo y precisión para el hombre actual</h1>
            <p>
              Agenda tu turno en la <strong>Barbería Harry Estilos</strong>, elige el servicio que necesitas,
              selecciona tu barbero de confianza y confirma tu reserva en tiempo real.
            </p>
            <div className="hero-actions">
              <a className="button button--primary" href="#agendamiento">Pide tu cita</a>
              <a className="button button--secondary" href="#servicios-destacados">Ver catálogo</a>
            </div>
          </div>
          <div className="hero-visual" aria-label="Identidad visual de Barbería Harry Estilos">
            <div className="barber-pole" aria-hidden="true"></div>
            <div className="hero-badge">
              <strong>Harry Estilos</strong>
              <span>Cortes, barba y paquetes profesionales</span>
            </div>
          </div>
        </section>

        {/* SECCIÓN 2: CATÁLOGO DE SERVICIOS DESTACADOS */}
        <section className="content-section" id="servicios-destacados" aria-labelledby="servicios-title">
          <div className="section-heading">
            <p className="eyebrow">Catálogo oficial</p>
            <h2 id="servicios-title">Servicios destacados</h2>
            <p className="section-subtitle">Conoce nuestras tarifas y tiempos estimados para cada atención.</p>
          </div>
          <div className="featured-services-grid">
            {SERVICES.map((service) => (
              <article className="featured-card" key={service.id}>
                <div className="featured-card__icon" aria-hidden="true">
                  {service.id === "corte-clasico" && "✂️"}
                  {service.id === "barba" && "💈"}
                  {service.id === "paquete-completo" && "⭐"}
                </div>
                <h3>{service.name}</h3>
                <p>{service.description}</p>
                <div className="featured-card__meta">
                  <strong>${service.price.toLocaleString("es-CO")} COP</strong>
                  <small>⏱️ {service.duration} minutos</small>
                </div>
                <button
                  type="button"
                  className="button button--primary button--full"
                  onClick={() => {
                    handleServiceSelect(service.id);
                    document.getElementById("agendamiento")?.scrollIntoView({ behavior: "smooth" });
                  }}
                >
                  Agendar este servicio
                </button>
              </article>
            ))}
          </div>
        </section>

        {/* SECCIÓN 3: MÓDULO REACT DE AGENDAMIENTO */}
        <section className="content-section" id="agendamiento" aria-labelledby="booking-title">
          <div className="section-heading">
            <p className="eyebrow">Módulo Front-end interactivo</p>
            <h2 id="booking-title">Reserva tu cita</h2>
            <p className="section-subtitle">Diligencia los datos y confirma tu atención con cálculo inmediato de tiempos y valores.</p>
          </div>
          <Alert message={notice} type={Object.keys(errors).length ? "error" : "success"} />
          <div className="booking-layout">
            <BookingForm
              barbers={BARBERS}
              errors={errors}
              formData={formData}
              onChange={handleChange}
              onSelectService={handleServiceSelect}
              onSubmit={handleSubmit}
              services={SERVICES}
            />
            <AppointmentSummary barbers={BARBERS} formData={formData} services={SERVICES} />
          </div>
        </section>

        {/* SECCIÓN 4: MIS CITAS REGISTRADAS */}
        <section className="content-section" id="mis-citas" aria-labelledby="appointments-title">
          <div className="section-heading section-heading--inline">
            <div>
              <p className="eyebrow">Consulta en memoria</p>
              <h2 id="appointments-title">Mis citas</h2>
            </div>
            <span className="counter">{activeAppointments.length} activas</span>
          </div>
          <AppointmentList appointments={appointments} barbers={BARBERS} onCancel={handleCancel} services={SERVICES} />
        </section>

        {/* SECCIÓN 5: NOSOTROS */}
        <section className="content-section split-section" id="nosotros" aria-labelledby="nosotros-title">
          <div className="split-section__content">
            <p className="eyebrow">Nosotros</p>
            <h2 id="nosotros-title">Atención cercana, resultado impecable</h2>
            <p>
              <strong>Barbería Harry Estilos</strong> es un proyecto concebido para clientes que buscan distinción,
              técnicas de corte de vanguardia y una gestión de turnos transparente y moderna.
            </p>
            <p>
              Este componente front-end fue desarrollado en <strong>React 19 con Vite</strong> para la evidencia formativa
              <strong> GA7-220501096-AA4-EV03</strong>, integrando el flujo de reserva con los artefactos de análisis y
              diseño validados en el programa ADSO del SENA.
            </p>
          </div>
          <div className="split-section__aside">
            <div className="stat-card">
              <strong>+10 años</strong>
              <span>Experiencia combinada de nuestros barberos</span>
            </div>
            <div className="stat-card">
              <strong>100%</strong>
              <span>Garantía de satisfacción y estilo</span>
            </div>
          </div>
        </section>

        {/* SECCIÓN 6: HORARIOS Y CONTACTO */}
        <section className="contact-band" id="contacto" aria-labelledby="contacto-title">
          <div className="contact-band__info">
            <p className="eyebrow">Ubicación y atención</p>
            <h2 id="contacto-title">Reserva tu espacio con anticipación</h2>
            <p><strong>Horario de atención:</strong> Lunes a sábado de 9:00 a. m. a 7:00 p. m.</p>
            <p><strong>Atención al cliente:</strong> Calle Principal · Sede Central Barbería Harry Estilos</p>
          </div>
          <a className="button button--primary" href="#agendamiento">Agendar ahora</a>
        </section>
      </main>
      <Footer />
    </div>
  );
}
