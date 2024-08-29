FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/cloudFileStorage-3.3.2.jar /app/cloudFileStorage-3.3.2.jar

ENTRYPOINT ["java", "-jar", "/app/cloudFileStorage-3.3.2.jar"]