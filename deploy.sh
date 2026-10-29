#!/bin/bash
set -e

echo "=== 1. Descargando últimos cambios del Monorepo desde GitHub ==="
git pull origin main

echo "=== 2. Deteniendo contenedores previos ==="
docker rm -f rickandmorty-bff taller-backend 2>/dev/null || true

echo "=== 3. Construyendo y levantando rickandmorty-bff (Puerto 8080) ==="
docker build -t rickandmorty-bff ./apps/rickandmorty-bff
docker run -d -p 8080:8080 --name rickandmorty-bff --restart unless-stopped rickandmorty-bff

echo "=== 4. Construyendo y levantando taller-backend (Puerto 8081) ==="
docker build -t taller-backend ./apps/taller-backend
docker run -d -p 8081:8080 --name taller-backend --restart unless-stopped taller-backend

echo "=== 5. Ambos proyectos del Monorepo están corriendo ==="
docker ps
