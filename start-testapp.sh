#!/bin/bash

# Configuración de variables
IMAGE_NAME="openpunch-app"
CONTAINER_NAME="openpunch-container"
NETWORK_NAME="openpunch-test-network"

echo "🚀 Starting OpenPunch deployment process..."

# 1. Crear la red si no existe
if [ ! "$(docker network ls | grep $NETWORK_NAME)" ]; then
  echo "🌐 Creating docker network: $NETWORK_NAME"
  docker network create $NETWORK_NAME
fi

# 2. Detener y eliminar contenedores antiguos para evitar conflictos
echo "🧹 Cleaning up old containers..."
docker stop $CONTAINER_NAME 2>/dev/null
docker rm $CONTAINER_NAME 2>/dev/null

# 3. Construir la imagen (Docker Build)
echo "🏗️ Building Docker image: $IMAGE_NAME..."
docker build -t $IMAGE_NAME .

# 4. Ejecutar el contenedor (Docker Run) con las variables de entorno solicitadas
echo "🏃 Running container: $CONTAINER_NAME..."
docker run -d \
  --name $CONTAINER_NAME \
  --network $NETWORK_NAME \
  -p 8080:8080 \
  -e DB_HOST=db \
  -e TZ=Europe/Madrid \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://openpunch-db-test/openpunch?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Europe/Madrid" \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=1234 \
  $IMAGE_NAME

echo "✅ OpenPunch is now running on http://localhost:8080"