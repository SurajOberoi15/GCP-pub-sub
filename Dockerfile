FROM openjdk:11
EXPOSE 8082
FROM maven:3.6.3-jdk-11-slim AS build
WORKDIR usr/src/app
COPY . ./
RUN mvn clean install -DskipTests
ADD target/pub-sub-sample.jar  pub-sub-sample.jar
ENTRYPOINT ["java", "-jar", "/pub-sub-sample.jar"]
