#!/bin/bash

echo "Tearing down using docker-compose"
docker-compose  --project-name edm down
#docker-compose kill
#docker-compose rm -f

rm -rf underStorage
mkdir underStorage

rm -rf elasticsearch/esdata1/nodes
rm -rf elasticsearch/esdata2/nodes

docker volume prune -f
