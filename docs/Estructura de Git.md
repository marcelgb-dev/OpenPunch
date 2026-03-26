# **Estructura de Directorios del Proyecto (OpenPunch)**

Esta organización separa la lógica de negocio (Java) de la infraestructura (Docker/Nginx) y facilita el despliegue al administrador.  
openpunch/  
├── docker/                 \# Configuraciones específicas de contenedores  
│   ├── mysql/  
│   │   └── init.sql        \# Script de creación de tablas y usuario admin inicial  
│   └── nginx/  
│       ├── conf.d/  
│       │   └── default.conf \# Configuración del Proxy Inverso  
│       └── certs/           \# Certificados SSL (no subir a Git)  
├── src/                    \# Código fuente de Spring Boot  
│   ├── main/  
│   │   ├── java/  
│   │   │   └── com/openpunch/  
│   │   │       ├── config/      \# Seguridad y Beans  
│   │   │       ├── controller/  \# Endpoints (Web)  
│   │   │       ├── model/       \# Entidades/POJOs  
│   │   │       └── repository/  \# Lógica JDBC (SQL)  
│   │   └── resources/  
│   │       ├── static/          \# CSS, imágenes, JS del escáner  
│   │       ├── templates/       \# Plantillas Thymeleaf (HTML)  
│   │       └── application.properties \# Configuración de la App  
│   └── test/               \# Pruebas unitarias  
├── .env.example            \# Plantilla de configuración para el administrador  
├── .gitignore              \# Archivos que Git debe ignorar  
├── Dockerfile              \# Instrucciones de imagen para Java  
├── docker-compose.yml      \# Orquestador de servicios  
├── pom.xml                 \# Dependencias de Maven  
├── setup.sh                \# Script de configuración inicial  
└── README.md               \# Documentación del proyecto

### **Detalle de archivos de configuración**

#### **1\. .env.example**

Sirve de guía. El administrador debe copiarlo a .env.  
\# Configuración de Base de Datos  
MYSQL\_ROOT\_PASSWORD=cambiame\_por\_favor  
DB\_NAME=openpunch

\# Configuración de la Aplicación  
APP\_PORT=80  
ADMIN\_PASSWORD=admin\_seguro\_123

#### **2\. setup.sh**

Automatiza la preparación del entorno.  
\#\!/bin/bash

\# 1\. Crear el archivo .env si no existe  
if \[ \! \-f .env \]; then  
    cp .env.example .env  
    echo "✔ Archivo .env creado. Por favor, edítalo con tus contraseñas."  
else  
    echo "ℹ El archivo .env ya existe."  
fi

\# 2\. Comprobar Docker  
if \! \[ \-x "$(command \-v docker)" \]; then  
  echo "✘ Error: docker no está instalado." \>&2  
  exit 1  
fi

echo "🚀 Todo listo. Edita el .env y ejecuta: docker-compose up \-d"

#### **3\. init.sql**

Este archivo se monta en /docker-entrypoint-initdb.d/ dentro del contenedor MySQL y se ejecuta **solo la primera vez** que se crea el volumen.  
CREATE DATABASE IF NOT EXISTS openpunch;  
USE openpunch;

\-- Aquí irían los CREATE TABLE que definimos anteriormente  
\-- Y un INSERT inicial del administrador usando la lógica de BCrypt  
