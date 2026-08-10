<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Barberia Harry Estilos - Cita agendada</title>
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

  <main class="modal-container">
    <section class="modal-box" aria-labelledby="confirm-title">
      <div class="success-mark" aria-hidden="true">&#9989;</div>
      <p class="eyebrow">Reserva confirmada</p>
      <h1 id="confirm-title">Su cita ha sido agendada con exito</h1>
      <div class="confirmation-details">
        <c:choose>
          <c:when test="${not empty cita}">
            <p><strong>Cliente:</strong> <c:out value="${cita.clienteNombre}"/></p>
            <p><strong>Servicio:</strong> <c:out value="${cita.servicioNombre}"/></p>
            <p><strong>Barbero:</strong> <c:out value="${cita.barberoNombre}"/></p>
            <p><strong>Fecha y hora:</strong> <c:out value="${cita.fecha}"/> - <c:out value="${cita.hora}"/></p>
            <p><strong>Duracion:</strong> ${cita.servicioDuracion} minutos</p>
            <p><strong>Total:</strong> $${cita.servicioPrecio} COP</p>
          </c:when>
          <c:otherwise>
            <p>Te esperamos en Barberia Harry Estilos.</p>
          </c:otherwise>
        </c:choose>
      </div>
      <div class="hero-actions centered">
        <a href="${pageContext.request.contextPath}/agendamiento" class="btn-secondary">Nueva reserva</a>
        <a href="${pageContext.request.contextPath}/inicio" class="btn-primary">Volver al inicio</a>
      </div>
    </section>
  </main>

  <footer>
    <p>Barberia Harry Estilos &copy; 2026. Proyecto formativo SENA.</p>
  </footer>
</body>
</html>
