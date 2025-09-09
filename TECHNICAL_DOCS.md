# Documentación Técnica - Sistema Bancario

## 🧠 Arquitectura y Decisiones Técnicas

### Frontend - Angular SPA

#### Estructura de Componentes
```
src/app/
├── components/           # Componentes de UI
│   ├── header/          # Encabezado con títulos dinámicos
│   ├── sidebar/         # Navegación lateral
│   ├── top-bar/         # Barra superior con logo
│   ├── clients-card/    # Gestión de clientes
│   ├── cuentas/         # Gestión de cuentas
│   ├── movimientos/     # Gestión de transacciones
│   ├── reportes/        # Generación de reportes
│   └── modal/           # Modal reutilizable
├── services/            # Servicios de datos
├── models/              # Interfaces TypeScript
└── shared/              # Utilidades compartidas
```

#### Sistema de Estilos - SCSS

**Variables Principales** (`app.scss`):
```scss
$primary: #ffd700;          // Amarillo Banco Pichincha
$secondary: #3b82f6;        // Azul para elementos secundarios
$bg-main: #f8fafc;          // Fondo principal
$bg-sidebar: #1f2937;       // Fondo sidebar
$text: #111827;             // Texto principal
$border-radius: 8px;        // Radio de bordes consistente
```

**Arquitectura de Estilos**:
1. **Variables globales**: Colores, espaciados, sombras
2. **Reset CSS**: Normalización de estilos base
3. **Layout system**: Flexbox para estructura principal
4. **Component styles**: Estilos específicos por componente
5. **Utility classes**: Clases de utilidad reutilizables

#### Layout System

**Estructura Principal**:
```html
<app-top-bar>           <!-- Fijo arriba, z-index 1002 -->
<div class="app-wrapper"> <!-- Contenedor principal -->
  <div class="app">
    <app-sidebar>       <!-- Fijo izquierda, width: 280px -->
    <div class="app__content"> <!-- Área de contenido -->
      <app-header>      <!-- Títulos dinámicos -->
      <section class="content__body"> <!-- Contenido -->
```

**Responsive Design**:
- **Desktop**: Sidebar fijo (280px), contenido con margen izquierdo
- **Mobile**: Sidebar overlay, contenido full-width
- **Breakpoint**: 768px

#### Gestión de Estado

**Signals (Angular 17)**:
```typescript
// Uso de signals para reactividad
section = signal<string>('clientes');
title = signal<string>('Clientes');
```

**Services Pattern**:
- `ClientService`: CRUD de clientes
- `CuentaService`: Gestión de cuentas
- `MovimientoService`: Transacciones
- Cada servicio maneja su propia lógica de negocio

#### Componentes Clave

**Modal Reutilizable**:
- Sistema de modal dinámico
- Confirmaciones y formularios
- Props: `isOpen`, `title`, `size`, `type`

**Header Dinámico**:
- Títulos que cambian según la sección
- Padding alineado con el contenido (2rem desktop, 1rem mobile)
- Typography system consistente

**Data Tables**:
- Estilos globales en `app.scss`
- Hover effects y estados
- Responsive con scroll horizontal
- Action buttons consistentes

### Backend - Spring Boot MSA

#### Estructura del Proyecto
```
src/main/java/com/pichincha/
├── controller/          # REST Controllers
├── service/            # Lógica de negocio
├── repository/         # Acceso a datos
├── model/              # Entidades JPA
├── dto/                # Data Transfer Objects
└── config/             # Configuraciones
```

#### API Design

**RESTful Endpoints**:
- `GET /api/clientes` - Lista de clientes
- `POST /api/clientes` - Crear cliente
- `PUT /api/clientes/{id}` - Actualizar cliente
- `DELETE /api/clientes/{id}` - Eliminar cliente

**Response Format**:
```json
{
  "success": true,
  "data": {...},
  "message": "Operación exitosa",
  "timestamp": "2024-09-09T10:00:00Z"
}
```

## 🎨 Design System Implementado

### Color Palette
```scss
// Colores principales
$primary: #ffd700;      // Amarillo Pichincha - Botones principales
$primary-dark: #f59e0b; // Hover state del amarillo
$secondary: #3b82f6;    // Azul - Enlaces y acciones secundarias
$success: #10b981;      // Verde - Estados exitosos
$warning: #f59e0b;      // Naranja - Advertencias
$danger: #ef4444;       // Rojo - Errores y eliminaciones

// Colores de fondo
$bg-main: #f8fafc;      // Fondo general de la app
$bg-sidebar: #1f2937;   // Fondo oscuro del sidebar
$card-bg: #ffffff;      // Fondo de cards y contenido

// Colores de texto
$text: #111827;         // Texto principal oscuro
$text-light: #6b7280;   // Texto secundario gris
```

### Typography System
```scss
h1: 2rem (32px)    - Títulos principales
h2: 1.5rem (24px)  - Títulos de secciones
h3: 1.25rem (20px) - Subtítulos
h4: 1.125rem (18px)- Títulos menores
h5: 1rem (16px)    - Texto normal destacado
h6: 0.875rem (14px)- Texto pequeño
```

### Spacing System
```scss
$gap: 1rem;            // Espaciado base (16px)
// Multiplicadores: 0.25rem, 0.5rem, 1rem, 1.5rem, 2rem, 3rem
```

### Component Design Patterns

**Buttons**:
```scss
.btn {
  // Base styles
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  font-weight: 600;
  
  // Variants
  &--primary    // Amarillo Pichincha
  &--secondary  // Azul
  &--success    // Verde
  &--danger     // Rojo
  &--outline    // Transparente con borde
}
```

**Cards**:
```scss
.card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  padding: 1.5rem;
}
```

**Tables**:
```scss
.data-table {
  // Header con fondo gris claro
  &__header { background: #f8fafc; }
  
  // Hover effects en filas
  tbody tr:hover { background: #f9fafb; }
  
  // Action buttons
  .action-btn--edit    // Azul
  .action-btn--delete  // Rojo
}
```

## 🔧 Patrones de Desarrollo

### Component Architecture
```typescript
// Patrón de componente Angular
@Component({
  selector: 'app-component',
  templateUrl: './component.html',
  styleUrls: ['./component.scss'],  // Estilos específicos
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class Component {
  // Signals para estado reactivo
  data = signal<Type[]>([]);
  isLoading = signal<boolean>(false);
  
  // Methods
  onAction() { /* ... */ }
}
```

### Service Pattern
```typescript
@Injectable({
  providedIn: 'root'
})
export class DataService {
  private apiUrl = 'http://localhost:8080/api';
  
  constructor(private http: HttpClient) {}
  
  getAll(): Observable<Type[]> {
    return this.http.get<Type[]>(`${this.apiUrl}/endpoint`);
  }
}
```

### Modal Pattern
```typescript
// Modal service para manejo centralizado
openModal(config: ModalConfig): void {
  this.modalConfig.set(config);
  this.isModalOpen.set(true);
}
```

## 🎭 Estados y Transiciones

### Loading States
```scss
.loading {
  &__spinner {
    border: 2px solid #f3f4f6;
    border-top: 2px solid $secondary;
    animation: spin 1s linear infinite;
  }
}
```

### Empty States
```scss
.empty-state {
  text-align: center;
  padding: 3rem 2rem;
  color: $text-light;
  
  &__icon { font-size: 3rem; opacity: 0.5; }
}
```

### Alert System
```scss
.alert {
  border-left: 4px solid;  // Borde izquierdo coloreado
  
  &--success  // Verde con icono check
  &--error    // Rojo con icono warning
  &--warning  // Naranja con icono info
  &--info     // Azul con icono info
}
```

## 🎯 Optimizaciones Implementadas

### Performance Frontend
1. **OnPush Change Detection**: Componentes optimizados
2. **Lazy Loading**: Carga diferida de módulos
3. **Signal-based**: Reactividad eficiente
4. **Tree Shaking**: Bundle optimizado

### CSS Optimizations
1. **SCSS Variables**: Reutilización de valores
2. **Mixins**: Patrones reutilizables
3. **BEM Methodology**: Nomenclatura consistente
4. **Responsive Mixins**: Media queries centralizadas

### UX Optimizations
1. **Micro-interactions**: Hover effects, transitions
2. **Visual Feedback**: Loading states, confirmations
3. **Error Handling**: Mensajes claros y accionables
4. **Keyboard Navigation**: Accesibilidad

## 🧪 Testing Strategy

### Frontend Testing
```bash
# Unit tests
ng test --code-coverage

# E2E tests
ng e2e

# Linting
ng lint
```

### Backend Testing
```bash
# Unit tests
./gradlew test

# Integration tests
./gradlew integrationTest

# Coverage report
./gradlew jacocoTestReport
```

## 🚀 Build & Deployment

### Frontend Production Build
```bash
ng build --configuration production
# Output: dist/ folder with optimized assets
```

### Backend Production Build
```bash
./gradlew bootJar
# Output: build/libs/app.jar executable
```

### Docker Strategy
```dockerfile
# Multi-stage build para optimización
FROM node:18-alpine as build-stage
FROM openjdk:11-jre-slim as runtime
```

## 📊 Métricas y Monitoreo

### Performance Metrics
- **Bundle Size**: < 2MB compressed
- **First Contentful Paint**: < 2s
- **Time to Interactive**: < 3s

### Code Quality
- **TypeScript**: Strict mode enabled
- **ESLint**: Angular recommended rules
- **Prettier**: Consistent formatting
- **SonarQube**: Code quality gates

---

Esta documentación técnica te da una visión completa de las decisiones arquitectónicas, patrones implementados y optimizaciones aplicadas en el sistema bancario.
