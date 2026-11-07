# 💈 Barbería Harry Estilos — Sistema Web de Gestión y Agendamiento

[![SENA ADSO](https://img.shields.io/badge/SENA-ADSO_Ficha_3336210-39a900?style=for-the-badge&logo=sena)](https://oferta.senasofiaplus.edu.co/)
[![Java EE](https://img.shields.io/badge/Java_EE-Servlet_6.0-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://jakarta.ee/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React_19-Vite-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://react.dev/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Postman](https://img.shields.io/badge/Postman-Testing-FF6C37?style=for-the-badge&logo=postman&logoColor=white)](https://www.postman.com/)

Repositorio oficial del proyecto formativo desarrollado para el programa de formación **Análisis y Desarrollo de Software (ADSO) — SENA (Ficha 3336210)**, Centro de Teleinformática y Producción Industrial (CTPI), Regional Cauca.

---

## 📌 Resumen del Proyecto

**Barbería Harry Estilos** es una solución de software integral orientada a modernizar y optimizar la administración de turnos, agendamiento de citas, control de servicios y catálogo de barberos profesionales. El sistema cuenta con una arquitectura desacoplada, escalable y modular, estructurada en tres componentes clave que comparten la base de datos relacional MySQL:

1. **Módulo Web Principal (Java EE / MVC):** Desarrollado con Jakarta Servlets 6.0, JSP/JSTL y capa DAO JDBC sobre Apache Tomcat 10.1 (puerto `8080`).
2. **Servicio Web REST (Spring Boot API):** Microservicio desacoplado con Spring Boot 3.3.4 (puerto `8081`) que expone endpoints RESTful para autenticación, gestión de barberos, servicios y operaciones CRUD completas sobre citas.
3. **Módulo Front-end Interactivo (React + Vite):** SPA moderna para agendamiento de citas con validación en tiempo real, cálculo dinámico de tiempos/tarifas y accesibilidad web.

---

## 📂 Arquitectura del Repositorio

```text
barberia-harry-estilos/
│
├── 📁 frontend/                         # 🌟 MÓDULO REACT SPA (GA7-220501096-AA4-EV03)
│   ├── docs/                            # Matriz de trazabilidad y requerimientos
│   ├── src/                             # Componentes React (agendamiento, servicios, citas)
│   ├── package.json                     # React 19 + Vite
│   └── README.md                        # Guía del módulo frontend
│
├── 📁 servicio-auth-api/                # 🚀 API REST SPRING BOOT (GA7-AA5 & GA8-AA1)
│   ├── src/main/java/com/harryestilos/  # Controllers (Auth, Citas, Barberos, Servicios), DAOs y Modelos
│   ├── src/test/java/                   # Pruebas unitarias automatizadas (JUnit 5)
│   ├── pom.xml                          # Maven con spring-boot-starter-web y MySQL Connector
│   └── README.md                        # Documentación técnica de endpoints REST
│
├── 📁 src/main/java/com/harryestilos/   # ☕ BACK-END JAVA WEB MVC (GA7-AA2)
│   ├── dao/                             # Capa de Acceso a Datos JDBC
│   ├── model/                           # Entidades del Dominio (POJOs)
│   └── servlet/                         # Controladores HTTP (Tomcat puerto 8080)
│
├── 📁 src/main/resources/db/init.sql     # Script DDL/DML de la base de datos MySQL (barberia_harry_estilos)
├── 📁 src/main/webapp/                   # Vistas JSP, estilos y recursos web
│
├── 📁 evidencias/                       # 📑 CARPETAS OFICIALES DE EVIDENCIAS SENA
│   ├── GA7-220501096-AA5-EV02/          # Testing servicio autenticación (Postman + video + informe)
│   ├── GA7-220501096-AA5-EV03/          # Diseño y desarrollo servicios web CRUD (código + informe)
│   ├── GA7-220501096-AA5-EV04/          # API del proyecto (Colección Postman, reporte y video demostrativo)
│   ├── GA8-220501096-AA1-EV01/          # Diseño de integración de módulos web y API (Diagramas UML SVG/PNG)
│   └── GA8-220501096-AA1-EV02/          # Módulos integrados y manual técnico (JDBC, ejecutables y pruebas)
│
├── 📄 pom.xml                           # Descriptor Maven principal (WAR Java Web)
├── 📄 INICIAR_PROYECTO.bat              # Script de arranque rápido (Windows)
└── 📄 MANUAL_DE_USUARIO_Y_DESPLIEGUE.md # Manual técnico y despliegue del componente Java Web
```

---

## 🚀 Puesta en Marcha

### 1. Base de Datos (MySQL 8.0)
1. Ejecutar el script `src/main/resources/db/init.sql` en MySQL para crear la base de datos `barberia_harry_estilos` y las tablas (`usuarios`, `clientes`, `barberos`, `servicios`, `citas`).
2. La base de datos es compartida tanto por el módulo Web Java como por el servicio API REST.

### 2. Servicio Web REST (`/servicio-auth-api`) — Puerto 8081
1. Ingrese a la carpeta del servicio:
   ```bash
   cd servicio-auth-api
   ```
2. Ejecute las pruebas unitarias:
   ```bash
   mvn test
   ```
3. Inicie el servidor embebido Spring Boot:
   ```bash
   mvn spring-boot:run
   ```
   El API queda disponible en `http://localhost:8081`. Endpoints disponibles en `servicio-auth-api/README.md`.

### 3. Módulo Web Principal (Java Web MVC / Tomcat) — Puerto 8080
1. Compilar el WAR con Maven:
   ```bash
   mvn clean package
   ```
2. Desplegar el archivo `target/barberia-harry-estilos.war` en Apache Tomcat 10.1+ o usar el asistente `INICIAR_PROYECTO.bat`.
3. Disponible en `http://localhost:8080/barberia-harry-estilos`.

### 4. Front-end React SPA (`/frontend`) — Puerto 5173
1. Ingrese a la carpeta `frontend`:
   ```bash
   cd frontend
   ```
2. Instale dependencias e inicie el entorno de desarrollo:
   ```bash
   npm install
   npm run dev
   ```

---

## 📋 Evidencias del Proyecto Formativo en este Repositorio

| Código de Evidencia | Nombre de la Actividad | Componente / Entregables |
| :--- | :--- | :--- |
| **GA7-220501096-AA2-EV01** | Codificación de módulos del software | Java Web MVC / Servlets Jakarta EE en Tomcat |
| **GA7-220501096-AA2-EV02** | Informe técnico de plan de trabajo y pruebas | Manual técnico y pruebas unitarias de software |
| **GA7-220501096-AA4-EV02** | Definición de componentes front-end | Arquitectura de componentes de interfaz |
| **GA7-220501096-AA4-EV03** | Componente front-end del proyecto formativo | Módulo SPA interactivo en `/frontend` (React + Vite) |
| **GA7-220501096-AA5-EV01** | Servicio web REST de autenticación | API REST Spring Boot (`/api/usuario/login`, `/registro`) |
| **GA7-220501096-AA5-EV02** | Testing de servicio de autenticación con Postman | Colección Postman, reporte de pruebas y video demostrativo |
| **GA7-220501096-AA5-EV03** | Diseño y desarrollo de servicios web - proyecto | CRUD REST completo (`/api/citas`, `/api/barberos`, `/api/servicios`) |
| **GA7-220501096-AA5-EV04** | API del proyecto | Testing integral con Postman, informe técnico y video demostrativo |
| **GA8-220501096-AA1-EV01** | Diseño de integración de módulos web y API | Diagramas UML de paquetes, componentes y clases (SVG / PNG) |
| **GA8-220501096-AA1-EV02** | Módulos integrados y manual técnico | Configuración JDBC compartida, ejecutables y verificación cruzada |

---

## 👥 Equipo de Desarrollo

**Programa:** Análisis y Desarrollo de Software (ADSO)  
**Ficha:** 3336210  
**Centro de Formación:** Centro de Teleinformática y Producción Industrial (CTPI) — Regional Cauca  
**Instructora:** Verónica María Muñoz  

- **Diego Fernando Caicedo Saldarriaga** — [GitHub: @dfcaicedosaldarriaga-cmd](https://github.com/dfcaicedosaldarriaga-cmd)
- **Hernán Salazar Ospina**
- **José Arteta Angulo**
