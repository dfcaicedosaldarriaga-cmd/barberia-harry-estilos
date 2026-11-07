# Servicio web REST — GA7-220501096-AA5-EV01/EV03

Aprendiz: Hernán Salazar Ospina
Proyecto formativo: Barbería Harry Estilos — módulo de sesión (EV01) y CRUD de citas (EV03)

## Qué es esto

Módulo Spring Boot **independiente** del WAR JSP/Servlet principal
(`barberia-harry-estilos`), que expone como servicio web REST el mismo
módulo de inicio de sesión y registro que ya existe en el proyecto.

No se reescribió la lógica de negocio: las clases `UsuarioDAO`, `Usuario`,
`PasswordUtil` y `RegistroValidator` son copias literales de las del
proyecto principal (ver la nota en `UsuarioDAO.java`), y apuntan a la
misma base de datos MySQL `barberia_harry_estilos`, tabla `usuarios`. Un
usuario creado desde el formulario web puede autenticarse por esta API, y
viceversa.

Se mantiene como módulo aparte (y no integrado al WAR existente) para no
arriesgar el despliegue JSP/Servlet que ya funciona: Spring Boot corre su
propio servidor embebido, en un puerto distinto (8081) al de Tomcat (8080),
así que ambos pueden ejecutarse al mismo tiempo sin conflicto.

## Tecnologías

- Java 17, Spring Boot 3.3.4 (`spring-boot-starter-web`).
- MySQL Connector/J 8.3.0 (misma versión que el proyecto principal).
- Maven (empaquetado JAR, servidor embebido).

## Endpoints

### POST /api/usuario/login

Parámetros (`application/x-www-form-urlencoded`):
- `usuario`: correo electrónico registrado.
- `password`: contraseña en texto plano.

Respuestas:
- `200 OK` — `Autenticación satisfactoria.`
- `400 Bad Request` — parámetros faltantes.
- `401 Unauthorized` — `Error en la autenticación.`
- `403 Forbidden` — `Error en la autenticación: la cuenta se encuentra inactiva.`

### POST /api/usuario/registro

Parámetros (`application/x-www-form-urlencoded`):
- `nombre`, `usuario` (correo), `password` (mínimo 6 caracteres).

Respuestas:
- `201 Created` — `Usuario registrado correctamente.`
- `400 Bad Request` — datos incompletos o inválidos.
- `409 Conflict` — `Ya existe una cuenta registrada con ese correo.`

### CRUD de citas y catálogos (GA7-220501096-AA5-EV03)

Reutiliza `CitaDAO`, `BarberoDAO`, `ServicioDAO` y `ClienteDAO` del
proyecto principal (copiadas igual que `UsuarioDAO`, ver la nota en cada
clase). No se agregó validación de cruce de horarios: esa regla no existe
en el código fuente del proyecto principal (ni en `CitaDAO` ni en
`AgendamientoServlet`), así que esta API tampoco la inventa, para que el
comportamiento sea el mismo por los dos canales.

#### GET /api/barberos
Lista de barberos. `200 OK`.

#### GET /api/servicios
Lista de servicios. `200 OK`.

#### GET /api/servicios/{id}
Un servicio por id. `200 OK` / `404 Not Found`.

#### POST /api/citas
Parámetros (`x-www-form-urlencoded`): `cliente`, `barbero_id`,
`servicio_id`, `fecha` (AAAA-MM-DD), `hora` (HH:mm).
Respuestas: `201 Created` con la cita creada / `400 Bad Request` si falta
algún dato o el `servicio_id` no existe.

#### GET /api/citas
Lista de todas las citas. `200 OK`.

#### GET /api/citas/{id}
Una cita por id. `200 OK` / `404 Not Found`.

#### PUT /api/citas/{id}
Mismos parámetros que POST. `200 OK` con la cita actualizada / `400` /
`404` si el id no existe.

#### DELETE /api/citas/{id}
`200 OK` / `404 Not Found`.

## Ejecución local

1. JDK 17+ y Maven 3.9+ instalados.
2. La base de datos `barberia_harry_estilos` debe existir y estar
   accesible (la misma que usa el proyecto principal; ver su
   `enlace_repositorio.txt` para el script `init.sql`).
3. `mvn spring-boot:run` (o `mvn clean package` y luego
   `java -jar target/servicio-auth-api-1.0-SNAPSHOT.jar`).
4. El servicio queda disponible en `http://localhost:8081`.
5. Probar con Postman (ver evidencia GA7-220501096-AA5-EV02) contra
   `http://localhost:8081/api/usuario/login` y
   `http://localhost:8081/api/usuario/registro`, usando los mismos
   usuarios de prueba del proyecto principal
   (`admin@harryestilos.com` / `admin123`, etc.).

## Pruebas unitarias

`src/test/java/.../PasswordUtilTest.java` verifica la lógica de hash y
verificación de contraseñas (4 casos). Ejecución: `mvn test`.
