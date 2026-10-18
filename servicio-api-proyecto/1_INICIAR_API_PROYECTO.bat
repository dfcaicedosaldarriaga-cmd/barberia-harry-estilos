@echo off
title SERVIDOR API REST - PROYECTO FORMATIVO BARBERIA HARRY ESTILOS (PUERTO 8081)
color 0A
echo ===============================================================================
echo   SERVICIO WEB RESTful - PROYECTO FORMATIVO BARBERIA HARRY ESTILOS
echo   EVIDENCIA SENA: GA7-220501096-AA5-EV03
echo   APRENDIZ: DIEGO FERNANDO CAICEDO SALDARRIAGA
echo ===============================================================================
echo.
echo [1/2] Iniciando Servidor Spring Boot en el puerto 8081...
echo.
echo  - Swagger UI: http://localhost:8081/swagger-ui/index.html
echo  - OpenAPI Spec: http://localhost:8081/v3/api-docs
echo  - Endpoint Base: http://localhost:8081/api
echo.
echo [IMPORTANTE] Manten esta consola abierta mientras realizas pruebas con Swagger o Postman.
echo.

set JAR_PATH="%~dp0PROYECTO_API_REST\target\servicio-api-barberia-1.0.0.jar"

if exist %JAR_PATH% (
    java -jar %JAR_PATH%
) else (
    echo Compilando y ejecutando con Maven...
    cd /d "%~dp0PROYECTO_API_REST"
    call mvn spring-boot:run
)

pause
