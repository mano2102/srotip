#!/bin/bash

source ./env.local.sh

echo "Starting User Service..."

cd /Volumes/MANO\ SSD/srotip/services/user-service

./mvnw spring-boot:run