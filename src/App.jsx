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
        <section className="hero" aria-labelledby="hero-title">
          <p className="eyebrow">Gestión de citas</p>
          <h1 id="hero-title">Agenda tu próximo estilo</h1>
          <p>Selecciona tu servicio, barbero y horario desde un único módulo.</p>
        </section>

        <section className="content-section" id="servicios" aria-labelledby="booking-title">
          <div className="section-heading">
            <p className="eyebrow">Módulo front-end</p>
            <h2 id="booking-title">Reserva de cita</h2>
          </div>
          <Alert message={notice} type={Object.keys(errors).length ? "error" : "success"} />
          <div className="booking-layout" id="agendamiento">
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

        <section className="content-section" id="mis-citas" aria-labelledby="appointments-title">
          <div className="section-heading section-heading--inline">
            <div>
              <p className="eyebrow">Consulta</p>
              <h2 id="appointments-title">Mis citas</h2>
            </div>
            <span className="counter">{activeAppointments.length} activas</span>
          </div>
          <AppointmentList appointments={appointments} barbers={BARBERS} onCancel={handleCancel} services={SERVICES} />
        </section>
      </main>
      <Footer />
    </div>
  );
}
