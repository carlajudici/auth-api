FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/quarkus-app /app
EXPOSE 8080
CMD ["java", "-jar", "quarkus-run.jar"]