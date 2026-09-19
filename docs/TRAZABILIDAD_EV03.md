# Trazabilidad de la evidencia EV03

| Requisito de EV03 | Aplicación en Barber System |
| --- | --- |
| Codificación de un módulo web | Módulo de agendamiento de citas en React. |
| Artefactos previos | El flujo atiende cliente, servicio, barbero, fecha y hora; entidades presentes en el proyecto de barbería suministrado. Debe contrastarse con las historias de usuario y prototipos oficiales antes de la entrega. |
| Comentarios | `appointmentService.js` y `App.jsx` incluyen comentarios sobre decisiones no evidentes. |
| Estándares de código | Componentes con responsabilidad única, nombres descriptivos, constantes, validación separada y estilos centralizados. |
| Versionamiento | Repositorio Git local inicializado a nombre de Diego Fernando Caicedo Saldarriaga y remoto configurado hacia `https://github.com/dfcaicedosaldarriaga-cmd/Programa-Git`. |
| Archivos del proyecto | Código fuente React, configuración Vite, instrucciones de ejecución y archivo del enlace incluidos. |

## Componentes implementados

| Componente | Responsabilidad |
| --- | --- |
| `Header` y `Footer` | Estructura global y navegación del módulo. |
| `BookingForm` | Captura y valida información de la cita. |
| `ServiceCard` | Presenta y permite seleccionar un servicio reutilizable. |
| `AppointmentSummary` | Refleja la reserva mientras el usuario diligencia el formulario. |
| `AppointmentList` | Consulta y permite cancelar citas. |
| `Alert` | Comunica resultados de las acciones y validaciones. |

## Pendientes del aprendiz antes de enviar

1. Validar que el alcance coincide con los artefactos oficiales del proyecto formativo.
2. Sustituir el simulador de `src/services/appointmentService.js` por la API real, si el alcance exige persistencia.
3. Publicar la rama `main` con los cambios de esta evidencia en el repositorio remoto.
4. Entregar el ZIP `DIEGO_FERNANDO_CAICEDO_SALDARRIAGA_AA4_EV03.zip`.
