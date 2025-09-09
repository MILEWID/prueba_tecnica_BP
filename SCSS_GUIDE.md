# Documentación de Estilos SCSS - Sistema Bancario

## 🎨 Arquitectura de Estilos

### Estructura de Archivos SCSS
```
src/
├── app/
│   ├── app.scss                 # Estilos globales principales
│   └── components/
│       ├── header/header.scss   # Estilos del encabezado
│       ├── sidebar/sidebar.scss # Estilos de navegación
│       ├── modal/modal.scss     # Estilos del modal
│       └── [component]/[component].scss
└── styles.scss                  # Estilos globales adicionales
```

## 📋 Análisis del app.scss

### 1. Variables del Sistema de Diseño
```scss
/* Variables basadas en el diseño de Banco Pichincha */
$gap: 1rem;                    // Espaciado base
$sidebar-width: 280px;         // Ancho fijo del sidebar
$color-border: #e5e7eb;        // Color de bordes
$primary: #ffd700;             // Amarillo corporativo Pichincha
$secondary: #3b82f6;           // Azul para elementos secundarios
$bg-main: #f8fafc;             // Fondo principal de la app
$bg-sidebar: #1f2937;          // Fondo oscuro del sidebar
$card-bg: #ffffff;             // Fondo de tarjetas y contenido
$border-radius: 8px;           // Radio de bordes consistente
$box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
```

### 2. Reset CSS y Estilos Base
```scss
* { 
  font-family: 'roboto', sans-serif;
  box-sizing: border-box; 
  margin: 0;
  padding: 0;
}
```
**Propósito**: Normalizar comportamientos del navegador y establecer box-sizing consistente.

### 3. Sistema de Tipografía
```scss
h1, h2, h3, h4, h5, h6 {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-weight: 700;
  color: $text;
  line-height: 1.2;
  margin: 0 0 1rem 0;
}
```
**Jerarquía Visual**:
- `h1`: 2rem (32px) - Títulos principales como "Gestión de Clientes"
- `h2`: 1.5rem (24px) - Títulos de secciones
- `h3`: 1.25rem (20px) - Subtítulos
- Responsive: Los tamaños se reducen en móvil

### 4. Layout Principal
```scss
:host {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.app-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding-top: 64px;        // Espacio para el top-bar fijo
}

.app {
  display: flex;
  flex: 1; 
  background: $bg-main;
}
```
**Estructura de Layout**:
1. **:host**: Contenedor raíz de Angular, altura completa del viewport
2. **app-wrapper**: Wrapper principal con padding para el top-bar
3. **app**: Container flex horizontal para sidebar + contenido

### 5. Sistema de Contenido
```scss
.app__content {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: $card-bg;
  position: relative;
  margin-left: $sidebar-width;    // Espacio para sidebar fijo
  min-height: calc(100vh - 64px);
}

.content {
  flex: 1;
  display: flex;
  flex-direction: column;
  
  &__body {
    flex: 1;
    padding: 2rem;              // Padding principal del contenido
  }
}
```

### 6. Sistema de Sidebar
```scss
.sidebar {
  width: $sidebar-width;
  background: $bg-sidebar;
  color: white;
  position: fixed;
  top: 64px;                    // Debajo del top-bar
  left: 0;
  bottom: 0;
  z-index: 1000;
}
```

### 7. Responsive Design
```scss
@media (max-width: 768px) {
  .app__content {
    margin-left: 0;             // Sin margen en móvil
    width: 100%;
  }
  
  .sidebar {
    width: 100%;
    left: -100%;                // Oculto por defecto
    z-index: 1001;
    
    &.sidebar--open {
      left: 0;                  // Mostrar cuando esté activo
    }
  }
}
```

### 8. Sistema de Botones
```scss
.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.75rem 1.5rem;
  border: 2px solid transparent;
  border-radius: $border-radius;
  font-weight: 600;
  font-size: 0.875rem;
  transition: all 0.2s ease;
  
  &--primary {
    background: $primary;
    color: #1f2937;             // Texto oscuro sobre amarillo
    
    &:hover:not(:disabled) {
      background: $primary-dark;
      transform: translateY(-1px); // Micro-interacción
      box-shadow: $box-shadow-lg;
    }
  }
}
```
**Variantes de Botones**:
- `btn--primary`: Amarillo Pichincha (acciones principales)
- `btn--secondary`: Azul (acciones secundarias)
- `btn--success`: Verde (confirmaciones)
- `btn--danger`: Rojo (eliminaciones)
- `btn--outline`: Transparente con borde

### 9. Sistema de Formularios
```scss
.form-input, input[type="text"], input[type="email"], /* ... */ {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 2px solid $color-border;
  border-radius: $border-radius;
  font-size: 0.875rem;
  transition: all 0.2s ease;

  &:focus {
    outline: none;
    border-color: $secondary;
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1); // Focus ring
  }
}
```

### 10. Sistema de Tablas
```scss
.data-table {
  width: 100%;
  border-collapse: collapse;
  background: $card-bg;
  
  &__header {
    background: #f8fafc;       // Fondo gris claro para headers
    
    th {
      padding: 1rem;
      text-align: left;
      font-weight: 600;
      border-bottom: 1px solid $color-border;
    }
  }
  
  &__body {
    tr {
      border-bottom: 1px solid #f3f4f6;
      transition: background-color 0.2s;
      
      &:hover {
        background: #f9fafb;    // Hover effect
      }
    }
  }
}
```

### 11. Sistema de Alertas
```scss
.alert {
  display: flex;
  align-items: flex-start;
  gap: 0.75rem;
  padding: 1rem;
  border-radius: $border-radius;
  border-left: 4px solid;      // Borde izquierdo identificativo
  
  &--success {
    background: #f0fdf4;
    border-color: $success;
    color: #166534;
  }
  
  &--error, &--danger {
    background: #fef2f2;
    border-color: $danger;
    color: #991b1b;
  }
}
```

### 12. Utilidades Globales
```scss
/* Clases de utilidad reutilizables */
.text-center { text-align: center; }
.text-right { text-align: right; }
.font-semibold { font-weight: 600; }
.text-green-600 { color: $success !important; }
.text-red-600 { color: $danger !important; }
.hidden { display: none; }
```

## 📱 Documentación de Componentes Específicos

### Header Component (header.scss)
```scss
.page-header {
  padding: 2rem 2rem 0 2rem;  // Alineado con content__body
  
  &__title {
    font-size: 2rem;
    font-weight: 700;
    color: #1f2937;
    margin: 0;
  }
  
  &__subtitle {
    font-size: 1rem;
    color: #6b7280;
    opacity: 0.8;
  }
}
```
**Propósito**: Encabezado que muestra títulos dinámicos como "Gestión de Clientes", "Gestión de Cuentas", etc.

### Sidebar Component
```scss
.sidebar__item {
  padding: 1rem 1.5rem;
  color: rgba(255, 255, 255, 0.8);
  transition: all 0.2s ease;
  
  &:hover {
    background: rgba(255, 255, 255, 0.1);
    color: white;
  }
  
  &--active {
    background: rgba(251, 191, 36, 0.1); // Amarillo translúcido
    border-left-color: $primary;
    font-weight: 600;
  }
}
```

## 🎯 Patrones de Diseño Implementados

### 1. BEM Methodology
```scss
// Block
.sidebar { }

// Block__Element
.sidebar__item { }
.sidebar__list { }

// Block__Element--Modifier
.sidebar__item--active { }
.btn--primary { }
```

### 2. Consistent Spacing
```scss
// Sistema de espaciado basado en múltiplos de 0.25rem
margin-bottom: 0.25rem;  // 4px
margin-bottom: 0.5rem;   // 8px
margin-bottom: 1rem;     // 16px
margin-bottom: 2rem;     // 32px
```

### 3. Color System
```scss
// Colores semánticos
$success: #10b981;  // Verde para éxito
$warning: #f59e0b;  // Naranja para advertencias
$danger: #ef4444;   // Rojo para errores
```

### 4. Animation & Transitions
```scss
transition: all 0.2s ease;          // Transición estándar
transform: translateY(-1px);        // Micro-interacción hover
animation: spin 1s linear infinite; // Loading spinner
```

## 🔧 Optimizaciones Implementadas

### 1. CSS Custom Properties Integration
```scss
// Variables CSS para componentes dinámicos
--primary-color: #{$primary};
--text-color: #{$text};
```

### 2. Performance Optimizations
- **Lazy Loading**: Estilos por componente
- **Tree Shaking**: Variables no utilizadas eliminadas
- **Compression**: Build optimizado para producción

### 3. Accessibility
```scss
// Focus states visibles
&:focus {
  outline: 2px solid $primary;
  outline-offset: 2px;
}

// High contrast support
@media (prefers-contrast: high) {
  border-width: 2px;
}
```

## 📐 Guidelines de Desarrollo

### 1. Naming Conventions
- **Blocks**: `component-name`
- **Elements**: `component-name__element`
- **Modifiers**: `component-name--modifier`

### 2. File Organization
- Un archivo SCSS por componente
- Variables globales en `app.scss`
- Mixins reutilizables separados

### 3. Performance Best Practices
- Evitar selectores complejos
- Usar transform en lugar de cambiar layout
- Minimizar reflows y repaints

---

Esta documentación te permite entender completamente la arquitectura de estilos, desde las decisiones de diseño hasta la implementación técnica.
