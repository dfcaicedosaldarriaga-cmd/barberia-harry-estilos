@echo off
title Barberia Harry Estilos - Servidores Java Web
echo ========================================================
echo   Iniciando Servidores para Barberia Harry Estilos
echo ========================================================
echo.

set JAVA_HOME=C:\Program Files\Java\jdk-26.0.1
set CATALINA_HOME=C:\Users\CAJA\Downloads\apache-tomcat-10.1.57

echo 1. Iniciando servidor MySQL (puerto 3306)...
start "MySQL Server" /min "C:\Program Files\MySQL\MySQL Server 8.4\bin\mysqld.exe" --datadir="C:\Users\CAJA\mysql_data" --port=3306

timeout /t 3 /nobreak > nul

echo 2. Iniciando Apache Tomcat (puerto 8080)...
cd /d "C:\Users\CAJA\Downloads\apache-tomcat-10.1.57\bin"
call startup.bat

echo.
echo ========================================================
echo   TODOS LOS SERVIDORES HAN SIDO INICIADOS CORRECTAMENTE
echo ========================================================
echo.
echo Ya puedes ingresar desde tu navegador a:
echo.
echo    http://localhost:8080/barberia-harry-estilos/inicio
echo    http://localhost:8080/barberia-harry-estilos/index.jsp
echo.
echo (Manten esta ventana abierta mientras uses la aplicacion)
echo ========================================================
echo.
pause
