#!/bin/bash

source ./env.local.sh

echo "Starting Product Service..."

cd /Volumes/MANO\ SSD/srotip/services/product-service

./mvnw spring-boot:run