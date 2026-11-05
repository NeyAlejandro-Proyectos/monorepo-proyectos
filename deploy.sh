#!/bin/bash
set -e

echo "=== Stopping default Apache httpd on port 80 if active ==="
sudo systemctl stop httpd 2>/dev/null || true
sudo systemctl disable httpd 2>/dev/null || true

echo "=== Updating Monorepo from GitHub ==="
git pull origin main

echo "=== Rebuilding and restarting Docker containers on EC2 Node ==="
if command -v docker-compose >/dev/null 2>&1; then
  sudo docker-compose up -d --build
else
  sudo docker compose up -d --build
fi

echo "=== Multi-Instance Node Deployment Complete ==="
sudo docker ps
