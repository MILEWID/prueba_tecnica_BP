# Dockerfile para msa_sistema_bancario
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/msa_sistema_bancario-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
