
FROM amazoncorretto:17-alpine-jdk AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build -x test

FROM amazoncorretto:17-alpine
WORKDIR /app

RUN apk add --no-cache curl

COPY --from=builder /app/build/libs/msa_sistema_bancario-*.jar app.jar


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
