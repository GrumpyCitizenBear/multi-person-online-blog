FROM adoptopenjdk:11-jdk-hotspot

WORKDIR /app
COPY target/opensnn-1.0-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java","-jar","opensnn-1.0-SNAPSHOT.jar"  ]