#!/bin/bash

OUTPUT="$(sudo sysctl -w vm.max_map_count=262144)"
OUTPUT2="$(sysctl vm.max_map_count)"
echo "New ${OUTPUT2}"

echo "Starting up using docker-compose"

docker network rm elastest
docker network create -d bridge elastest

sudo chmod a+rwx -R mysql/data

# docker-compose --project-name edm   up -d  --remove-orphans --force-recreate
set -e
docker-compose --project-name edm   up -d 

# docker-compose scale esnode=3
#docker volume create --driver local --opt o=size=20G,uid=1000 esdata1
#docker volume create --driver local --opt o=size=20G,uid=1000 esdata2
