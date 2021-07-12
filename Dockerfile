FROM java:8-jdk
RUN mkdir /app
WORKDIR /app
COPY target/nagp-0.0.1-SNAPSHOT.jar /app
EXPOSE 8002
ENTRYPOINT ["java", "-jar", "nagp-0.0.1-SNAPSHOT.jar"]

