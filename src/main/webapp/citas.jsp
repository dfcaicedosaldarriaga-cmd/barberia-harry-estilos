<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Barberia Harry Estilos - Gestionar Citas</title>
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
      <a class="active" href="${pageContext.request.contextPath}/citas">Mis Citas</a>
    </nav>
  </header>

  <main>
    <section class="page-title">
      <p class="eyebrow">Gestion de citas</p>
      <h1>Citas agendadas</h1>
      <p>Consulta, edita o elimina tus reservas.</p>
    </section>

    <section class="service-list" aria-label="Lista de citas" style="margin-top: 30px;">
      <c:choose>
        <c:when test="${empty citas}">
          <div class="contact-band" style="grid-template-columns: 1fr auto;">
            <div>
              <p class="eyebrow">Sin citas</p>
              <h2>No hay citas registradas</h2>
              <p>Agenda tu primera cita y comienza a disfrutar del mejor servicio.</p>
            </div>
            <a class="btn-primary" href="${pageContext.request.contextPath}/agendamiento">Agendar ahora</a>
          </div>
        </c:when>
        <c:otherwise>
          <div style="overflow-x: auto;">
            <table style="width: 100%; border-collapse: collapse; background: var(--surface); border-radius: 8px; overflow: hidden; box-shadow: 0 8px 24px rgba(22,22,22,0.08);">
              <thead>
                <tr style="background: #252525; color: #fff; text-transform: uppercase; font-size: 0.85rem; font-weight: 900;">
                  <th style="padding: 14px 18px; text-align: left;">ID</th>
                  <th style="padding: 14px 18px; text-align: left;">Cliente</th>
                  <th style="padding: 14px 18px; text-align: left;">Servicio</th>
                  <th style="padding: 14px 18px; text-align: left;">Barbero</th>
                  <th style="padding: 14px 18px; text-align: left;">Fecha</th>
                  <th style="padding: 14px 18px; text-align: left;">Hora</th>
                  <th style="padding: 14px 18px; text-align: left;">Precio</th>
                  <th style="padding: 14px 18px; text-align: center;">Acciones</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="c" items="${citas}">
                  <tr style="border-bottom: 1px solid var(--line);">
                    <td style="padding: 14px 18px; font-weight: 800;">${c.id}</td>
                    <td style="padding: 14px 18px;"><c:out value="${c.clienteNombre}"/></td>
                    <td style="padding: 14px 18px;"><c:out value="${c.servicioNombre}"/></td>
                    <td style="padding: 14px 18px;"><c:out value="${c.barberoNombre}"/></td>
                    <td style="padding: 14px 18px;">${c.fecha}</td>
                    <td style="padding: 14px 18px;">${c.hora}</td>
                    <td style="padding: 14px 18px; font-weight: 800;">$${c.servicioPrecio} COP</td>
                    <td style="padding: 14px 18px; text-align: center;">
                      <div style="display: flex; gap: 8px; justify-content: center;">
                        <a href="${pageContext.request.contextPath}/citas?id=${c.id}"
                           class="btn-primary"
                           style="min-height: 36px; padding: 8px 14px; font-size: 0.8rem;">
                          Editar
                        </a>
                        <form action="${pageContext.request.contextPath}/eliminar-cita" method="POST"
                              style="display:inline;"
                              onsubmit="return confirm('¿Desea eliminar la cita #${c.id}?');">
                          <input type="hidden" name="id" value="${c.id}">
                          <button type="submit" class="btn-secondary"
                                  style="min-height: 36px; padding: 8px 14px; font-size: 0.8rem; background: #a33a2b; color: #fff;">
                            Eliminar
                          </button>
                        </form>
                      </div>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>

          <div style="margin-top: 24px; text-align: center;">
            <a class="btn-primary" href="${pageContext.request.contextPath}/agendamiento">Agendar nueva cita</a>
          </div>
        </c:otherwise>
      </c:choose>
    </section>
  </main>

  <footer>
    <p>Barberia Harry Estilos &copy; 2026. Proyecto formativo SENA.</p>
  </footer>
</body>
</html>
