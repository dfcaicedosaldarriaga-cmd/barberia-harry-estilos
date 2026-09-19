# Barber System - GA7-220501096-AA4-EV03

Módulo front-end de **agendamiento de citas** para una barbería. Se desarrolla como evidencia de la actividad **GA7-220501096-AA4-EV03: Componente front-end del proyecto formativo y proyectos de clase**.

**Aprendiz:** Diego Fernando Caicedo Saldarriaga

## Funcionalidades

- Selección visual y reutilizable de servicios.
- Registro de citas con cliente, barbero, fecha y hora.
- Validaciones de campos obligatorios y nombre del cliente.
- Resumen dinámico de la reserva con precio y duración.
- Consulta y cancelación de citas en memoria.
- Diseño adaptable para escritorio y móvil.

## Tecnologías

- React 19
- JavaScript (ES modules)
- CSS responsivo
- Vite
- Git para control de versiones

## Estructura relevante

```text
src/
  components/       Componentes reutilizables de interfaz
  data/             Datos temporales de barberos, servicios y citas
  services/         Capa preparada para integración con API
  utils/            Validaciones y formato de datos
  App.jsx           Orquestación del módulo de agendamiento
```

## Ejecución

1. Instale Node.js 20 o superior.
2. En la raíz del proyecto ejecute `npm install`.
3. Ejecute `npm run dev`.
4. Abra la dirección local que muestre Vite, normalmente `http://localhost:5173`.

Para crear el paquete de producción ejecute `npm run build`.

## Integración futura

Actualmente `src/services/appointmentService.js` simula las operaciones de guardar y cancelar. Para conectarlo al backend de Java/MySQL del proyecto anterior, reemplace esas funciones por solicitudes HTTP a endpoints que gestionen citas, servicios y barberos. La interfaz no debe consultar directamente la base de datos.

## Evidencia de versionamiento

El proyecto fue inicializado como repositorio Git local y se configuró para el repositorio remoto `Programa-Git` de la cuenta del aprendiz. No se incluye `node_modules` ni la carpeta de compilación en el comprimido.
