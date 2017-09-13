#!/bin/bash

sudo true

echo "Tearing down using docker-compose"
docker-compose  --project-name edm down

sudo rm -rf alluxio/data
mkdir alluxio/data
touch alluxio/data/.gitkeep

sudo rm -rf mysql/data
mkdir mysql/data
touch mysql/data/.gitkeep

sudo rm -rf elasticsearch/esdata1/nodes
sudo rm -rf elasticsearch/esdata2/nodes
sudo rm -rf rest-java/rest_api_project/deployment/edm

docker volume prune -f

sudo find . -name "__pycache__"  -type d -exec rm -rf {} +
