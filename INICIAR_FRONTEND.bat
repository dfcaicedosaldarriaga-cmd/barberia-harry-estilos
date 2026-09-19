@echo off
title Barberia Harry Estilos - Modulo Frontend (EV03)
color 0E
echo ========================================================
echo   BARBERIA HARRY ESTILOS - MODULO FRONT-END REACT
echo   Evidencia SENA: GA7-220501096-AA4-EV03
echo   Aprendiz: Diego Fernando Caicedo Saldarriaga
echo ========================================================
echo.

:: 1. Verificar si Node.js esta instalado
where node >nul 2>nul
if %errorlevel% neq 0 (
    echo [ALERTA] Node.js no esta en el PATH del sistema.
    if exist "C:\Program Files\nodejs\node.exe" (
        echo [INFO] Detectado Node.js en C:\Program Files\nodejs. Agregando al PATH...
        set "PATH=C:\Program Files\nodejs;%PATH%"
    ) else (
        echo [ERROR] No se encontro Node.js en este equipo.
        echo Por favor descarga e instala Node.js (LTS) desde: https://nodejs.org/
        echo.
        pause
        exit /b 1
    )
)

:: 2. Instalar dependencias si falta la carpeta node_modules
if not exist node_modules (
    echo [1/2] Instalando paquetes y dependencias del proyecto (npm install)...
    call npm install
    echo.
)

:: 3. Abrir el navegador en localhost:5173
echo [2/2] Abriendo la aplicacion en tu navegador...
start "" "http://localhost:5173/"

:: 4. Iniciar el servidor local de desarrollo
echo.
echo Servidor en ejecucion. Presiona Ctrl + C para detener.
echo ========================================================
call npm run dev
pause