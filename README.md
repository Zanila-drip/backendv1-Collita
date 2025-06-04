# Demo12 - API REST con Spring Boot y MongoDB

## Descripción
API REST desarrollada con Spring Boot y MongoDB, siguiendo las mejores prácticas de desarrollo y despliegue.

## Requisitos
- Java 17
- MongoDB
- Gradle

## Configuración del Entorno
1. Clonar el repositorio
2. Configurar las variables de entorno:
   - `MONGODB_URI`: URI de conexión a MongoDB
   - `PORT`: Puerto de la aplicación (por defecto: 8080)

## Ejecución Local
```bash
./gradlew bootRun
```

## Documentación de la API
- Swagger UI: http://localhost:8080/api/v1/swagger-ui.html
- OpenAPI: http://localhost:8080/api/v1/api-docs

## Monitoreo
- Health Check: http://localhost:8080/api/v1/actuator/health
- Métricas: http://localhost:8080/api/v1/actuator/metrics

## Estructura del Proyecto
```
src/main/kotlin/com/desarrollomovil/demo12/
├── config/         # Configuraciones de la aplicación
├── controller/     # Controladores REST
├── model/         # Modelos de datos
├── repository/    # Repositorios de MongoDB
├── service/       # Lógica de negocio
└── util/          # Utilidades
```

## Despliegue
El proyecto está configurado para ser desplegado en Railway.

## Testing
```bash
./gradlew test
```

## Licencia
MIT 