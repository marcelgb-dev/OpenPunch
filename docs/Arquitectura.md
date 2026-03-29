# 🏗️ Arquitectura del Sistema: OpenPunch

Este documento detalla el diseño técnico, el stack tecnológico y las decisiones de arquitectura para **OpenPunch**, el sistema de control de asistencia mediante códigos QR.

---

## 1. Resumen del Proyecto
OpenPunch es una solución profesional para el control de fichaje de empleados. El sistema permite registrar entradas y salidas mediante el escaneo de un código QR personal frente a una estación fija de lectura (modo Tótem).

### Funcionalidades Clave
* **Control de Accesos por Rol:** Permisos diferenciados para `ADMIN`, `SCANNER` y `EMPLOYEE`.
* **Sistema de Tótem (Scanner):** Un rol específico de "Scanner" gestiona una estación web que utiliza la cámara para capturar los tokens QR de los empleados.
* **Lógica de Toggle Automático:** El sistema detecta si el escaneo corresponde a una "Entrada" o una "Salida" consultando el último registro del usuario en la base de datos.
* **Interfaz en Inglés:** Siguiendo los requisitos académicos, toda la aplicación y documentación técnica final será en inglés.

---

## 2. Stack Tecnológico

### Backend y Lógica
* **Lenguaje:** Java 21 con el framework **Spring Boot**.
* **Acceso a Datos:** **JDBC Template** (SQL Puro) para mantener un control total sobre las consultas, sin usar JPA/Hibernate.
* **Seguridad:** Spring Security basado en sesiones tradicionales (JSESSIONID).
* **Cifrado de Contraseñas:** Uso obligatorio de **BCrypt** para todas las credenciales.

### Frontend
* **Motor de Plantillas:** **Thymeleaf** para el renderizado desde el servidor.
* **Estilos:** CSS estándar.
* **Procesamiento de QR:** Librería JavaScript (como `html5-qrcode`) integrada en el frontend para la lectura de cámara, comunicándose con el backend vía Fetch API.

### Infraestructura y Despliegue
* **Base de Datos:** MySQL 8.x.
* **Proxy Inverso:** **Nginx** encargado de gestionar el certificado SSL (HTTPS) y redirigir el tráfico a la aplicación.
* **Contenedores:** Docker y Docker Compose (Servicios: `app`, `db`, `proxy`).
* **Nube:** Despliegue en Amazon AWS EC2.

---

## 3. Modelo de Datos (Relación de 3 Tablas)
Para cumplir con el requerimiento de tener al menos 3 tablas relacionadas:

1.  **`roles`**: Define los niveles de permiso (`id`, `role_name`).
2.  **`users`**: Contiene los datos del empleado, su contraseña en BCrypt y su `qr_token` único (UUID v4). Está vinculada a la tabla `roles`.
3.  **`attendance_logs`**: Registra cada evento de fichaje vinculado a un `user_id`, incluyendo marca de tiempo y tipo de evento.

---

## 4. Seguridad y Red (Diseño DMZ)
El despliegue sigue un esquema profesional de seguridad perimetral:

* **Zona Pública (DMZ):** Solo el contenedor de **Nginx** está expuesto al exterior por los puertos 80 y 443.
* **Red Privada:** Los contenedores de Spring Boot y MySQL están en una red interna de Docker, aislados del acceso directo desde internet.
* **Uso de HTTPS:** El certificado SSL es obligatorio para que el navegador permita el acceso a la cámara del dispositivo de escaneo.

---

## 5. Automatización y Configuración
* **Variables de Entorno:** Gestión de datos sensibles mediante archivos `.env` (fuera de Git).
* **Script de Setup:** Un archivo `setup.sh` automatiza la creación del entorno y la verificación de dependencias.
* **Inicialización:** El esquema de la BD y el administrador inicial se cargan automáticamente vía `docker/mysql/init.sql`.
