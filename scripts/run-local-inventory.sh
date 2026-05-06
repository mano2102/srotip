#!/bin/bash

source ./env.sh

echo "Starting Inventory Service..."

cd /Volumes/MANO\ SSD/srotip/services/inventory-service

./mvnw spring-boot:run