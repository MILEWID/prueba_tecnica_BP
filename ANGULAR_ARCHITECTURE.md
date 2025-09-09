# Angular Component Architecture - Sistema Bancario

## 🏗️ Arquitectura de Componentes Angular

### Estructura del Proyecto Angular
```
src/app/
├── app.ts                    # Componente raíz
├── app.html                  # Template principal
├── app.scss                  # Estilos globales
├── app.config.ts            # Configuración de la app
├── app.routes.ts            # Rutas de la aplicación
├── components/              # Componentes de UI
│   ├── header/             # Encabezado dinámico
│   ├── sidebar/            # Navegación lateral
│   ├── top-bar/            # Barra superior
│   ├── modal/              # Modal reutilizable
│   ├── clients-card/       # Gestión de clientes
│   ├── cuentas/            # Gestión de cuentas
│   ├── movimientos/        # Gestión de movimientos
│   ├── reportes/           # Sistema de reportes
│   └── footer/             # Pie de página
├── services/               # Servicios de datos
│   ├── client.service.ts
│   ├── cuenta.service.ts
│   └── movimiento.service.ts
├── models/                 # Interfaces TypeScript
│   ├── client.interface.ts
│   ├── cuenta.interface.ts
│   └── movimiento.interface.ts
└── shared/                 # Utilidades compartidas
```

## 🎯 Análisis de Componentes Principales

### 1. App Component (app.ts)
```typescript
@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrls: ['./app.scss'],
  imports: [
    CommonModule,
    TopBarComponent,
    SidebarComponent,
    HeaderComponent,
    ClientsCardComponent,
    CuentasComponent,
    MovimientosComponent,
    ReportesComponent,
    FooterComponent
  ]
})
export class AppComponent {
  section = signal<string>('clientes');      // Sección activa
  title = signal<string>('Clientes');       // Título dinámico
  
  // Manejo de navegación
  onSectionChange(newSection: string) {
    this.section.set(newSection);
    this.title.set(this.getSectionTitle(newSection));
  }
  
  // Mobile menu toggle
  onMobileMenuToggle() {
    // Lógica para mostrar/ocultar sidebar en móvil
  }
  
  private getSectionTitle(section: string): string {
    const titles = {
      'clientes': 'Clientes',
      'cuentas': 'Cuentas',
      'movimientos': 'Movimientos',
      'reportes': 'Reportes'
    };
    return titles[section] || 'Sistema Bancario';
  }
}
```

**Características**:
- **Signals**: Estado reactivo con Angular 17+
- **Standalone**: No requiere NgModule
- **Layout Management**: Controla la estructura principal
- **Navigation State**: Maneja qué sección está activa

### 2. Header Component (header.ts)
```typescript
@Component({
  selector: 'app-header',
  templateUrl: './header.html',
  styleUrls: ['./header.scss']
})
export class HeaderComponent {
  @Input() title!: string;     // Título recibido del componente padre
  
  constructor() {}
}
```

**Template (header.html)**:
```html
<header class="page-header">
  <div class="page-header__info">
    <h1 class="page-header__title">Gestión de {{ title }}</h1>
    <p class="page-header__subtitle">Gestión de {{ title.toLowerCase() }} del usuario</p>
  </div>
</header>
```

**Estilos (header.scss)**:
```scss
.page-header {
  padding: 2rem 2rem 0 2rem;  // Alineado con content__body
  
  &__title {
    font-size: 2rem;
    font-weight: 700;
    color: #1f2937;
  }
  
  &__subtitle {
    font-size: 1rem;
    color: #6b7280;
    opacity: 0.8;
  }
}
```

### 3. Sidebar Component
```typescript
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.html',
  styleUrls: ['./sidebar.scss']
})
export class SidebarComponent {
  @Input() active!: string;                    // Sección activa
  @Output() sectionChange = new EventEmitter<string>();
  
  menuItems = [
    { id: 'clientes', label: 'Clientes', icon: 'people' },
    { id: 'cuentas', label: 'Cuentas', icon: 'account_balance' },
    { id: 'movimientos', label: 'Movimientos', icon: 'swap_horiz' },
    { id: 'reportes', label: 'Reportes', icon: 'assessment' }
  ];
  
  onItemClick(sectionId: string) {
    this.sectionChange.emit(sectionId);
  }
}
```

### 4. Modal Component (Reutilizable)
```typescript
@Component({
  selector: 'app-modal',
  templateUrl: './modal.html',
  styleUrls: ['./modal.scss']
})
export class ModalComponent {
  @Input() isOpen = false;
  @Input() title = '';
  @Input() size: 'small' | 'medium' | 'large' = 'medium';
  @Output() close = new EventEmitter<void>();
  @Output() confirm = new EventEmitter<void>();
  
  onBackdropClick(event: Event) {
    if (event.target === event.currentTarget) {
      this.close.emit();
    }
  }
  
  onConfirm() {
    this.confirm.emit();
  }
}
```

**Template del Modal**:
```html
<div class="modal-backdrop" *ngIf="isOpen" (click)="onBackdropClick($event)">
  <div class="modal__dialog" [class]="'modal__dialog--' + size">
    <div class="modal__header">
      <h3 class="modal__title">{{ title }}</h3>
      <button class="modal__close-button" (click)="close.emit()">
        <i class="material-icons">close</i>
      </button>
    </div>
    
    <div class="modal__body">
      <ng-content></ng-content>  <!-- Contenido proyectado -->
    </div>
    
    <div class="modal__footer" *ngIf="confirm.observed">
      <div class="modal__footer-buttons">
        <button class="btn btn--outline" (click)="close.emit()">Cancelar</button>
        <button class="btn btn--primary" (click)="onConfirm()">Confirmar</button>
      </div>
    </div>
  </div>
</div>
```

### 5. Client Management Component
```typescript
@Component({
  selector: 'app-clients-card',
  templateUrl: './clients-card.html',
  styleUrls: ['./clients-card.scss']
})
export class ClientsCardComponent implements OnInit {
  clients = signal<Client[]>([]);
  isLoading = signal<boolean>(false);
  searchTerm = signal<string>('');
  
  // Modal states
  isModalOpen = signal<boolean>(false);
  modalConfig = signal<ModalConfig | null>(null);
  
  constructor(private clientService: ClientService) {}
  
  ngOnInit() {
    this.loadClients();
  }
  
  async loadClients() {
    this.isLoading.set(true);
    try {
      const clients = await this.clientService.getAll();
      this.clients.set(clients);
    } catch (error) {
      console.error('Error loading clients:', error);
    } finally {
      this.isLoading.set(false);
    }
  }
  
  onSearch(term: string) {
    this.searchTerm.set(term);
    // Filtrar clientes basado en el término de búsqueda
  }
  
  openCreateModal() {
    this.modalConfig.set({
      title: 'Agregar Nuevo Cliente',
      type: 'create',
      data: null
    });
    this.isModalOpen.set(true);
  }
  
  openEditModal(client: Client) {
    this.modalConfig.set({
      title: 'Editar Cliente',
      type: 'edit',
      data: client
    });
    this.isModalOpen.set(true);
  }
  
  openDeleteModal(client: Client) {
    this.modalConfig.set({
      title: 'Eliminar Cliente',
      type: 'delete',
      data: client
    });
    this.isModalOpen.set(true);
  }
}
```

## 🔧 Servicios de Datos

### Client Service
```typescript
@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = 'http://localhost:8080/api/clientes';
  
  constructor(private http: HttpClient) {}
  
  getAll(): Observable<Client[]> {
    return this.http.get<Client[]>(this.apiUrl);
  }
  
  create(client: Partial<Client>): Observable<Client> {
    return this.http.post<Client>(this.apiUrl, client);
  }
  
  update(id: number, client: Partial<Client>): Observable<Client> {
    return this.http.put<Client>(`${this.apiUrl}/${id}`, client);
  }
  
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  
  // Métodos de búsqueda y filtrado
  search(term: string): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.apiUrl}/search?q=${term}`);
  }
}
```

## 📊 Interfaces y Modelos

### Client Interface
```typescript
export interface Client {
  clienteId: number;
  nombre: string;
  genero: 'M' | 'F';
  edad: number;
  identificacion: string;
  direccion: string;
  telefono: string;
  contrasena: string;
  estado: boolean;
  fechaCreacion?: Date;
  fechaActualizacion?: Date;
}

export interface CreateClientRequest {
  nombre: string;
  genero: 'M' | 'F';
  edad: number;
  identificacion: string;
  direccion: string;
  telefono: string;
  contrasena: string;
}
```

### Cuenta Interface
```typescript
export interface Cuenta {
  cuentaId: number;
  numeroCuenta: string;
  tipoCuenta: 'AHORROS' | 'CORRIENTE';
  saldoInicial: number;
  saldoDisponible: number;
  estado: boolean;
  clienteId: number;
  clienteNombre?: string;
  fechaCreacion?: Date;
}
```

### Movimiento Interface
```typescript
export interface Movimiento {
  movimientoId: number;
  fecha: Date;
  tipoMovimiento: 'DEPOSITO' | 'RETIRO';
  valor: number;
  saldo: number;
  cuentaId: number;
  numeroCuenta?: string;
  clienteNombre?: string;
}
```

## 🎨 Patrones de Diseño Implementados

### 1. Container/Presentational Pattern
```typescript
// Container Component (Smart Component)
@Component({
  template: `
    <app-client-list 
      [clients]="clients()"
      [isLoading]="isLoading()"
      (onCreate)="openCreateModal()"
      (onEdit)="openEditModal($event)"
      (onDelete)="openDeleteModal($event)">
    </app-client-list>
  `
})
export class ClientContainerComponent {
  // Lógica de estado y operaciones
}

// Presentational Component (Dumb Component)
@Component({
  template: `<!-- UI pura sin lógica de negocio -->`
})
export class ClientListComponent {
  @Input() clients!: Client[];
  @Input() isLoading!: boolean;
  @Output() onCreate = new EventEmitter<void>();
  @Output() onEdit = new EventEmitter<Client>();
  @Output() onDelete = new EventEmitter<Client>();
}
```

### 2. Service Layer Pattern
```typescript
// Separación de responsabilidades
export class ComponentService {
  // Solo lógica de presentación
}

export class DataService {
  // Solo acceso a datos
}

export class BusinessService {
  // Solo lógica de negocio
}
```

### 3. Observable Pattern
```typescript
// Manejo reactivo de datos
export class ReactiveComponent {
  data$ = this.service.getData();
  filteredData$ = combineLatest([
    this.data$,
    this.searchTerm$
  ]).pipe(
    map(([data, term]) => this.filterData(data, term))
  );
}
```

## 🚀 Optimizaciones de Performance

### 1. OnPush Change Detection
```typescript
@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  // ...resto del componente
})
export class OptimizedComponent {
  // Angular solo detecta cambios cuando:
  // 1. Inputs cambian
  // 2. Event emitters se disparan
  // 3. Manualmente se ejecuta detectChanges()
}
```

### 2. TrackBy Functions
```typescript
// En el template
<tr *ngFor="let item of items; trackBy: trackByFn">

// En el componente
trackByFn(index: number, item: any): any {
  return item.id; // Usar ID único para tracking
}
```

### 3. Lazy Loading
```typescript
// En app.routes.ts
export const routes: Routes = [
  {
    path: 'reports',
    loadComponent: () => import('./components/reportes/reportes.component')
      .then(c => c.ReportesComponent)
  }
];
```

## 🧪 Testing Estratégico

### Unit Testing
```typescript
describe('ClientsCardComponent', () => {
  let component: ClientsCardComponent;
  let fixture: ComponentFixture<ClientsCardComponent>;
  let mockClientService: jasmine.SpyObj<ClientService>;

  beforeEach(() => {
    const spy = jasmine.createSpyObj('ClientService', ['getAll', 'create', 'update', 'delete']);
    
    TestBed.configureTestingModule({
      imports: [ClientsCardComponent],
      providers: [
        { provide: ClientService, useValue: spy }
      ]
    });

    fixture = TestBed.createComponent(ClientsCardComponent);
    component = fixture.componentInstance;
    mockClientService = TestBed.inject(ClientService) as jasmine.SpyObj<ClientService>;
  });

  it('should load clients on init', () => {
    mockClientService.getAll.and.returnValue(of([]));
    
    component.ngOnInit();
    
    expect(mockClientService.getAll).toHaveBeenCalled();
    expect(component.clients()).toEqual([]);
  });
});
```

## 📱 Responsive Design

### Component-Level Responsive Logic
```typescript
export class ResponsiveComponent implements OnInit, OnDestroy {
  isMobile = signal<boolean>(false);
  private mediaQuery = window.matchMedia('(max-width: 768px)');
  
  ngOnInit() {
    this.isMobile.set(this.mediaQuery.matches);
    this.mediaQuery.addEventListener('change', this.onMediaQueryChange);
  }
  
  private onMediaQueryChange = (e: MediaQueryListEvent) => {
    this.isMobile.set(e.matches);
  }
  
  ngOnDestroy() {
    this.mediaQuery.removeEventListener('change', this.onMediaQueryChange);
  }
}
```

---

Esta documentación te proporciona una visión completa de la arquitectura de componentes Angular, desde los patrones de diseño hasta las optimizaciones de performance implementadas en el sistema bancario.
