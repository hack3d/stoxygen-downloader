FROM adoptopenjdk/openjdk11:jre-11.0.9_11-debianslim
COPY staging/stoxygen-downloader*.jar /app.jar

CMD ["/usr/bin/java", "-jar", "/app.jar"]