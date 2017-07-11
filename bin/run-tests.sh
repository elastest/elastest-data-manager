#!/bin/bash

echo "Running all tests !!!"

docker-compose -p edm exec rest-api nose2 -v

