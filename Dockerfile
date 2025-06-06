# Usa una imagen oficial de Java 17 para correr aplicaciones Spring Boot
FROM eclipse-temurin:17-jdk-alpine

# Crea un directorio para la app
WORKDIR /app

# Copia el JAR generado por Gradle al contenedor
COPY build/libs/*.jar app.jar

# Expón el puerto 8080 (Railway usará la variable $PORT)
EXPOSE 8080

# Comando para correr la app
ENTRYPOINT ["java", "-jar", "app.jar"] 