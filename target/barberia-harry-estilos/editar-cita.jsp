<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Barberia Harry Estilos - Editar Cita</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
  <script src="${pageContext.request.contextPath}/js/app.js" defer></script>
</head>
<body>
  <header class="site-header">
    <a class="brand" href="${pageContext.request.contextPath}/inicio" aria-label="Ir al inicio">
      <img class="brand-icon" src="${pageContext.request.contextPath}/img/scissors-mark.svg" alt="">
      <span><strong>Barberia</strong> Harry Estilos</span>
    </a>
    <button class="nav-toggle" type="button" aria-label="Abrir menu" aria-expanded="false">&#9776;</button>
    <nav class="site-nav" aria-label="Navegacion principal">
      <a href="${pageContext.request.contextPath}/inicio">Inicio</a>
      <a href="${pageContext.request.contextPath}/servicios">Servicios</a>
      <a href="${pageContext.request.contextPath}/inicio#nosotros">Nosotros</a>
      <a href="${pageContext.request.contextPath}/inicio#contacto">Contacto</a>
      <a href="${pageContext.request.contextPath}/citas">Mis Citas</a>
    </nav>
  </header>

  <main>
    <section class="page-title compact">
      <p class="eyebrow">Editar cita #${cita.id}</p>
      <h1>Modificar reserva</h1>
      <p>Actualiza los datos de la cita y guarda los cambios.</p>
    </section>

    <form class="booking-layout" id="editForm" action="${pageContext.request.contextPath}/citas" method="POST" novalidate>
      <input type="hidden" name="id" value="${cita.id}">

      <section class="form-panel" aria-label="Datos de la reserva">
        <div class="form-group">
          <label for="cliente">Nombre del cliente</label>
          <input id="cliente" name="cliente" class="form-control" type="text"
                 value="<c:out value='${cita.clienteNombre}'/>" required>
        </div>

        <div class="form-group">
          <label for="barbero_id">1. Selecciona barbero</label>
          <select id="barbero_id" name="barbero_id" class="form-control" required>
            <option value="">Selecciona una opcion</option>
            <c:forEach var="b" items="${barberos}">
              <option value="${b.id}" <c:if test="${b.id == cita.barberoId}">selected</c:if>>${b.nombre}</option>
            </c:forEach>
          </select>
        </div>

        <fieldset class="form-group">
          <legend>2. Elegir servicio</legend>
          <div class="service-options">
            <c:forEach var="s" items="${servicios}">
              <label class="option-card">
                <input type="radio" name="servicio_id" value="${s.id}"
                       data-clave="${s.clave}" data-nombre="${s.nombre}"
                       data-precio="${s.precio}" data-duracion="${s.duracionMinutos}"
                       <c:if test="${s.id == cita.servicioId}">checked</c:if>
                       required>
                <span>${s.nombre}</span>
                <strong>$${s.precio}</strong>
              </label>
            </c:forEach>
          </div>
        </fieldset>

        <div class="form-row">
          <div class="form-group">
            <label for="fecha">3. Fecha</label>
            <input id="fecha" name="fecha" class="form-control" type="date"
                   value="${cita.fecha}" required>
          </div>
          <div class="form-group">
            <label for="hora">Hora</label>
            <input id="hora" name="hora" class="form-control" type="time"
                   min="09:00" max="19:00" value="${cita.hora}" required>
          </div>
        </div>

        <p class="form-message" id="formMessage" role="alert" aria-live="polite"></p>
      </section>

      <aside class="summary-card" aria-label="Resumen de reserva">
        <h2>Resumen de reserva</h2>
        <div class="summary-line"><span>Cliente</span><strong data-summary="cliente"><c:out value="${cita.clienteNombre}" default="Pendiente"/></strong></div>
        <div class="summary-line"><span>Servicio</span><strong data-summary="servicio"><c:out value="${cita.servicioNombre}" default="Pendiente"/></strong></div>
        <div class="summary-line"><span>Barbero</span><strong data-summary="barbero"><c:out value="${cita.barberoNombre}" default="Pendiente"/></strong></div>
        <div class="summary-line"><span>Fecha</span><strong data-summary="fecha">${not empty cita.fecha ? cita.fecha : 'Pendiente'}</strong></div>
        <div class="summary-line"><span>Hora</span><strong data-summary="hora">${not empty cita.hora ? cita.hora : 'Pendiente'}</strong></div>
        <div class="summary-line"><span>Duracion</span><strong data-summary="duracion">${cita.servicioDuracion} min</strong></div>
        <div class="summary-total"><span>Total</span><strong data-summary="total">$${cita.servicioPrecio} COP</strong></div>
        <button class="btn-confirm" type="submit">Guardar cambios</button>
        <a href="${pageContext.request.contextPath}/citas"
           style="display: block; text-align: center; margin-top: 12px; color: #d7d7d7; text-decoration: none; font-weight: 800; text-transform: uppercase; font-size: 0.85rem;">
          Cancelar
        </a>
      </aside>
    </form>
  </main>

  <footer>
    <p>Barberia Harry Estilos &copy; 2026. Proyecto formativo SENA.</p>
  </footer>
</body>
</html>
