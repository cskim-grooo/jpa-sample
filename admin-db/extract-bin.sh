#!/bin/bash

DST_DIR="./docker-data"

if [ "$1" != "" ]; then
    DST_DIR="$1"
fi

docker-compose up extract-binlog
docker-compose down

echo "mv ./docker-data/log/binary/bin.sql '$DST_DIR'"
mv ./docker-data/log/binary/bin.sql "$DST_DIR"
