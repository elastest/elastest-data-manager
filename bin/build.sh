#!/bin/bash

docker build -t sgioldasis/elastest-hadoop:2.8.0 hadoop
docker build -t sgioldasis/elastest-alluxio:1.5.0 alluxio
docker build -t sgioldasis/elastest-elasticsearch:5.4.1 elasticsearch
docker build -t sgioldasis/elastest-kibana:5.4.1 kibana
docker build -t sgioldasis/elastest-cerebro:0.6.5 cerebro
docker build -t sgioldasis/elastest-mysql:5.7 mysql
