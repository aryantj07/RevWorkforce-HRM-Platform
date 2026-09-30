Write-Host "Saving service images..."

docker save docker-user-service:latest -o user-service.tar
docker save docker-leave-service:latest -o leave-service.tar
docker save docker-performance-service:latest -o performance-service.tar
docker save docker-employee-management-service:latest -o employee-management-service.tar
docker save docker-notification-service:latest -o notification-service.tar
docker save docker-reporting-service:latest -o reporting-service.tar


Write-Host "Copying images into Kubernetes node..."

docker cp user-service.tar desktop-control-plane:/user-service.tar
docker cp leave-service.tar desktop-control-plane:/leave-service.tar
docker cp performance-service.tar desktop-control-plane:/performance-service.tar
docker cp employee-management-service.tar desktop-control-plane:/employee-management-service.tar
docker cp notification-service.tar desktop-control-plane:/notification-service.tar
docker cp reporting-service.tar desktop-control-plane:/reporting-service.tar


Write-Host "Importing images into Kubernetes containerd..."

docker exec desktop-control-plane ctr --namespace k8s.io images import /user-service.tar

docker exec desktop-control-plane ctr --namespace k8s.io images import /leave-service.tar

docker exec desktop-control-plane ctr --namespace k8s.io images import /performance-service.tar

docker exec desktop-control-plane ctr --namespace k8s.io images import /employee-management-service.tar

docker exec desktop-control-plane ctr --namespace k8s.io images import /notification-service.tar

docker exec desktop-control-plane ctr --namespace k8s.io images import /reporting-service.tar


Write-Host "Done loading images!"