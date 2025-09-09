# Sistema Bancario - Banco Pichincha

## 📋 Descripción General

Este proyecto es un sistema bancario completo que incluye tanto el backend (microservicio) como el frontend (Single Page Application). Permite la gestión integral de clientes, cuentas bancarias, movimientos y generación de reportes.

## 🏗️ Arquitectura del Sistema

### Backend - Microservicio (MSA)
- **Tecnología**: Java + Spring Boot
- **Base de Datos**: Configuración con esquemas SQL
- **API**: RESTful endpoints
- **Contenedores**: Docker support

### Frontend - SPA (Single Page Application)
- **Framework**: Angular 17+
- **Estilos**: SCSS con diseño responsivo
- **UI**: Material Icons integration
- **Arquitectura**: Componentes modulares

## 🚀 Funcionalidades Principales

### 👥 Gestión de Clientes
- ✅ Crear nuevos clientes
- ✅ Visualizar lista de clientes
- ✅ Editar información de clientes
- ✅ Eliminar clientes (con validaciones)
- ✅ Búsqueda y filtrado

### 🏦 Gestión de Cuentas
- ✅ Crear cuentas bancarias
- ✅ Asociar cuentas a clientes
- ✅ Diferentes tipos de cuenta (Ahorros, Corriente)
- ✅ Visualizar saldos y estados
- ✅ Activar/Desactivar cuentas

### 💰 Gestión de Movimientos
- ✅ Registrar depósitos y retiros
- ✅ Validaciones de saldo disponible
- ✅ Historial de transacciones
- ✅ Estados de movimientos
- ✅ Filtrado por fecha y tipo

### 📊 Reportes
- ✅ Reportes por cliente y período
- ✅ Resumen de ingresos y egresos
- ✅ Saldo inicial, movimientos y saldo final
- ✅ Exportación a PDF
- ✅ Filtros avanzados

## 🛠️ Requisitos del Sistema

### Backend
- Java 11 o superior
- Gradle
- Base de datos compatible (PostgreSQL/MySQL)
- Docker (opcional)

### Frontend
- Node.js 18 o superior
- npm o yarn
- Angular CLI

## ⚙️ Instalación y Configuración

### 1. Backend (MSA)

```bash
# Navegar al directorio del backend
cd msa_sistema_bancario

# Compilar el proyecto
./gradlew build

# Ejecutar la aplicación
./gradlew bootRun
```

**Con Docker:**
```bash
# Construir la imagen
docker build -t sistema-bancario-api .

# Ejecutar el contenedor
docker run -p 8080:8080 sistema-bancario-api
```

### 2. Frontend (SPA)

```bash
# Navegar al directorio del frontend
cd spa_sistema_bancario

# Instalar dependencias
npm install

# Ejecutar en modo desarrollo
ng serve

# La aplicación estará disponible en http://localhost:4200
```

### 3. Base de Datos

```sql
-- Ejecutar el script de esquema
-- Ubicado en: msa_sistema_bancario/db_schema.sql
```

## 🎨 Características de Diseño

### Paleta de Colores Banco Pichincha
- **Primario**: #FFD700 (Amarillo corporativo)
- **Secundario**: #3B82F6 (Azul)
- **Fondo**: #F8FAFC (Gris claro)
- **Texto**: #111827 (Gris oscuro)
- **Sidebar**: #1F2937 (Gris carbón)

### Responsive Design
- ✅ Diseño adaptativo para móviles
- ✅ Sidebar colapsible en pantallas pequeñas
- ✅ Tablas con scroll horizontal
- ✅ Optimización táctil

### Iconografía
- **Material Icons** para consistencia visual
- Iconos semánticos para cada acción
- Estados visuales claros

## 📱 Navegación y Uso

### Menú Principal (Sidebar)
1. **Clientes**: Gestión completa de clientes
2. **Cuentas**: Administración de cuentas bancarias
3. **Movimientos**: Registro de transacciones
4. **Reportes**: Generación de informes

### Flujo de Trabajo Típico
1. **Crear Cliente** → Registrar información personal
2. **Crear Cuenta** → Asociar cuenta al cliente
3. **Realizar Movimientos** → Depósitos/Retiros
4. **Generar Reportes** → Análisis y seguimiento

## 🔒 Validaciones y Seguridad

### Validaciones de Negocio
- ✅ Saldo suficiente para retiros
- ✅ Estados de cuenta válidos
- ✅ Datos obligatorios completos
- ✅ Formatos de entrada correctos

### UX/UI
- ✅ Mensajes de error claros
- ✅ Confirmaciones para acciones destructivas
- ✅ Estados de carga visual
- ✅ Feedback inmediato

## 🧪 Testing

### Backend
```bash
# Ejecutar tests unitarios
./gradlew test

# Ejecutar tests de integración
./gradlew integrationTest
```

### Frontend
```bash
# Tests unitarios
ng test

# Tests end-to-end
ng e2e
```

## 📦 Colección de Postman

Incluye una colección completa de endpoints en:
`msa_sistema_bancario/postman_collection.json`

## 🚀 Deployment

### Producción
1. **Backend**: Generar JAR ejecutable con `./gradlew bootJar`
2. **Frontend**: Build de producción con `ng build --prod`
3. **Docker**: Imágenes containerizadas disponibles

## 🤝 Contribuciones

### Estructura de Código
- Código limpio y documentado
- Principios SOLID aplicados
- Separación de responsabilidades
- Arquitectura modular

### Estándares
- Nomenclatura consistente
- Comentarios en español
- Commits descriptivos
- Code review process

## 📞 Soporte

Para problemas técnicos o consultas:
- Revisar logs de aplicación
- Verificar configuración de base de datos
- Comprobar dependencias actualizadas

## 📄 Licencia

Proyecto desarrollado para Banco Pichincha - Uso interno.

---

**Banco Pichincha © 2024** - Sistema de Gestión Bancaria
