# Experimenting with AWS lambda

Function created like so:

```
aws lambda create-function \
--region eu-west-1 \
--function-name javatest \
--zip-file fileb://./target/lambda-java-example-1.0-SNAPSHOT.jar \
--role arn:aws:iam::717594159500:role/AWSLambdaBasicExecutionRole \
--handler example.Hello::myHandler \
--runtime java8 \
--timeout 15 \
--memory-size 128
```

Builds and deploys like so:

```
mvn package && \
aws lambda update-function-code --region eu-west-1 --function-name javatest --zip-file fileb://./target/lambda-java-example-1.0-SNAPSHOT.jar && \
aws lambda invoke --region eu-west-1 --function-name javatest --payload 123 run-result.txt && \
cat run-result.txt
```
