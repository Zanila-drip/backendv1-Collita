# Etapa 1: Construcción del JAR usando Gradle
FROM gradle:8.4.0-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Etapa 2: Imagen ligera para correr la app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 