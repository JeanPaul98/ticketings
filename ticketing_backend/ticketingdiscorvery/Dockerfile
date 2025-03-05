# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
#FROM alpine:latest

#CMD ["/bin/sh"]



# Use an OpenJDK Runtime as a parent image
#FROM openjdk:17-jdk-slim
FROM eclipse-temurin:17-jdk-jammy as base
# Define environment variables
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \JAVA_OPTS=""
ENV JAVA_HOME /usr/local/openjdk-17
ENV PATH $PATH:$JAVA_HOME/bin
# Set the working directory to /app
WORKDIR  /app
# Copy the executable into the container at /app
# Copy the Spring Boot application JAR file into the Docker image
COPY target/* /app

ADD target/*.jar ticketing-discoveryservice.jar
#ADD C:\Users\Admin\Documents\dev_acl\serveur_congo\pnep\pnep_backend\regideso-api.jar regideso-api.jar
# Make port 8080 available to the world outside this container
EXPOSE 9093
# Run app.jar when the container launches
#ENTRYPOINT ["java", "-jar", "/app/regidesoapi-kin.jar"]
CMD ["java", "-jar", "/app/ticketing-discoveryservice.jar"]