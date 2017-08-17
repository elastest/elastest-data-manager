#!/bin/bash

set -e

file="logs.jsonl"
if [ ! -f "$file" ]
then
    echo "File '${file}' not found ..."
    echo "Downloading test log data ..."
    wget https://download.elastic.co/demos/kibana/gettingstarted/logs.jsonl.gz
    gunzip logs.jsonl.gz
else
    echo "File '${file}' found ..."
fi


echo "Creating Elasticsearch Mappings for log data ..."

curl -XPUT 'localhost:9201/logstash-2015.05.18?pretty' -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "log": {
      "properties": {
        "geo": {
          "properties": {
            "coordinates": {
              "type": "geo_point"
            }
          }
        }
      }
    }
  }
}
'

curl -XPUT 'localhost:9201/logstash-2015.05.19?pretty' -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "log": {
      "properties": {
        "geo": {
          "properties": {
            "coordinates": {
              "type": "geo_point"
            }
          }
        }
      }
    }
  }
}
'

curl -XPUT 'localhost:9201/logstash-2015.05.20?pretty' -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "log": {
      "properties": {
        "geo": {
          "properties": {
            "coordinates": {
              "type": "geo_point"
            }
          }
        }
      }
    }
  }
}
'



echo "Loading log data to Elasticsearch"

curl -H 'Content-Type: application/x-ndjson' -XPOST 'localhost:9201/_bulk?pretty' --data-binary @logs.jsonl


echo "Log data has loaded ..."
