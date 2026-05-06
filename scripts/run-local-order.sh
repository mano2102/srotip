#!/bin/bash

source ./env.local.sh

echo "Starting Order Service..."

cd /Volumes/MANO\ SSD/srotip/services/order-service

./mvnw spring-boot:run