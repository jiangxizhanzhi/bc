kubectl apply -f pv-nfs.yml
kubectl apply -f pvc-nfs.yml
kubectl apply -f deployment-all.yml
kubectl apply -f service-all.yml

kubectl delete -f service-all.yml
kubectl delete -f deployment-all.yml
kubectl delete -f pvc-nfs.yml
kubectl delete -f pv-nfs.yml
