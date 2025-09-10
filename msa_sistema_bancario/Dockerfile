# Dockerfile para msa_sistema_bancario

# Stage 1: Build
FROM amazoncorretto:17-alpine-jdk AS builder
WORKDIR /app
COPY . .
# Dar permisos de ejecución al wrapper de Gradle
RUN chmod +x ./gradlew
# Construir la aplicación
RUN ./gradlew build -x test

# Stage 2: Runtime
FROM amazoncorretto:17-alpine
WORKDIR /app

# Instalar dependencias del sistema necesarias
RUN apk add --no-cache curl

# Copiar el JAR desde el stage de build
COPY --from=builder /app/build/libs/msa_sistema_bancario-*.jar app.jar

# Crear un usuario no-root por seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
RUN chown -R appuser:appgroup /app
USER appuser

# Exponer el puerto correcto (según application.properties)
EXPOSE 8081

# Variables de entorno para Docker
ENV SPRING_PROFILES_ACTIVE=docker

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8081/actuator/health || exit 1

# Punto de entrada con configuración optimizada
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Xmx512m", \
    "-Xms256m", \
    "-jar", \
    "app.jar"]
