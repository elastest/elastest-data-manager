# https://github.com/elastic/kibana-docker
FROM docker.elastic.co/kibana/kibana:5.4.1

# Set Image Labels
ARG GIT_COMMIT=unspecified
LABEL git_commit=$GIT_COMMIT

ARG COMMIT_DATE=unspecified
LABEL commit_date=$COMMIT_DATE

ARG VERSION=unspecified
LABEL version=$VERSION 

# Add your kibana plugins setup here
# Example: RUN kibana-plugin install <name|url>
#RUN kibana-plugin install x-pack
RUN kibana-plugin remove x-pack

