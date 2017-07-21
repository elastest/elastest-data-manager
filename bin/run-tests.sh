#!/bin/bash

echo "Running tests !!!"

#docker-compose -p edm exec rest-api nose2 --with-coverage --verbose
# docker-compose -p edm exec rest-api nose2 --verbose
#docker-compose -p edm exec rest-api nose2 --with-coverage --coverage-report=html
#docker-compose -p edm exec rest-api nose2 --with-coverage --coverage-report term-missing

docker-compose -p edm exec rest-api tox
# docker-compose -p edm exec rest-api codecov -t a07586d2-4aed-4a0d-819c-35520f5eda54

#docker-compose -p edm exec codecov -t a07586d2-4aed-4a0d-819c-35520f5eda54