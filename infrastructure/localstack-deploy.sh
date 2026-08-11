#set -e # Stops the script if any command fails
#
#aws --endpoint-url=http://localhost:4566 cloudformation delete-stack \
#    --stack-name patient-management
#
#aws --endpoint-url=http://localhost:4566 cloudformation deploy \
#    --stack-name patient-management \
#    --template-file "./cdk.out/localstack.template.json"
#
#aws --endpoint-url=http://localhost:4566 elbv2 describe-load-balancers \
#    --query "LoadBalancers[0].DNSName" --output text



set -e

awslocal cloudformation deploy \
    --stack-name patient-management \
    --template-file "./cdk.out/localstack.template.json" \
    --s3-bucket cdk-templates-bucket

echo "Deployment complete."

# Optional: check LB DNS (may show 'unknown' since ELBv2 is a stub on Community tier)
awslocal elbv2 describe-load-balancers \
    --query "LoadBalancers[0].DNSName" --output text || echo "Could not retrieve LB DNS (expected on Community tier)"