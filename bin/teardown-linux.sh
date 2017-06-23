#!/bin/bash

echo "Tearing down using docker-compose"
docker-compose down
#docker-compose kill
#docker-compose rm -f

rm -rf underStorage
mkdir underStorage

docker volume prune -f
