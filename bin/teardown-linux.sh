#!/bin/bash

echo "Tearing down using docker-compose"
docker-compose  --project-name edm down

rm -rf alluxio/data
mkdir alluxio/data

sudo rm -rf mysql/data/*

rm -rf elasticsearch/esdata1/nodes
rm -rf elasticsearch/esdata2/nodes

docker volume prune -f

