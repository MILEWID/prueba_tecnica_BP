# 🏦 MSA Sistema Bancario - Arquitectura Hexagonal

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.4-blue.svg)](https://gradle.org)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue.svg)](https://www.postgresql.org/)

## 📋 Descripción del Proyecto

Sistema bancario desarrollado con **Arquitectura Hexagonal (Ports & Adapters)** que implementa gestión completa de clientes, cuentas y movimientos bancarios. El proyecto demuestra el uso de principios **SOLID**, patrones de diseño enterprise, y mejores prácticas de desarrollo backend con Java y Spring Boot.

### 🎯 Características Principales

- ✅ **Arquitectura Hexagonal** completa con separación de capas
- ✅ **Principios SOLID** aplicados en toda la implementación
- ✅ **API REST** documentada con **OpenAPI/Swagger**
- ✅ **Operaciones bancarias** (débitos, créditos, transferencias)
- ✅ **Reportes PDF** con iText7
- ✅ **Tests unitarios** con JUnit 5 y Mockito
- ✅ **Contenedorización Docker** completa
- ✅ **Validaciones** con Bean Validation
- ✅ **Mapeo automático** con MapStruct
- ✅ **Manejo centralizado de excepciones**
- ✅ **Identificación única** por cédula/identificación (sin clienteId redundante)

## 🏗️ Arquitectura Hexagonal Implementada

### Estructura del Proyecto
```
src/main/java/com/pichincha/
├── MsaSistemaBancarioApplication.java    # Punto de entrada
├── adapters/                             # ADAPTADORES
│   ├── input/                           # Adaptadores de entrada
│   │   └── rest/                        # Controllers REST
│   │       ├── ClienteController.java
│   │       ├── CuentaController.java
│   │       ├── MovimientoController.java
│   │       └── GlobalExceptionHandler.java
│   └── output/                          # Adaptadores de salida
│       ├── persistence/                 # Adaptadores JPA
│       │   ├── ClienteRepositoryAdapter.java
│       │   ├── CuentaRepositoryAdapter.java
│       │   └── MovimientoRepositoryAdapter.java
│       └── external/                    # Servicios externos
│           └── PdfReportGenerator.java
├── application/                         # CAPA DE APLICACIÓN
│   └── usecases/                       # Casos de uso (Lógica de negocio)
│       ├── ClienteUseCaseImpl.java
│       ├── CuentaUseCaseImpl.java
│       └── MovimientoUseCaseImpl.java
├── domain/                             # DOMINIO
│   ├── Cliente.java                    # Entidades del dominio
│   ├── Cuenta.java
│   ├── Movimiento.java
│   └── Persona.java
├── ports/                              # PUERTOS (Interfaces)
│   ├── input/                          # Puertos de entrada
│   │   ├── ClienteUseCase.java
│   │   ├── CuentaUseCase.java
│   │   └── MovimientoUseCase.java
│   └── output/                         # Puertos de salida
│       ├── ClienteRepositoryPort.java
│       ├── CuentaRepositoryPort.java
│       ├── MovimientoRepositoryPort.java
│       └── ReporteGeneratorPort.java
├── dto/                                # Data Transfer Objects
├── mappers/                            # MapStruct Mappers
├── errors/                             # Excepciones personalizadas
└── config/                             # Configuraciones
    ├── BeanConfiguration.java
    └── OpenApiConfig.java
```

## 🎯 Principios SOLID Implementados

### 1. **Single Responsibility Principle (SRP)**
- **ClienteController**: Solo maneja requests HTTP de clientes
- **ClienteUseCaseImpl**: Solo coordina lógica de negocio de clientes
- **ClienteRepositoryAdapter**: Solo maneja persistencia de clientes

### 2. **Open/Closed Principle (OCP)**
- Los casos de uso están **cerrados para modificación** pero **abiertos para extensión**
- Nuevos adaptadores pueden agregarse sin modificar el código existente
- Interfaces permiten intercambiar implementaciones

### 3. **Liskov Substitution Principle (LSP)**
- Todas las implementaciones de `ClienteUseCase` son intercambiables
- Los adaptadores de persistencia pueden sustituirse sin afectar la lógica

### 4. **Interface Segregation Principle (ISP)**
- Interfaces específicas y cohesivas (`ClienteRepositoryPort`, `ReporteGeneratorPort`)
- Clientes no dependen de métodos que no utilizan

### 5. **Dependency Inversion Principle (DIP)**
- Casos de uso dependen de **abstracciones** (ports), no de implementaciones
- Inyección de dependencias con Spring Boot
- Inversión completa del control de dependencias

## 🔧 Patrones de Diseño Aplicados

### 1. **Ports and Adapters (Hexagonal Architecture)**
- Separación clara entre lógica de negocio y detalles técnicos
- Puertos como contratos de comunicación
- Adaptadores como implementaciones específicas

### 2. **Repository Pattern**
- Abstracción de la capa de persistencia
- `ClienteRepositoryPort` define el contrato
- `ClienteRepositoryAdapter` implementa con Spring Data JPA

### 3. **Use Case Pattern**
- Encapsula reglas de negocio específicas
- Cada operación tiene su caso de uso correspondiente
- Orchestración de diferentes puertos de salida

### 4. **Adapter Pattern**
- `PdfReportGenerator` adapta iText7 a nuestras necesidades
- Controllers adaptan HTTP a casos de uso
- Repository adapters adaptan JPA a puertos

### 5. **Factory Pattern (Implicit)**
- Spring Boot actúa como factory para beans
- MapStruct genera factories para mappers

## 🚀 Tecnologías Utilizadas

### Backend Core
- **Java 17** - LTS version con características modernas
- **Spring Boot 3.2.0** - Framework principal
- **Spring Data JPA** - ORM y persistencia
- **Spring Web** - REST APIs
- **Spring Validation** - Validación de datos

### Base de Datos
- **PostgreSQL 15+** - Base de datos principal
- **Hibernate** - ORM implementation
- **HikariCP** - Connection pooling

### Documentación y Testing
- **OpenAPI 3 / Swagger** - Documentación de API
- **JUnit 5** - Framework de testing
- **Mockito 5** - Mocking framework
- **Spring Boot Test** - Testing integrado

### Herramientas y Librerías
- **MapStruct** - Mapeo automático DTO ↔ Entity
- **Lombok** - Reducción de boilerplate code
- **iText7** - Generación de reportes PDF
- **Gradle 8.4** - Build tool y dependency management
- **Docker** - Containerización

## 🐳 Docker - Despliegue Completo

### Dockerfile Optimizado
```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8081
```

### Comandos de Despliegue
```bash
# 1. Construir el proyecto
./gradlew clean build

# 2. Construir imagen Docker
docker build -t msa-sistema-bancario:latest .

# 3. Ejecutar contenedor
docker run -p 8081:8081 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/bancario \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=password \
  msa-sistema-bancario:latest

# 4. Verificar funcionamiento
curl http://localhost:8081/swagger-ui.html
```

## 📚 API REST - Endpoints Principales

### 🧑‍💼 Gestión de Clientes
```http
GET    /api/clientes              # Listar todos los clientes
GET    /api/clientes/{id}         # Obtener cliente por ID
POST   /api/clientes              # Crear nuevo cliente
PUT    /api/clientes/{id}         # Actualizar cliente
DELETE /api/clientes/{id}         # Eliminar cliente
```

### 🏦 Gestión de Cuentas
```http
GET    /api/cuentas               # Listar todas las cuentas
GET    /api/cuentas/{id}          # Obtener cuenta por ID
POST   /api/cuentas               # Crear nueva cuenta
PUT    /api/cuentas/{id}          # Actualizar cuenta
DELETE /api/cuentas/{id}          # Eliminar cuenta
```

### 💰 Operaciones Bancarias
```http
GET    /api/movimientos           # Listar movimientos
GET    /api/movimientos/{id}      # Obtener movimiento por ID
POST   /api/movimientos           # Crear movimiento
POST   /api/movimientos/debito    # Realizar débito
POST   /api/movimientos/credito   # Realizar crédito
GET    /api/movimientos/reporte   # Generar reporte PDF
```
- `POST /api/clientes` - Crear cliente
- `PUT /api/clientes/{id}` - Actualizar cliente
- `DELETE /api/clientes/{id}` - Eliminar cliente

### Cuenta
- `GET /api/cuentas` - Listar cuentas
- `GET /api/cuentas/{id}` - Obtener cuenta por ID (UUID)
- `GET /api/cuentas/cliente/{identificacion}` - Obtener cuentas por identificación del cliente
- `POST /api/cuentas` - Crear cuenta (usando identificación del cliente)
- `PUT /api/cuentas/{id}` - Actualizar cuenta (UUID)
- `DELETE /api/cuentas/{id}` - Eliminar cuenta (UUID)

### Movimiento
- `GET /api/movimientos` - Listar todos los movimientos
- `POST /api/movimientos/operacion?numeroCuenta=...&movimiento=...` - Registrar movimiento (valores: positivos para crédito, negativos para débito)
- `DELETE /api/movimientos/{id}` - Eliminar movimiento (borrado lógico)
- `GET /api/movimientos/reporte?identificacion=...&desde=...&hasta=...` - Obtener movimientos por cliente y fechas (JSON)
- `GET /api/movimientos/reporte/pdf?identificacion=...&desde=...&hasta=...` - Generar reporte PDF de movimientos

## Características Modernas Implementadas

### UUID como Identificadores
- Todos los IDs de entidades ahora usan UUID en lugar de números secuenciales
- Mayor seguridad y escalabilidad
- Evita conflictos de IDs en sistemas distribuidos

### Búsqueda por Identificación
- Las cuentas se crean y actualizan usando la identificación del cliente ("123456")
- Los reportes de movimientos se generan usando la identificación del cliente
- Permite múltiples cuentas por cliente

### Arquitectura Hexagonal
- Separación clara entre dominio, aplicación e infraestructura
- Fácil testabilidad y mantenibilidad
- Puertos y adaptadores para independencia tecnológica

### Error Handling Estandarizado
- Respuestas de error consistentes en formato JSON
- Códigos de estado HTTP apropiados
- Mensajes descriptivos para debugging

### Operaciones Bancarias
- Soporte para débitos y créditos
- Validación de saldos
- Reportes en JSON y PDF

## Ejemplo de uso
Ver la colección Postman incluida en el repositorio para ejemplos de cada endpoint CRUD y operaciones bancarias.

### Identificación de Cliente
Para las operaciones que requieren identificación de cliente, usar el valor único "123456":
- Crear cuenta: `clienteIdentificacion: "123456"`
- Buscar cuentas: `GET /api/cuentas/cliente/123456`
- Generar reportes: `identificacion=123456`
