#
# The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
# (the "License"). You may not use this work except in compliance with the License, which is
# available at www.apache.org/licenses/LICENSE-2.0
#
# This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
# either express or implied, as more fully set forth in the License.
#
# See the NOTICE file distributed with this work for information regarding copyright ownership.
#

FROM openjdk:7-jdk-alpine

ENV ALLUXIO_VERSION=1.5.0
ENV HADOOP_VERSION=2.7

#ARG ALLUXIO_TARBALL=http://downloads.alluxio.org/downloads/files/${ALLUXIO_VERSION}/alluxio-${ALLUXIO_VERSION}-hadoop-2.8-bin.tar.gz
RUN mkdir /opt && cd /opt \
#	&& wget http://alluxio.org/downloads/files/${ALLUXIO_VERSION}/alluxio-${ALLUXIO_VERSION}-hadoop-${HADOOP_VERSION}-bin.tar.gz 
	&& wget http://alluxio.org/downloads/files/1.5.0/alluxio-1.5.0-hadoop-2.7-bin.tar.gz 
#	\
#	&& tar xvfz alluxio-1.5.0-hadoop-${HADOOP_VERSION}-bin.tar.gz \
#	&& mv alluxio-1.5.0-hadoop-${HADOOP_VERSION} /opt/alluxio \
#	&& rm alluxio-1.5.0-hadoop-${HADOOP_VERSION}-bin.tar.gz


RUN apk add --update bash && \
    # apk add --update git && \
    # apk add --update mvn && \
    rm -rf /var/cache/apk/*

#ADD ${ALLUXIO_TARBALL} /opt/

# RUN mkdir /opt && cd /opt && \
# 	git clone git://github.com/alluxio/alluxio.git && \
#     cd alluxio && \
#     mvn install -DskipTests

# if the tarball was remote, it needs to be untarred
RUN cd /opt && \
    (if ls | grep -q ".tar.gz"; then tar -xzf *.tar.gz && rm *.tar.gz; fi) && \
    mv alluxio-* alluxio

COPY conf /opt/alluxio/conf/
COPY entrypoint.sh /

ENV PATH "$PATH:/opt/alluxio/bin"

ENTRYPOINT ["/entrypoint.sh"]
