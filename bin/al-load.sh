#!/bin/bash

set -e

# Create file test.txt
echo "This is a test" > test.txt

echo "Loading test data to Alluxio (Local & HDFS)"

# Upload file (local)
curl -v -X POST http://localhost:39999/api/v1/paths//test.txt/create-file
curl -v -X POST http://localhost:39999/api/v1/streams/1/write --data-binary @test.txt -H "Content-Type: application/octet-stream"
curl -v -X POST http://localhost:39999/api/v1/streams/1/close

# Upload file (HDFS)
curl -v -X POST http://localhost:39999/api/v1/paths//hdfs/test.txt/create-file
curl -v -X POST http://localhost:39999/api/v1/streams/2/write --data-binary @test.txt -H "Content-Type: application/octet-stream"
curl -v -X POST http://localhost:39999/api/v1/streams/2/close

echo "Test data has loaded ..."
