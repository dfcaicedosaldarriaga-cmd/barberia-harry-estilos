# Servicio web REST de autenticación — GA7-220501096-AA5-EV01

Aprendiz: Hernán Salazar Ospina
Proyecto formativo: Barbería Harry Estilos — módulo de sesión (continuación de GA7-220501096-AA3-EV01/EV02)

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
