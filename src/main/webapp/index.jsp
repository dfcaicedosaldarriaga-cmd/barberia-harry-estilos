<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Barberia Harry Estilos - Inicio</title>
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
      <a class="active" href="${pageContext.request.contextPath}/inicio">Inicio</a>
      <a href="${pageContext.request.contextPath}/servicios">Servicios</a>
      <a href="${pageContext.request.contextPath}/inicio#nosotros">Nosotros</a>
      <a href="${pageContext.request.contextPath}/inicio#contacto">Contacto</a>
      <a href="${pageContext.request.contextPath}/citas">Mis Citas</a>
    </nav>
  </header>

  <main>
    <section class="hero-section" aria-labelledby="hero-title">
      <div class="hero-copy">
        <p class="eyebrow">Clasicos y modernos</p>
        <h1 id="hero-title">Estilo y precision para el hombre actual</h1>
        <p>
          Agenda tu turno en la Barberia Harry Estilos, elige el servicio que necesitas
          y confirma tu reserva en pocos pasos.
        </p>
        <div class="hero-actions">
          <a class="btn-primary" href="${pageContext.request.contextPath}/agendamiento">Pide tu cita</a>
          <a class="btn-secondary" href="${pageContext.request.contextPath}/servicios">Ver servicios</a>
        </div>
      </div>
      <div class="hero-visual" aria-label="Identidad visual de Barberia Harry Estilos">
        <div class="barber-pole" aria-hidden="true"></div>
        <div class="hero-badge">
          <strong>Harry Estilos</strong>
          <span>Cortes, barba y paquetes</span>
        </div>
      </div>
    </section>

    <section class="section-block" aria-labelledby="servicios-destacados">
      <div class="section-heading">
        <p class="eyebrow">Catalogo</p>
        <h2 id="servicios-destacados">Servicios destacados</h2>
      </div>
      <div class="cards-grid">
        <c:forEach var="s" items="${servicios}">
          <article class="service-card">
            <span class="service-icon" aria-hidden="true">
              <c:choose>
                <c:when test="${s.clave == 'corte'}">&#9986;</c:when>
                <c:when test="${s.clave == 'barba'}">&#129535;</c:when>
                <c:when test="${s.clave == 'paquete'}">&#128142;</c:when>
                <c:otherwise>&#9986;</c:otherwise>
              </c:choose>
            </span>
            <h3>${s.nombre}</h3>
            <p>
              <c:choose>
                <c:when test="${s.clave == 'corte'}">Corte tradicional con tijera, maquina y perfilado fino.</c:when>
                <c:when test="${s.clave == 'barba'}">Diseno, arreglo y perfilado de barba con acabado limpio.</c:when>
                <c:when test="${s.clave == 'paquete'}">Corte, barba y finalizacion para una experiencia integral.</c:when>
              </c:choose>
            </p>
            <strong>$${s.precio} COP</strong>
            <a href="${pageContext.request.contextPath}/servicios#${s.clave}">Ver detalle</a>
          </article>
        </c:forEach>
      </div>
    </section>

    <section class="split-section" id="nosotros" aria-labelledby="nosotros-title">
      <div>
        <p class="eyebrow">Nosotros</p>
        <h2 id="nosotros-title">Atencion cercana, resultado impecable</h2>
      </div>
      <p>
        Harry Estilos ofrece servicios de barberia pensados para clientes que buscan
        practicidad, buena asesoria y una reserva clara. El front-end replica el flujo
        validado en Figma: consulta de servicios, agendamiento y confirmacion.
      </p>
    </section>

    <section class="contact-band" id="contacto" aria-labelledby="contacto-title">
      <div>
        <p class="eyebrow">Contacto</p>
        <h2 id="contacto-title">Reserva tu espacio</h2>
        <p>Horario de atencion: lunes a sabado, 9:00 a. m. - 7:00 p. m.</p>
      </div>
      <a class="btn-primary" href="${pageContext.request.contextPath}/agendamiento">Agendar ahora</a>
    </section>
  </main>

  <footer>
    <p>Barberia Harry Estilos &copy; 2026. Proyecto formativo SENA.</p>
  </footer>
</body>
</html>
