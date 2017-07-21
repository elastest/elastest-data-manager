#!/bin/bash

echo "Tearing down using docker-compose"
docker-compose  --project-name edm down

rm -rf alluxio/data
mkdir alluxio/data

rm -rf mysql/data/*

rm -rf elasticsearch/esdata1/nodes
rm -rf elasticsearch/esdata2/nodes

docker volume prune -f

find . -name "__pycache__"  -type d -exec rm -rf {} +
