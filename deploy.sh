#!/bin/bash
set -e

echo "=== Stopping default Apache httpd on port 80 if active ==="
sudo systemctl stop httpd 2>/dev/null || true
sudo systemctl disable httpd 2>/dev/null || true

echo "=== Building and starting containers on Amazon Linux / Ubuntu ==="
sudo docker rm -f rickandmorty-bff taller-backend 2>/dev/null || true
sudo DOCKER_BUILDKIT=0 docker build -t rickandmorty-bff ./apps/rickandmorty-bff
sudo DOCKER_BUILDKIT=0 docker build -t taller-backend ./apps/taller-backend
sudo docker run -d --name rickandmorty-bff --restart unless-stopped -p 80:8080 -p 8080:8080 rickandmorty-bff
sudo docker run -d --name taller-backend --restart unless-stopped -p 8081:8080 taller-backend

echo "=== Multi-Instance Node Deployment Complete ==="
sudo docker ps
