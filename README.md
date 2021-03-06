# es-quarkus-compliance project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .


## What this microservice does

Currently this microservice acts as the first of the microservices. It contains a mock producer that creates a mock Java "FinancialMessage" POJO with the compliance_service boolean set to true initially. It will then send that mocked message to the Kafka topic. The service will then pull from the topic, change it's compliance_service boolean to false and the trade_validation flag to true to signal that to the next microservice for consumption.


## Setting the environment variables prior to use

Run the following command and replace the items in <> with your values prior to running the application. Note that the CERT_LOCATION environment variable is only necessary if you need to connect to an Event Streams instance on Cloud Pak for Integration for example.

```shell
export BOOTSTRAP_SERVERS=<your-bootstrap-server> \ 
export TOPIC_NAME=<your-topic-name> \
export API_KEY=<your-api-key> \
export CERT_LOCATION=<path-to-truststorefile/es-cert.jks>
```


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

Note - if you plan to spin up the other Financial project microservices locally you will need to add a -Ddebug=<port> to the above run command as the default puts the local service at port 5005.


## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `es-quarkus-compliance-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/es-quarkus-compliance-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/es-quarkus-compliance-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.
