#!/bin/bash

echo "Clearing all backups !!!"

sudo rm -rf backup/elasticsearch/*
touch backup/elasticsearch/.gitkeep

sudo rm -rf backup/mysql/*
touch backup/mysql/.gitkeep

sudo rm -rf backup/hdfs/*
touch backup/hdfs/.gitkeep

sudo rm rest/rest_api_project/rest_api_app/db.sqlite
sudo rm rest/rest_api_project/tests/db.sqlite

sudo rm rest/rest_api_project/.coverage
sudo rm rest/rest_api_project/coverage.xml

sudo rm -rf rest/rest_api_project/.tox
sudo rm -rf rest/rest_api_project/edm.egg-info
sudo rm rest/rest_api_project/nosetests.xml

rm logs.jsonl

