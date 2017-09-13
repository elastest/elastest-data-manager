#!/bin/bash

echo "Building and Running tests !!!"

pushd .
cd rest-java/rest_api_project/edm-rest

mvn clean package install
mvn cobertura:cobertura

popd
