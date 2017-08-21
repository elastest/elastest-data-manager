# ElasTest Data Manager (EDM)

The ElasTest Data Manager is responsible for installing, managing and uninstalling the different persistent services available for the whole ElasTest platform.

The persistent services under the responsibility of EDM are the following:

- Relational database (MySQL)
- Persistance control service (API-including Alluxio, S3 & HDFS compatible)
- ElasticSearch (for both logs and metrics)
- API for exporting and importing data

## Features
The version 0.1 of the ElasTest Data Manager, provides the following features:


- One HDFS namenode
- One HDFS datanode
- One Alluxio master
- One Alluxio worker
- An Elasticsearch cluster (with two nodes)
- A Cerebro instance (web tool for monitoring and administering the Elasticsearch cluster )
- A Kibana instance
- A MySQL instance
- REST API to export and import all data stored in HDFS, Elasticsearch and MySQL (backup & restore)

## How to run

## Prerequisites
- Install Docker Compose: https://docs.docker.com/compose/install/
- Install Git: https://www.atlassian.com/git/tutorials/install-git
- Install curl: https://curl.haxx.se/ (Optional - in order to follow some of the examples)

### Clone the project
    # Clone the project to your system
    # Alternatively, you can download the zip file from Github and unzip it
    git clone https://github.com/elastest/elastest-data-manager.git

    # Change working directory to main project folder
    cd elastest-data-manager

### Prepare your environment

    # From main project folder

    # Make scripts executable (Linux, OSX)
    chmod +x bin/*

**Notes for OSX users:**

First, you have to follow the instructions at [https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#docker-cli-run-prod-mode](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#docker-cli-run-prod-mode) to increase vm.max_map_count to 262144.

Also you have to make sure that Docker has enough memory available to it. In Docker for Mac, you can increase memory by going to Preferences -> Advanced and move the Memory slider up.

**Notes for Windows users:**

If using Windows you should run commands/scripts below from Windows PowerShell.

Also you have to make sure that Docker has enough memory available to it. In Docker for Windows, you can increase memory by going to Preferences -> Advanced and move the Memory slider up.

## Start this component using docker-compose
Note: your terminal need to be in the main project folder where the docker-compose.yml is located.

You can start this image using docker-compose. It will start the following:

- One HDFS namenode
- One HDFS datanode
- One Alluxio master
- One Alluxio worker
- An Elasticsearch cluster (with two nodes)
- A Cerebro instance (web tool for monitoring and administering the Elasticsearch cluster )
- A Kibana instance
- A MySQL instance

You have the possibility to scale the number of HDFS datanodes, Alluxio workers and Elasticsearch nodes.

    # From main project folder

    # Start component (Linux, OSX)
    bin/startup-linux.sh

    # Start component (Windows)
    bin/startup-windows.ps1

    # View service status
    docker-compose -p edm ps

    # View logs
    docker-compose -p edm logs

Please note that it will take some time (in the order of several seconds - depending on your system) for all the services to be fully available.

Also note that if you are in a Linux host, you need to set vm.max_map_count of the host to at least 262144  in order to run the Elasticsearch container. (more info at https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#docker-cli-run-prod-mode). If you use the above startup-linux.sh script you don't have to do anything extra since it already contains code to do this. However, in order to be able to change your system settings, the script will prompt you for your sudo password.

## Basic usage

### Load test data (optional)
The following Linux scripts load some test data in the system:

    # From main project folder

    # Load test data to MySQL
    bin/load-mysql-test.sh

    # Load test data to Elasticsearch
    bin/load-es-test.sh

    # Load test data to Alluxio
    bin/load-alluxio-test.sh

 You can verify the data is loaded using the web or client interfaces of each system.
 
### Accessing the web interfaces
Each component provide its own web UI. Open you browser at one of the URLs below, where `dockerhost` is the name / IP of the host running the docker daemon. If using Linux, this is the IP of your linux box. If using OSX or Windows (via Boot2docker), you can find out your docker host by typing `boot2docker ip`.

| Component               	| Port                                               |
| ----------------------- 	| -------------------------------------------------- |
| HDFS NameNode           	| [http://localhost:50070](http://localhost:50070) |
| Alluxio Web Interface		| [http://localhost:19999](http://localhost:19999) |
| Kibana Web Interface		| [http://localhost:5601](http://localhost:5601) |
| Cerebro Web Interface		| [http://localhost:9400/#/overview?host=http:%2F%2Felasticsearch:9200](http://localhost:9400/#/overview?host=http:%2F%2Felasticsearch:9200) |
| REST API Swagger UI		| [http://localhost:8000/api/](http://localhost:8000/api/) |

### Scaling the number of instances
If you want to increase the number of HDFS datanodes in your cluster

    docker-compose -p edm scale hdfs-datanode=<number of instances>

If you want to increase the number of Alluxio workers in your cluster

    docker-compose -p edm scale alluxio-worker=<number of instances>

### Finding the port for web access of scalable containers
To allow worker instances (such as the hdfs-datanode, the alluxio-worker or the esnode) to scale, we need to let docker decide the port used on the host machine.

For example, to find the port for the hdfs-datanode:

    docker-compose -p edm port hdfs-datanode 50075

With this port, you can access the web interfaces of the datanode.

### Accessing the Alluxio REST API
The Alluxio REST API is available at http://localhost:39999

You can try the following examples:

	# From main project folder

	# Upload file (local)
	curl -v -X POST http://localhost:39999/api/v1/paths//LICENSE/create-file
	curl -v -X POST http://localhost:39999/api/v1/streams/1/write --data-binary @LICENSE -H "Content-Type: application/octet-stream"
	curl -v -X POST http://localhost:39999/api/v1/streams/1/close

	# Upload file (HDFS)
	curl -v -X POST http://localhost:39999/api/v1/paths//hdfs/LICENSE/create-file
	curl -v -X POST http://localhost:39999/api/v1/streams/2/write --data-binary @LICENSE -H "Content-Type: application/octet-stream"
	curl -v -X POST http://localhost:39999/api/v1/streams/2/close

	# Read file (local)
	curl -v -X POST http://localhost:39999/api/v1/paths//LICENSE/open-file
	curl -v -X POST http://localhost:39999/api/v1/streams/3/read
	curl -v -X POST http://localhost:39999/api/v1/streams/3/close

	# Read file (HDFS)
	curl -v -X POST http://localhost:39999/api/v1/paths//hdfs/LICENSE/open-file
	curl -v -X POST http://localhost:39999/api/v1/streams/4/read
	curl -v -X POST http://localhost:39999/api/v1/streams/4/close

	# Delete file (local)
	curl -v -X POST http://localhost:39999/api/v1/paths//LICENSE/delete

	# Delete file (HDFS)
	curl -v -X POST http://localhost:39999/api/v1/paths//hdfs/LICENSE/delete

### Elasticsearch
Elasticsearch is listening at localhost:9200 if you want to access it with your own client interface.
For example you can install the Sense Chrome plugin https://chrome.google.com/webstore/detail/sense-beta/lhjgkmllcaadmopgmanpapmpjgmfcfig?hl=en).

You can also connect to it and run queries through the provided Cerebro Web Interface.
In order to connect to Elasticsearch from Cerebro use hostname: elasticsearch and port: 9200

### MySQL
MySQL Server is listening at localhost:3306.

You can connect to it with user: elastest and password: elastest

A database elastest is already created and you can use it for your schemas.

### Stop this component using docker-compose

    # From main project folder

    # Teardown component (Linux, OSX)
    bin/teardown-linux.sh

    # Teardown component (Windows)
    bin/teardown-windows.ps1

## Development documentation

### Architecture

The following diagram depicts the architecture of the EDM and its interaction with other components of the Elastest platform:

![](images/edm_architecture_diagram.png) 

- **Kibana**: An open source data visualization plugin for Elasticsearch. It provides visualization capabilities on top of the content indexed on an Elasticsearch cluster. Users can create bar, line and scatter plots, or pie charts and maps on top of large volumes of data.

- **Cerebro**: An open source (MIT License) elasticsearch web admin tool built using Scala, Play Framework, AngularJS and Bootstrap.

- **Elasticsearch**: A search engine based on Lucene. It provides a distributed, multitenant-capable full-text search engine with an HTTP web interface and schema-free JSON documents.

- **HDFS**: The Hadoop Distributed File System (HDFS) is a distributed file system designed to run on commodity hardware. It has many similarities with existing distributed file systems. However, the differences from other distributed file systems are significant. HDFS is highly fault-tolerant and is designed to be deployed on low-cost hardware. HDFS provides high throughput access to application data and is suitable for applications that have large data sets. HDFS relaxes a few POSIX requirements to enable streaming access to file system data. HDFS was originally built as infrastructure for the Apache Nutch web search engine project. HDFS is an Apache Hadoop subproject. 

- **Alluxio**: Formerly known as Tachyon, Alluxio is the world’s first memory speed virtual distributed storage system. It unifies data access and bridges computation frameworks and underlying storage systems. Applications only need to connect with Alluxio to access data stored in any underlying storage systems. Additionally, Alluxio’s memory-centric architecture enables data access orders of magnitude faster than existing solutions. In the big data ecosystem, Alluxio lies between computation frameworks or jobs, such as Apache Spark, Apache MapReduce, Apache HBase, Apache Hive, or Apache Flink, and various kinds of storage systems, such as Amazon S3, Google Cloud Storage, OpenStack Swift, GlusterFS, HDFS, MaprFS, Ceph, NFS, and Alibaba OSS

- **MySQL**: MySQL is an open source relational database management system (RDBMS) based on Structured Query Language (SQL). MySQL runs on virtually all platforms, including Linux, UNIX, and Windows.


### Prepare development environment

####Prerequisites

For developing the REST API, you will need to first follow the instructions above in order to start the ElasTest Data Manager (EDM) component using docker-compose. 

### Development procedure

Once everything is up, you can edit the Python project residing in `elastest-data-manager/rest` folder using the IDE of your choice. Inside the rest-api container the folder `elastest-data-manager/rest/rest_api_project` is mounted as `/usr/src/app`. Whenever you make changes the application is reloaded automatically. Below, you will find a short description of the individual technologies used for the REST API development. 

The EDM REST API was created using Python, Flask and Flask-RESTPlus. These tools combine into a framework, which automates common tasks:

- API input validation
- formatting output (as JSON)
- generating interactive documentation (with Swagger UI)
- turning Python exceptions into machine-readable HTTP responses

**Flask**

[Flask](http://flask.readthedocs.io/) is a web micro-framework written in Python. Since it’s a micro-framework, Flask does very little by itself. In contrast to a framework like [Django](http://www.djangoproject.com/), which takes the “batteries included” approach, Flask does not come with an ORM, serializers, user management or built-in internationalization. All these features and many others are available as [Flask extensions](http://flask.pocoo.org/extensions/), which make up a rich, but loosely coupled ecosystem.

**Flask-RESTPlus**

[Flask-RESTPlus](https://github.com/noirbizarre/flask-restplus) aims to make building REST APIs quick and easy. It provides just enough syntactic sugar to make your code readable and easy to maintain. The killer feature of RESTPlus is its ability to automatically generate an interactive documentation for the API using Swagger UI.

**Swagger UI**

[Swagger UI](http://swagger.io/swagger-ui/) is part of a suite of technologies for documenting RESTful web services. Swagger has evolved into the [OpenAPI specification](https://openapis.org/), currently curated by the Linux Foundation. Once you have an OpenAPI description of your web service, you can use a variety of tool to generate documentation or even boilerplate code in a variety of languages. Take a look at [swagger.io](http://swagger.io/) for more information.

#### Running the tests
The tests can be run as follows:

    # From main project folder

    # Run the tests
    bin/run-tests.sh

 The output should be a list of tests with status ok. The code for the tests can be found inside the `elastest-data-manager/rest/rest-api-project/tests/test_endpoinds.py` file.
 
#### Accessing the Swagger UI
You can access the REST API Swagger UI at [http://localhost:8000/api/](http://localhost:8000/api/). The documentation is dynamically updated when you make changes to the code.


