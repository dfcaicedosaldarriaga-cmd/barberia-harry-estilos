@echo off
title DOCUMENTACION INTERACTIVA SWAGGER UI - BARBERIA HARRY ESTILOS
color 0B
echo ===============================================================================
echo   ABRIENDO DOCUMENTACION INTERACTIVA OPENAPI 3.0 / SWAGGER UI
echo   URL: http://localhost:8081/swagger-ui/index.html
echo ===============================================================================
echo.
echo Abriendo en el navegador predeterminado...
start http://localhost:8081/swagger-ui/index.html
timeout /t 2 /nobreak > nul
