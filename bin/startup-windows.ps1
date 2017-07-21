Write-Host  "Starting up using docker-compose"

docker network rm elastest
docker network create -d bridge elastest

docker-compose --project-name edm   up -d