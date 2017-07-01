Write-Host   "Tearing down using docker-compose"

docker-compose  --project-name edm down

rm -r alluxio\data\*
rm -r mysql\data\*
rm -r elasticsearch\esdata1\*
rm -r elasticsearch\esdata2\*

docker volume prune -f