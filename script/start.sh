#!/bin/bash

compose_dir="../docker-compose"

# docker-compose
echo "start docker-compose.admin.local $2"
  docker-compose \
  -f "${compose_dir}/docker-compose.sample.yml" \
  up --build -d
