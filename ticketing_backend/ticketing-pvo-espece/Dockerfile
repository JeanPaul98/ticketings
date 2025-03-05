# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
#FROM alpine:latest

#CMD ["/bin/sh"]


#RUN mkdir -p /logs
#WORKDIR /logs

# Use an OpenJDK Runtime as a parent image
FROM openjdk:17-jdk-slim
# Define environment variables
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \JAVA_OPTS=""
ENV JAVA_HOME /usr/local/openjdk-17
ENV PATH $PATH:$JAVA_HOME/bin
# Set the working directory to /app
WORKDIR  /app
# Copy the executable into the container at /app
ADD target/*.jar ticketing-pvo-espece.jar
#ADD C:\Users\Admin\Documents\dev_acl\serveur_congo\pnep\pnep_backend\regideso-api.jar regideso-api.jar
# Make port 8080 available to the world outside this container
EXPOSE 0
# Run app.jar when the container launches
#ENTRYPOINT ["java", "-jar", "/app/regidesoapi-congo-central.jar"]
CMD ["java", "-jar", "/app/ticketing-pvo-espece.jar"]