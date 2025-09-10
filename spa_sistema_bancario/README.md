# SpaSistemaBancario (Frontend)

Sistema bancario SPA desarrollado en Angular 20.2.2. Permite la gestión de clientes, cuentas y movimientos bancarios, así como la generación de reportes y exportación en PDF. El frontend consume una API REST y está diseñado para ser escalable, mantenible y fácil de usar.

## Estructura de carpetas principal

- `src/app/components/`: Componentes reutilizables (formularios, modales, barra lateral, etc.)
- `src/app/models/`: Interfaces TypeScript para tipado fuerte (Cliente, Cuenta, Movimiento)
- `src/app/pages/`: Vistas principales (Clientes, Cuentas, Movimientos, Reportes)
- `src/app/services/`: Servicios para comunicación con la API REST
- `src/app/app.*`: Configuración y bootstrap de la aplicación

## Instalación y ejecución

1. Instala dependencias:
   ```bash
   npm install
   ```
2. Ejecuta el servidor de desarrollo:
   ```bash
   ng serve
   ```
3. Accede a la app en [http://localhost:4200](http://localhost:4200)

## Funcionalidades principales

- **Clientes:** Alta, edición y listado de clientes.
- **Cuentas:** Creación y gestión de cuentas bancarias asociadas a clientes.
- **Movimientos:** Registro de depósitos/retiros, consulta de historial, eliminación de movimientos.
- **Reportes:** Generación de reportes filtrados por cliente y rango de fechas, exportación a PDF.

## Buenas prácticas implementadas

- Tipado estricto con interfaces y DTOs.
- Componentes desacoplados y reutilizables.
- Servicios centralizados para acceso a datos.
- Manejo de estados y mensajes de error/success reactivos.
- Validaciones de formularios y feedback visual.
- Uso de Angular Signals para gestión eficiente de estado.
- Estilos modulares y responsivos (SCSS).

## Testing

- Unit tests con Jest y Angular Testing Utilities.
- E2E tests recomendados (no incluidos por defecto).

## Recomendaciones para desarrollo

- Usa `ng generate` para crear nuevos componentes, servicios, etc.
- Mantén la lógica de negocio en los servicios y la presentación en los componentes.
- Revisa los modelos en `src/app/models/` antes de consumir/crear datos.
- Para nuevas integraciones, sigue el patrón de servicios y tipado estricto.

## Integración con backend

- El frontend espera una API REST en `http://localhost:8081/api/`.
- Revisa los DTOs y endpoints en el backend para asegurar compatibilidad.

## Recursos útiles

- [Angular CLI Reference](https://angular.dev/tools/cli)
- [Angular Signals](https://angular.dev/reference/signals)
- [Jest Testing](https://jestjs.io/)

---

