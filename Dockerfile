FROM adoptopenjdk/openjdk11:11.0.9-jdk-buster
COPY staging/stoxygen-downloader*.jar /app.jar

CMD ["/usr/bin/java", "-jar", "/app.jar"]