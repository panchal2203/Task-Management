#!/usr/bin/env bash

# Stop and delete the containers
docker-compose down

# Deleting network if available
docker network rm task-management-application || true
