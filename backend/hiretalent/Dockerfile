FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/hiretalent-0.0.1-SNAPSHOT.jar hiretalent.jar
ENTRYPOINT [ "java","-jar","/hiretalent.jar" ]
