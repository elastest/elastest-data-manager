
FROM mysql:5.7

# Set Image Labels
ARG GIT_COMMIT=unspecified
LABEL git_commit=$GIT_COMMIT

ARG COMMIT_DATE=unspecified
LABEL commit_date=$COMMIT_DATE

ARG VERSION=unspecified
LABEL version=$VERSION 

ADD init /docker-entrypoint-initdb.d/
ADD config/* /etc/mysql/conf.d/
