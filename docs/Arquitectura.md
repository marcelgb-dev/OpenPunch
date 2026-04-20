# 🏗️ Arquitectura del Sistema: OpenPunch

Este documento detalla el diseño técnico, el stack tecnológico y las decisiones de arquitectura para **OpenPunch**, el sistema de control de asistencia mediante códigos QR.

---

## 1. Resumen del Proyecto
OpenPunch es una solución profesional para el control de fichaje de empleados. El sistema permite registrar entradas y salidas mediante el escaneo de un código QR personal frente a una estación de lectura gestionada por un usuario con rol específico.

### Funcionalidades Clave
* **Control de Accesos por Rol:** Permisos diferenciados para `ADMIN`, `SCANNER` y `USER`.
* **Estación de Fichaje (Scanner):** Interfaz web que utiliza la cámara para capturar los tokens QR de los empleados.
* **Lógica de Toggle Automático:** El sistema determina si el escaneo es una "Entrada" o una "Salida" consultando en tiempo real el último registro del usuario.
* **Interfaz en Inglés:** Toda la aplicación y documentación técnica se desarrollan en inglés por requisitos académicos.

---

## 2. Stack Tecnológico

### Backend y Lógica
* **Lenguaje:** Java 21 (LTS) con el framework **Spring Boot**.
* **Acceso a Datos:** **JDBC Template** (SQL Puro). Se utilizan **Vistas SQL** para simplificar la obtención de estados complejos.
* **Seguridad:** Spring Security basado en sesiones y cifrado **BCrypt** para credenciales.
* **Arquitectura por capas:** Separación de clases e interfaces en:
  - **`config`**: Configuraciones de Java / Spring.
  - **`controller`**: Recibe peticiones del usuario, pide datos procesados a **service** y se los envía a Thymeleaf.
  - **`model`**: Clases de datos de Java para modelar las entidades SQL.
  - **`repository`**: Comunicación entre la base de datos y el **service**.
  - **`service`**: Recibe peticiones de los **controllers**, pide los datos necesarios a **repository** y envía una respuesta de vuelta.

### Frontend
* **Motor de Plantillas:** **Thymeleaf** para el renderizado desde el servidor.
* **Estilos:** CSS3 estándar.
* **Procesamiento de QR:** Librería JavaScript integrada en el cliente para la lectura de cámara vía Fetch API.

### Infraestructura y Despliegue
* **Base de Datos:** MySQL 8.0 (Imagen oficial `mysql:8.0-oracle`).
* **Proxy Inverso:** **Nginx** para la gestión de certificados SSL (HTTPS).
* **Contenedores:** Docker y Docker Compose (Servicios: `app`, `db`, `proxy`). La aplicación se compila dentro del contenedor por el momento.
* **Nube:** Despliegue en Amazon AWS EC2.

---

## 3. Modelo de Datos
La estructura detallada se encuentra en el script de inicialización SQL. El modelo se basa en las siguientes entidades relacionadas:

1.  **`groups`**: Gestión de departamentos o sedes.
2.  **`users`**: Datos de empleados y su `qr_token` (UUID v4).
3.  **`punch_logs`**: Registro histórico de eventos (`log_time`, `event`).
4.  **`work_sessions`**: Registro de jornadas completas calculadas.

* **`view_user_status`**: Vista SQL que identifica el estado actual de cada usuario para la lógica de toggle.

---

## 4. Seguridad y Red (Diseño DMZ)
* **Aislamiento:** Los contenedores de la App y la DB operan en una red interna privada de Docker.
* **HTTPS:** Obligatorio para habilitar el uso de la cámara en el navegador del Scanner. Se estudiará el uso de CertBot + DuckDNS.

---

## 5. Automatización
* **Variables de Entorno:** Uso de archivos `.env` para datos sensibles.
* **Script de Setup:** Automatización de la creación del entorno y verificación de dependencias.
* **Contenedor de testeo de la DB:** Script start-testdb.sh arranca un contenedor Docker mysql para hacer pruebas sin docker-compose.
* **Inicialización:** Carga automática de esquema y datos maestros desde la carpeta de inicialización de MySQL (docker/mysql/init)