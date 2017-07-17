#!/bin/bash

echo "Running all tests !!!"

#docker-compose -p edm exec rest-api nose2 --with-coverage --verbose
# docker-compose -p edm exec rest-api nose2 --verbose
docker-compose -p edm exec rest-api nose2 --with-coverage --coverage-report term-missing