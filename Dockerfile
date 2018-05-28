FROM openjdk:8-slim-stretch
COPY build/stoxygen-downloader*.jar /app.jar

CMD ["/usr/bin/java", "-jar", "/app.jar"]