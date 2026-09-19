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

const EMPTY_APPOINTMENT = {
  customerName: "",
  serviceId: "",
  barberId: "",
  date: "",
  time: "",
};

export default function App() {
  const [formData, setFormData] = useState(EMPTY_APPOINTMENT);
  const [errors, setErrors] = useState({});
  const [notice, setNotice] = useState("");
  const [appointments, setAppointments] = useState(INITIAL_APPOINTMENTS);

  const activeAppointments = useMemo(
    () => appointments.filter((appointment) => appointment.status !== "Cancelada"),
    [appointments],
  );

  function handleChange({ target: { name, value } }) {
    setFormData((current) => ({ ...current, [name]: value }));
    setErrors((current) => ({ ...current, [name]: "" }));
  }

  function handleServiceSelect(serviceId) {
    setFormData((current) => ({ ...current, serviceId }));
    setErrors((current) => ({ ...current, serviceId: "" }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    const validationErrors = validateAppointment(formData);
    setErrors(validationErrors);

    if (Object.keys(validationErrors).length > 0) {
      setNotice("Revisa los campos marcados para confirmar tu reserva.");
      return;
    }

    // La capa de servicio permite reemplazar el simulador por una API sin cambiar la interfaz.
    const createdAppointment = await createAppointment(formData);
    setAppointments((current) => [createdAppointment, ...current]);
    setFormData(EMPTY_APPOINTMENT);
    setNotice(`La cita ${createdAppointment.id} fue registrada correctamente.`);
  }

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
