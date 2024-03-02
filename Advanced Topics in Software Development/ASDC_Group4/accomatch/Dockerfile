FROM maven:3.8.7-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:18-alpine
COPY --from=build /home/app/target/accomatch-0.0.1-SNAPSHOT.jar /usr/local/lib/cicd.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/cicd.jar"]