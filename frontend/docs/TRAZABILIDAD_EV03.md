# Matriz de Trazabilidad de la Evidencia GA7-220501096-AA4-EV03

**Proyecto Formativo:** Barbería Harry Estilos  
**Aprendiz:** Diego Fernando Caicedo Saldarriaga  
**Actividad:** Componente front-end del proyecto formativo y proyectos de clase  

---

| Requisito de la Guía SENA | Implementación en Barbería Harry Estilos | Nivel de Cumplimiento |
| :--- | :--- | :---: |
| **Codificación de módulo web** | Módulo de agendamiento y gestión de citas desarrollado en React 19 con Vite, modularizado por capas. | 100% |
| **Alineación con artefactos previos** | Respeta las entidades del modelo de dominio: Clientes, Barberos, Servicios y Citas, derivadas de los diagramas de clases, casos de uso e historias de usuario previas. | 100% |
| **Código con comentarios** | Cada componente (`App`, `BookingForm`, `AppointmentSummary`, `AppointmentList`, `ServiceCard`, `Alert`, `Header`, `Footer`), servicio y validador cuenta con bloques descriptivos estándar **JSDoc**, especificando parámetros, tipos de retorno, accesibilidad y lógica de negocio. | 100% |
| **Estándares de codificación** | Arquitectura limpia (separación en `components/`, `data/`, `services/`, `utils/`, `styles/`), principios SOLID, componentes funcionales con hooks, nombres descriptivos, validaciones robustas y accesibilidad web (ARIA y HTML5 semántico). | 100% |
| **Herramientas de versionamiento** | Versionado bajo Git y publicado en GitHub en la rama principal `main` del repositorio oficial: `https://github.com/dfcaicedosaldarriaga-cmd/barberia-harry-estilos`. | 100% |
| **Lineamientos de entrega** | Carpeta y comprimido nombrados `DIEGO_FERNANDO_CAICEDO_SALDARRIAGA_AA4_EV03.zip` con los archivos del proyecto (excluyendo carpetas innecesarias como `node_modules` para optimizar peso) y el archivo formal `ENLACE_REPOSITORIO.txt`. | 100% |

---

## Componentes y Responsabilidades

| Componente | Responsabilidad |
| :--- | :--- |
| `Header` y `Footer` | Identidad corporativa de Barbería Harry Estilos y navegación semántica accesible. |
| `BookingForm` | Captura y validación en tiempo real de los campos requeridos para la reserva. |
| `ServiceCard` | Tarjeta interactiva y accesible (`aria-pressed`) para seleccionar servicios con tarifa y duración. |
| `AppointmentSummary` | Panel lateral dinámico que calcula y refleja costo total y duración en vivo. |
| `AppointmentList` | Listado histórico de citas registradas y control de cancelación. |
| `Alert` | Retroalimentación dinámica para lectores de pantalla (`role="status"`, `aria-live="polite"`). |
