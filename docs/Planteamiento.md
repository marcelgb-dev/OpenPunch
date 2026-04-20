# Planteamiento del Proyecto: OpenPunch

## 1. Introducción
OpenPunch es un sistema de control de asistencia diseñado para organizaciones que buscan una solución de fichaje ágil y moderna. Este proyecto académico para 1º de DAM integra desarrollo backend con Spring Boot, gestión de bases de datos MySQL y despliegue en la nube mediante Docker y AWS.

## 2. Descripción del Sistema
El sistema se basa en la interacción de tres roles principales para garantizar un control total sobre el flujo de asistencia:

* **Administrator (Gestor):** Tiene acceso total al panel de control. Puede gestionar usuarios, visualizar estadísticas generales del grupo, revisar el historial de sesiones de trabajo y realizar ajustes manuales si fuera necesario.
* **Scanner (Estación de Fichaje):** Este rol gestiona el dispositivo de lectura. Utiliza la cámara para procesar los códigos QR presentados por los empleados, actuando como el punto de entrada y salida de datos del sistema.
* **User (Empleado):** Cada empleado dispone de un código QR único (UUID v4) vinculado a su identidad. El usuario presenta este código al **Scanner**, activando la lógica de toggle (Punch IN / Punch OUT)

## 3. Lógica de "Toggle" Automático
Para simplificar la interacción, OpenPunch elimina la necesidad de seleccionar manualmente si se está entrando o saliendo:
1.  El **Scanner** captura el QR del usuario.
2.  El **Backend** identifica al usuario y consulta su estado actual en la base de datos mediante una vista optimizada.
3.  **Acción Automática:** * Si el usuario no tiene registros previos o su último log fue una salida (**OUT**), el sistema registra una entrada (**IN**).
    * Si el usuario ya estaba dentro (**IN**), el sistema registra una salida (**OUT**) y cierra su sesión de trabajo calculando la duración total de la jornada.

## 4. Resumen del Stack Tecnológico
Se ha optado por un stack robusto y equilibrado:
* **Backend:** Java 21 (LTS) con Spring Boot y JDBC Template para un control total del SQL.
* **Frontend:** HTML, CSS y JavaScript (vanilla) con Thymeleaf como motor de plantillas.
* **Base de Datos:** MySQL 8.x con una estructura de tablas relacionadas y lógica optimizada mediante Vistas SQL.
* **Infraestructura:** Despliegue en Amazon AWS EC2 utilizando Docker y Docker Compose para la orquestación de servicios.