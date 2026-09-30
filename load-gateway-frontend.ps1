Write-Host "Saving gateway and frontend images..."

docker save docker-api-gateway:latest -o api-gateway.tar
docker save docker-frontend:latest -o frontend.tar


Write-Host "Copying images..."

docker cp api-gateway.tar desktop-control-plane:/api-gateway.tar
docker cp frontend.tar desktop-control-plane:/frontend.tar


Write-Host "Importing gateway..."

docker exec desktop-control-plane ctr --namespace k8s.io images import /api-gateway.tar


Write-Host "Importing frontend..."

docker exec desktop-control-plane ctr --namespace k8s.io images import /frontend.tar


Write-Host "Completed!"