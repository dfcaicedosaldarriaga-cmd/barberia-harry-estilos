# Barbería Harry Estilos — Módulo Front-end (GA7-220501096-AA4-EV03)

Repositorio del proyecto formativo: [barberia-harry-estilos](https://github.com/dfcaicedosaldarriaga-cmd/barberia-harry-estilos)  
**Aprendiz:** Diego Fernando Caicedo Saldarriaga  
**Programa:** Análisis y Desarrollo de Software (ADSO) — SENA  
**Actividad:** GA7-220501096-AA4-EV03: Componente front-end del proyecto formativo y proyectos de clase  

---

## 📌 Descripción del Módulo

Este módulo constituye el **componente front-end reactivo de agendamiento de citas** para el sistema de información de la **Barbería Harry Estilos**. Ha sido diseñado e implementado con base en los artefactos del ciclo de software previos (diagramas de clases, historias de usuario, prototipos y plan de construcción), integrando estándares de accesibilidad web, modularidad y código documentado con JSDoc.

### ✂️ Funcionalidades Implementadas

- **Catálogo interactivo de servicios:** Selección visual de servicios con descripción, tarifa en COP y duración estimada.
- **Formulario de reserva reactivo:** Captura guiada de cliente, barbero y fecha/hora con validaciones en tiempo real.
- **Resumen dinámico de la cita:** Cálculo instantáneo del costo total y tiempo del servicio conforme se interactúa con la interfaz.
- **Gestión de citas:** Listado interactivo de reservas confirmadas y capacidad de cancelación con retroalimentación accesible (`role="status"`).
- **Diseño responsivo:** Adaptado para dispositivos móviles, tabletas y computadores de escritorio.

---

## 🛠️ Tecnologías y Estándares

- **React 19:** Biblioteca principal basada en componentes funcionales y Hooks (`useState`, `useMemo`).
- **Vite:** Herramienta de compilación y empaquetado optimizada para desarrollo ágil.
- **JavaScript Moderno (ES Modules):** Sintaxis limpia y modular.
- **CSS3 Personalizado:** Estilos centralizados y responsivos sin dependencias pesadas.
- **Estándares de Codificación:** Separación de capas, principios SOLID y documentación exhaustiva con **JSDoc**.
- **Control de Versiones:** Git y GitHub como repositorio remoto oficial.

---

## 📂 Estructura del Código

```text
frontend/
├── docs/
│   └── TRAZABILIDAD_EV03.md      # Matriz de trazabilidad con la guía SENA
├── src/
│   ├── components/
│   │   ├── appointments/         # Visualización y cancelación de citas
│   │   ├── booking/              # Formulario y resumen dinámico
│   │   ├── common/               # Componentes genéricos accesibles (Alert)
│   │   ├── layout/               # Header y Footer con identidad de marca
│   │   └── services/             # Tarjetas interactivas de servicios
│   ├── data/
│   │   └── barberiaData.js       # Modelos y datos de prueba alineados a BD
│   ├── services/
│   │   └── appointmentService.js # Capa de servicio desacoplada para API
│   ├── styles/
│   │   └── index.css             # Hoja de estilos centralizada
│   ├── utils/
│   │   └── appointmentValidation.js # Reglas de validación y formateo es-CO
│   ├── App.jsx                   # Orquestador del estado y flujo del módulo
│   └── main.jsx                  # Punto de montaje del DOM con StrictMode
├── ENLACE_REPOSITORIO.txt        # Documento oficial de enlace remoto
├── index.html                    # Plantilla HTML5 con metadatos y accesibilidad
└── package.json                  # Dependencias y scripts del proyecto
```

---

## 🚀 Instrucciones de Ejecución

1. Asegúrese de contar con **Node.js 20+** instalado.
2. Clone o descargue el repositorio:
   ```bash
   git clone https://github.com/dfcaicedosaldarriaga-cmd/barberia-harry-estilos.git
   cd barberia-harry-estilos/frontend
   ```
3. Instale las dependencias del proyecto:
   ```bash
   npm install
   ```
4. Inicie el servidor de desarrollo local:
   ```bash
   npm run dev
   ```
5. Abra en su navegador la URL proporcionada por Vite (usualmente `http://localhost:5173`).

Para generar el bundle optimizado para producción:
```bash
npm run build
```

---

## 🔗 Integración con el Backend Java Web

El archivo `src/services/appointmentService.js` implementa el patrón de repositorio para interactuar asíncronamente con los endpoints del backend en Java Web MVC (Servlets y MySQL) implementado en las evidencias anteriores, permitiendo conectar la base de datos sin modificar la interfaz de usuario.
