# 📖 MANUAL DE DESPLIEGUE E INVENTARIO DEL PROYECTO
## BARBERÍA HARRY ESTILOS — JAVA WEB MVC
**Evidencias SENA:** GA7-220501096-AA2-EV01 & GA7-220501096-AA2-EV02

---

## 📋 1. REQUISITOS PREVIOS DEL SISTEMA

Para ejecutar la aplicación en cualquier computadora, se deben tener instaladas las siguientes herramientas:

1. **Java Development Kit (JDK 17 o superior):**
   - Recomendado: JDK 17, 21 o 26.
   - Variable de entorno `JAVA_HOME` configurada.
2. **Apache Maven 3.9.0+:** (Para gestión de dependencias y compilación del archivo WAR).
3. **MySQL Server 8.0+:** (Base de datos relacional).
4. **Apache Tomcat 10.1+:** (Servidor de aplicaciones compatible con Jakarta EE 10 / Servlet 6.0).
5. **Navegador Web:** Google Chrome, Microsoft Edge, Firefox o Brave.

---

## 📁 2. LISTA E INVENTARIO DE ARCHIVOS DEL PROYECTO

Al descomprimir el archivo `.zip` del proyecto, tus compañeros encontrarán la siguiente estructura ordenada:

```text
barberia-harry-estilos/
│
├── 🚀 INICIAR_PROYECTO.bat         <-- Script de inicio rápido en 1 clic para Windows
├── 📄 pom.xml                      <-- Archivo de configuración Maven (dependencias)
├── 📄 MANUAL_DE_USUARIO_Y_DESPLIEGUE.md <-- Este manual
│
├── 📂 frontend-original/            <-- Respaldo de los HTML estáticos originales
│   ├── index.html
│   ├── servicios.html
│   ├── agendamiento.html
│   └── confirmacion.html
│
└── 📂 src/
    └── main/
        ├── 📂 java/com/harryestilos/
        │   ├── 📂 model/           <-- Modelos / POJOs de Datos
        │   │   ├── Barbero.java
        │   │   ├── Servicio.java
        │   │   ├── Cliente.java
        │   │   └── Cita.java
        │   │
        │   ├── 📂 dao/             <-- Capa de Acceso a Datos (JDBC/SQL)
        │   │   ├── ConexionDB.java
        │   │   ├── BarberoDAO.java
        │   │   ├── ServicioDAO.java
        │   │   ├── ClienteDAO.java
        │   │   └── CitaDAO.java
        │   │
        │   └── 📂 servlet/         <-- Controladores Web (HTTP Requests)
        │       ├── InicioServlet.java
        │       ├── ServiciosServlet.java
        │       ├── AgendamientoServlet.java
        │       ├── CitasServlet.java
        │       └── EliminarCitaServlet.java
        │
        ├── 📂 resources/db/         <-- Base de Datos
        │   └── init.sql            <-- Script SQL de creación e inserción de datos
        │
        └── 📂 webapp/              <-- Vistas y Recursos Web
            ├── 📂 WEB-INF/
            │   └── web.xml         <-- Descriptor de despliegue Jakarta EE
            ├── 📂 css/
            │   └── styles.css      <-- Estilos CSS responsivos (Diseño original)
            ├── 📂 js/
            │   └── app.js          <-- Lógica JavaScript del front-end
            ├── 📂 img/             <-- Logotipos e íconos SVG
            │
            ├── 📄 index.jsp        <-- Página de Inicio Dinámica
            ├── 📄 servicios.jsp    <-- Catálogo de Servicios
            ├── 📄 agendamiento.jsp <-- Formulario de Reserva de Citas
            ├── 📄 confirmacion.jsp <-- Resumen de Confirmación de Cita
            ├── 📄 citas.jsp        <-- Módulo CRUD (Lista de Citas Agendadas)
            └── 📄 editar-cita.jsp  <-- Módulo CRUD (Formulario de Edición)
```

---

## 🛠️ 3. GUÍA PASO A PASO PARA EJECUTAR EL PROYECTO

### **Opción A: Ejecución Automática (Recomendada)**
1. Descomprimir el archivo `.zip`.
2. Abrir la carpeta del proyecto.
3. Hacer doble clic sobre el archivo **`INICIAR_PROYECTO.bat`**.
4. El script encenderá MySQL y Tomcat automáticamente.
5. Abrir el navegador e ingresar a la URL:  
   👉 **`http://localhost:8080/barberia-harry-estilos/inicio`**

---

### **Opción B: Instalación y Ejecución Manual**

#### Paso 1: Configurar la Base de Datos MySQL
Abrir MySQL Workbench o la consola de comandos de MySQL y ejecutar el script `src/main/resources/db/init.sql`:

```sql
CREATE DATABASE IF NOT EXISTS barberia_harry_estilos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE barberia_harry_estilos;

-- El script creará las tablas: barberos, servicios, clientes y citas
-- e insertará los datos iniciales de Barberos (Harry, Andres, Camilo) y Servicios.
```

#### Paso 2: Compilar el Proyecto con Maven
Abrir la terminal dentro de la carpeta del proyecto y ejecutar:

```bash
mvn clean package
```
Esto creará el archivo ejecutable `barberia-harry-estilos.war` dentro de la carpeta `target/`.

#### Paso 3: Desplegar en Apache Tomcat
1. Copiar el archivo `target/barberia-harry-estilos.war` a la carpeta `webapps/` de Apache Tomcat.
2. Iniciar el servidor Apache Tomcat (`bin/startup.bat`).
3. Ingresar al navegador en: `http://localhost:8080/barberia-harry-estilos/inicio`

---

## 🔀 4. RUTAS Y MÓDULOS DE NAVEGACIÓN (FLUJOS DE LA APP)

| Módulo / Acción | URL en el Navegador | Descripción |
|---|---|---|
| **Inicio** | `/inicio` | Presentación del negocio, hero banner y catálogo destacado. |
| **Servicios** | `/servicios` | Lista detallada de precios ($25K, $14K, $35K) y tiempos de atención. |
| **Agendamiento** | `/agendamiento` | Formulario para agendar cita (Nombre, Barbero, Servicio, Fecha, Hora). |
| **Confirmación** | `/confirmacion?id=X` | Recibo de confirmación generado tras registrar la cita. |
| **Gestión (CRUD)** | `/citas` | Muestra la tabla de citas registradas en BD MySQL. Permite **Editar** y **Eliminar**. |
| **Editar Cita** | `/citas?id=X` | Formulario cargado con la cita a modificar. |

---

## 💡 5. CRÉDITOS Y TECNOLOGÍAS UTILIZADAS
- **Front-End:** HTML5, CSS3 (Vanilla CSS variables), JavaScript ES6.
- **Back-End:** Java 17+, Jakarta Servlets 6.0, JavaServer Pages (JSP) + JSTL 3.0.
- **Persistencia & BD:** JDBC con MySQL Connector/J 8.4 y MySQL Server 8.0+.
- **Arquitectura:** MVC (Modelo-Vista-Controlador) + Patrón DAO.
