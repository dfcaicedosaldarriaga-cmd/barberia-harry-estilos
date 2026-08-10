<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Barberia Harry Estilos - Servicios</title>
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
      <a class="active" href="${pageContext.request.contextPath}/servicios">Servicios</a>
      <a href="${pageContext.request.contextPath}/inicio#nosotros">Nosotros</a>
      <a href="${pageContext.request.contextPath}/inicio#contacto">Contacto</a>
      <a href="${pageContext.request.contextPath}/citas">Mis Citas</a>
    </nav>
  </header>

  <main>
    <section class="page-title">
      <p class="eyebrow">Catalogo de servicios</p>
      <h1>Elige el servicio ideal para tu estilo</h1>
      <p>Consulta precios, duracion aproximada y agenda el turno desde el mismo flujo.</p>
    </section>

    <section class="service-list" aria-label="Servicios disponibles">
      <c:forEach var="s" items="${servicios}">
        <article class="service-detail" id="${s.clave}">
          <div class="service-media">
            <span aria-hidden="true">
              <c:choose>
                <c:when test="${s.clave == 'corte'}">&#9986;</c:when>
                <c:when test="${s.clave == 'barba'}">&#129535;</c:when>
                <c:when test="${s.clave == 'paquete'}">&#128142;</c:when>
                <c:otherwise>&#9986;</c:otherwise>
              </c:choose>
            </span>
          </div>
          <div>
            <h2>${s.nombre}</h2>
            <p>
              <c:choose>
                <c:when test="${s.clave == 'corte'}">
                  Corte tradicional con tijera y maquina, perfilado fino a navaja y acabado
                  prolijo para mantener una imagen limpia y profesional.
                </c:when>
                <c:when test="${s.clave == 'barba'}">
                  Diseno y perfilado de barba con lineas definidas, hidratacion y terminacion
                  uniforme para resaltar la forma del rostro.
                </c:when>
                <c:when test="${s.clave == 'paquete'}">
                  Corte clasico, arreglo de barba y finalizacion con producto. Recomendado
                  para clientes que desean una transformacion completa en una sola cita.
                </c:when>
              </c:choose>
            </p>
            <div class="meta-row">
              <span>$${s.precio} COP</span>
              <span>${s.duracionMinutos} minutos</span>
            </div>
            <a class="btn-primary" href="${pageContext.request.contextPath}/agendamiento?servicio=${s.clave}">
              Agendar
              <c:choose>
                <c:when test="${s.clave == 'corte'}">corte</c:when>
                <c:when test="${s.clave == 'barba'}">barba</c:when>
                <c:when test="${s.clave == 'paquete'}">paquete</c:when>
              </c:choose>
            </a>
          </div>
        </article>
      </c:forEach>
    </section>
  </main>

  <footer>
    <p>Barberia Harry Estilos &copy; 2026. Proyecto formativo SENA.</p>
  </footer>
</body>
</html>
