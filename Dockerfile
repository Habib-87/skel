FROM openjdk:11-jdk
VOLUME /tmp
COPY target/skel-0.0.1-SNAPSHOT.jar app.jar
CMD [ "sh", "-c", "java -jar /app.jar" ]