#!/bin/bash

BASE_DIR="../services"
PID_DIR="./pids"
LOG_DIR="./logs"

mkdir -p "$PID_DIR"
mkdir -p "$LOG_DIR"

source ./env.local.sh

# =========================
# START SERVICE
# =========================
start_service() {
  NAME=$1
  PATH_DIR=$2

  echo "🚀 Starting $NAME..."

  cd "$BASE_DIR/$PATH_DIR" || exit

  nohup ./mvnw spring-boot:run > "../../scripts/logs/$NAME.log" 2>&1 &

  echo $! > "../../scripts/pids/$NAME.pid"

  cd - > /dev/null

  echo "✅ $NAME started (PID: $(cat $PID_DIR/$NAME.pid))"
}

# =========================
# STOP SERVICE
# =========================
stop_service() {
  NAME=$1

  PID_FILE="$PID_DIR/$NAME.pid"

  if [ -f "$PID_FILE" ]; then
    PID=$(cat "$PID_FILE")

    echo "🛑 Stopping $NAME (PID: $PID)..."
    kill $PID 2>/dev/null

    rm -f "$PID_FILE"
    echo "✅ $NAME stopped"
  else
    echo "⚠️ $NAME is not running"
  fi
}

# =========================
# STATUS
# =========================
status_service() {
  NAME=$1
  PID_FILE="$PID_DIR/$NAME.pid"

  if [ -f "$PID_FILE" ]; then
    PID=$(cat "$PID_FILE")
    if ps -p $PID > /dev/null; then
      echo "🟢 $NAME is RUNNING (PID: $PID)"
    else
      echo "🔴 $NAME not running (stale PID)"
    fi
  else
    echo "⚪ $NAME is STOPPED"
  fi
}

# =========================
# LOG VIEW
# =========================
view_logs() {
  NAME=$1
  LOG_FILE="$LOG_DIR/$NAME.log"

  if [ -f "$LOG_FILE" ]; then
    echo "📄 Showing logs for $NAME (Ctrl+C to exit)"
    tail -f "$LOG_FILE"
  else
    echo "⚠️ No logs found for $NAME"
  fi
}

# =========================
# START KAFKA (DOCKER ONLY)
# =========================
start_kafka() {
  echo "🚀 Starting Kafka (Docker)..."
  cd ../infrastructure/kafka || exit
  docker-compose up -d
  cd - > /dev/null
  echo "✅ Kafka started"
}

# =========================
# ALL SERVICES LIST
# =========================
declare -A SERVICES=(
  [1]="eureka:eureka"
  [2]="api-gateway:apigateway"
  [3]="user-service:user-service"
  [4]="product-service:product-service"
  [5]="order-service:order-service"
  [6]="payment-service:payment-service"
  [7]="inventory-service:inventory-service"
  [8]="delivery-service:delivery-service"
  [9]="notification-service:notification-service"
  [10]="tracking-service:tracking-service"
  [11]="auth-service:auth-service"
)

# =========================
# MENU
# =========================
show_menu() {
  echo ""
  echo "========================================"
  echo "   🚀 MICROSERVICE CONTROL CENTER"
  echo "========================================"
  echo "0) 🔥 START FULL STACK (Kafka + All)"
  echo "1) ▶ Start Service"
  echo "2) 🛑 Stop Service"
  echo "3) 🔁 Restart Service"
  echo "4) 📊 Status Dashboard"
  echo "5) 📄 View Logs"
  echo "6) ❌ Exit"
  echo "========================================"
}

# =========================
# START ALL
# =========================
start_all() {
  start_kafka

  for i in "${!SERVICES[@]}"; do
    IFS=":" read NAME PATH <<< "${SERVICES[$i]}"
    start_service "$NAME" "$PATH"
    sleep 2
  done

  echo "🎉 FULL STACK RUNNING"
}

# =========================
# STATUS ALL
# =========================
status_all() {
  echo ""
  echo "📊 SERVICE STATUS"
  echo "----------------------"

  for i in "${!SERVICES[@]}"; do
    IFS=":" read NAME PATH <<< "${SERVICES[$i]}"
    status_service "$NAME"
  done
}

# =========================
# MAIN LOOP
# =========================
while true; do
  show_menu
  read -p "👉 Choose option: " choice

  case $choice in
    0)
      start_all
      ;;
    1)
      read -p "Enter service number (1-11): " s
      IFS=":" read NAME PATH <<< "${SERVICES[$s]}"
      start_service "$NAME" "$PATH"
      ;;
    2)
      read -p "Enter service number (1-11): " s
      IFS=":" read NAME PATH <<< "${SERVICES[$s]}"
      stop_service "$NAME"
      ;;
    3)
      read -p "Enter service number (1-11): " s
      IFS=":" read NAME PATH <<< "${SERVICES[$s]}"
      stop_service "$NAME"
      sleep 2
      start_service "$NAME" "$PATH"
      ;;
    4)
      status_all
      ;;
    5)
      read -p "Enter service number (1-11): " s
      IFS=":" read NAME PATH <<< "${SERVICES[$s]}"
      view_logs "$NAME"
      ;;
    6)
      echo "👋 Bye"
      exit 0
      ;;
    *)
      echo "❌ Invalid option"
      ;;
  esac
done