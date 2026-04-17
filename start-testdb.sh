#!/bin/bash

# Configuración de variables
CONTAINER_NAME="openpunch-db-test"
DB_PASSWORD="1234"
VOLUME_NAME="openpunch_mysql_data"
DB_NAME="openpunch"

echo "🚀 Iniciando entorno de base de datos para OpenPunch..."

# 1. Comprobar si el contenedor ya existe y eliminarlo
if [ "$(docker ps -aq -f name=${CONTAINER_NAME})" ]; then
    echo "⚠️ Deteniendo y eliminando contenedor previo..."
    docker rm -f ${CONTAINER_NAME} > /dev/null
fi

docker run -d \
  --rm \
  --name ${CONTAINER_NAME} \
  -p 3306:3306 \
  -e TZ=Europe/Madrid \
  -e MYSQL_ROOT_PASSWORD=${DB_PASSWORD} \
  -v ${VOLUME_NAME}:/var/lib/mysql \
  mysql:8.0-oracle

docker ps -f name=${CONTAINER_NAME}