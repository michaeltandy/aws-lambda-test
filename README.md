# Experimenting with AWS lambda

Builds and deploys like so:

```bash
mvn package && aws lambda update-function-code --region eu-west-1 --function-name javatest --zip-file fileb://./target/lambda-java-example-1.0-SNAPSHOT.jar && aws lambda invoke --region eu-west-1 --function-name javatest --payload 123 run-result.txt && cat run-result.txt
```
