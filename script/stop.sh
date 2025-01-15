#!/bin/bash

compose_dir="../docker-compose"

# docker-compose
docker-compose \
-f "${compose_dir}/docker-compose.sample.yml" \
down
