# Introducción

Este es un documento inicial para empezar a plantear el proyecto de OpenPunch. Es un proyecto académico para una asignatura de 1º de Desarrollo de Aplicaciones Multiplataforma.

Los requisitos académicos se adjuntan en el documento @Instrucciones.md.

El objetivo es crear una aplicación web con frontend, autenticación y base de datos usando todo el conocimiento adquirido durante el curso y las tecnologías que hemos visto en clase.

# Descripción del proyecto

OpenPunch es una aplicación sencilla que sirve cómo método de fichaje y seguimiento del tiempo de trabajo en una empresa / organización.

El fichaje se realizará a través de códigos QR, quedando registrados los turnos en una base de datos MySQL a la que se podrá acceder desde la web.

A dicha web podrán acceder tanto los usuarios para ver sus horas como el administrador para ver las horas de cada usuario y estadísticas generales del grupo.

# Stack tecnológico

- Frontend (web): HTML, CSS y si es necesario un poco de JavaScript vanilla.
- Lógica backend: Java con JDBC y SpringBoot.
- Códigos QR: API externa (pendiente de investigación)
- Base de Datos: MySQL
- Despliegue: Docker + Amazon AWS EC2
- Control de versiones: Git + GitHub