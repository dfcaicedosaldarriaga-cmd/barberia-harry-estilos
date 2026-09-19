# 💈 Barbería Harry Estilos — Sistema Web de Gestión y Agendamiento

[![SENA ADSO](https://img.shields.io/badge/SENA-ADSO-39a900?style=for-the-badge&logo=sena)](https://oferta.senasofiaplus.edu.co/)
[![Java EE](https://img.shields.io/badge/Java_EE-Servlet_6.0-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://jakarta.ee/)
[![React](https://img.shields.io/badge/React_19-Vite-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://react.dev/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)

Repositorio oficial del proyecto formativo desarrollado por **Diego Fernando Caicedo Saldarriaga** para el programa de formación **Análisis y Desarrollo de Software (ADSO) — SENA**.

---

## 📌 Resumen del Proyecto

**Barbería Harry Estilos** es una solución integral diseñada para modernizar y optimizar la administración de turnos, agendamiento de citas, control de servicios y catálogo de barberos profesionales. El software implementa una arquitectura desacoplada y escalable, estructurada en dos componentes principales:

1. **Componente Back-end (Java Web MVC):** Desarrollado con Jakarta EE (Servlets 6.0), JSP/JSTL, DAO JDBC y base de datos relacional MySQL 8.0. (Evidencias previas `GA7-220501096-AA2-EV01` y `GA7-220501096-AA2-EV02`).
2. **Componente Front-end (React + Vite):** Módulo SPA interactivo de agendamiento de citas con validación en tiempo real, cálculo dinámico de tiempos/tarifas y accesibilidad web. (Evidencia actual **`GA7-220501096-AA4-EV03`**).

---

## 📂 Arquitectura del Repositorio

```text
barberia-harry-estilos/
│
├── 📁 frontend/                         # 🌟 MÓDULO REACT (GA7-220501096-AA4-EV03)
│   ├── docs/
│   │   └── TRAZABILIDAD_EV03.md         # Matriz de cumplimiento y trazabilidad SENA
│   ├── src/
│   │   ├── components/                  # Componentes reutilizables con estándar JSDoc
│   │   │   ├── appointments/            # Lista y cancelación de citas
│   │   │   ├── booking/                 # Formulario reactivo y resumen dinámico
│   │   │   ├── common/                  # Alertas accesibles (role="status")
│   │   │   ├── layout/                  # Header y Footer con identidad oficial
│   │   │   └── services/                # Tarjetas interactivas de servicios
│   │   ├── data/barberiaData.js         # Datos semilla vinculados al modelo de datos
│   │   ├── services/appointmentService.js # Capa asíncrona desacoplada para API
│   │   ├── styles/index.css             # Estilos centralizados y responsivos
│   │   ├── utils/appointmentValidation.js # Validaciones y formateo regional (es-CO)
│   │   ├── App.jsx                      # Orquestador del estado y flujo
│   │   └── main.jsx                     # Punto de entrada StrictMode
│   ├── ENLACE_REPOSITORIO.txt           # Ficha técnica de entrega de la evidencia
│   ├── index.html                       # HTML5 con metadatos y accesibilidad
│   ├── package.json                     # Scripts y dependencias de React 19 + Vite
│   └── README.md                        # Guía específica del módulo front-end
│
├── 📁 src/main/java/com/harryestilos/   # ☕ BACK-END JAVA WEB MVC
│   ├── dao/                             # Capa de Acceso a Datos (Barbero, Cita, Cliente, Servicio)
│   ├── model/                           # Entidades del Dominio / POJOs
│   └── servlet/                         # Controladores HTTP (Agendamiento, Citas, Servicios)
│
├── 📁 src/main/resources/db/init.sql     # Script DDL/DML de la base de datos MySQL
├── 📁 src/main/webapp/                   # Vistas JSP y recursos web originales
├── 📁 frontend-original/                 # Maquetas HTML estáticas iniciales
├── 📄 pom.xml                           # Descriptor Maven de compilación y empaquetado WAR
├── 📄 INICIAR_PROYECTO.bat              # Script de inicio rápido en 1 clic (Windows)
└── 📄 MANUAL_DE_USUARIO_Y_DESPLIEGUE.md # Manual técnico del componente Java Web
```

---

## 🚀 Puesta en Marcha

### 1. Ejecución del Módulo Front-end React (`/frontend`) — Evidencia EV03

1. Ingrese al directorio del front-end:
   ```bash
   cd frontend
   ```
2. Instale dependencias con Node.js 20+:
   ```bash
   npm install
   ```
3. Inicie el servidor de desarrollo en tiempo real:
   ```bash
   npm run dev
   ```
4. Ingrese a la dirección local reportada (por defecto `http://localhost:5173`).

Para compilar el módulo para producción:
```bash
npm run build
```

### 2. Ejecución del Back-end Java Web (Servlets + Tomcat + MySQL)

1. Crear la base de datos ejecutando el script `src/main/resources/db/init.sql` en MySQL.
2. Compilar con Apache Maven:
   ```bash
   mvn clean package
   ```
3. Desplegar el archivo `target/barberia-harry-estilos.war` en Apache Tomcat 10.1+ o ejecutar mediante `INICIAR_PROYECTO.bat`.
4. Consultar `MANUAL_DE_USUARIO_Y_DESPLIEGUE.md` para detalles específicos de configuración de puertos y credenciales.

---

## 📋 Evidencias del Proyecto Formativo en este Repositorio

| Código de Evidencia | Nombre de la Actividad | Componente |
| :--- | :--- | :---: |
| **GA7-220501096-AA2-EV01** | Codificación de módulos del software (Backend) | Java Web MVC / Servlets |
| **GA7-220501096-AA2-EV02** | Informe técnico de plan de trabajo y pruebas | Manual y Pruebas Unitarias |
| **GA7-220501096-AA4-EV02** | Definición de componentes front-end | Arquitectura de Componentes |
| **GA7-220501096-AA4-EV03** | Componente front-end del proyecto formativo y proyectos de clase | **Módulo React en `/frontend`** |

---

## 👨‍💻 Autor
**Diego Fernando Caicedo Saldarriaga**  
Aprendiz ADSO — Centro de Formación SENA  
GitHub: [@dfcaicedosaldarriaga-cmd](https://github.com/dfcaicedosaldarriaga-cmd)