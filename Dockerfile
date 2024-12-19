FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
