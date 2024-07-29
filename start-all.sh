#!/usr/bin/env bash

# Stop and delete existing containers if any
docker-compose down

# Stop and delete existing containers if any
docker-compose stop

# Deleting network if available
docker network rm task-management-application || true

# Creating network for services
docker network create task-management-application

# Increasing default HTTP Timeout from 60 to 300
export COMPOSE_HTTP_TIMEOUT=300

# Load environment variables
#export $(cat .env | xargs)
set -a
source .env
set +a


# Start all services in background with -d flag
docker-compose up --build -d
