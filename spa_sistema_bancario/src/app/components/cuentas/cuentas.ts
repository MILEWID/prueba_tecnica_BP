import { Component, OnInit, ViewChild, Input, signal, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Cuenta, CuentaRequest } from '../../models/cuenta.interface';
import { CuentaService } from '../../services/cuenta.service';
import { Modal } from '../modal/modal';
import { CuentaForm } from '../cuenta-form/cuenta-form';

interface ModalConfig {
  title: string;
  size?: 'small' | 'medium' | 'large';
  showCloseButton?: boolean;
  showFooter?: boolean;
  closeOnBackdropClick?: boolean;
  customClass?: string;
}

@Component({
  selector: 'app-cuentas',
  standalone: true,
  imports: [CommonModule, FormsModule, Modal, CuentaForm],
  templateUrl: './cuentas.html',
  styleUrl: './cuentas.scss',
  encapsulation: ViewEncapsulation.None
})
export class Cuentas implements OnInit {
  @Input() section: string = '';
  @ViewChild('createCuentaForm') createCuentaForm!: CuentaForm;
  @ViewChild('editCuentaForm') editCuentaForm!: CuentaForm;

  protected cuentas = signal<Cuenta[]>([]);
  protected filteredCuentas = signal<Cuenta[]>([]);
  protected searchTerm = signal<string>('');
  protected isLoading = signal<boolean>(false);
  protected error = signal<string>('');
  protected successMessage = signal<string>('');
  protected isCreateModalOpen = signal<boolean>(false);
  protected isEditModalOpen = signal<boolean>(false);
  protected isDeleteModalOpen = signal<boolean>(false);
  protected selectedCuenta = signal<Cuenta | null>(null);

  protected readonly createModalConfig: ModalConfig = {
    title: 'Agregar Nueva Cuenta',
    showFooter: false,
    showCloseButton: true
  };

  protected readonly editModalConfig: ModalConfig = {
    title: 'Editar Cuenta',
    showFooter: false,
    showCloseButton: true
  };

  protected readonly deleteModalConfig: ModalConfig = {
    title: 'Confirmar Eliminación',
    showFooter: true,
    showCloseButton: true,
    closeOnBackdropClick: false
  };

  constructor(private cuentaService: CuentaService) {}

  ngOnInit(): void {
    this.loadCuentas();
  }

  protected loadCuentas(): void {
    this.cuentaService.getCuentas().subscribe({
      next: (cuentas: Cuenta[]) => {
        this.cuentas.set(cuentas);
        this.filterCuentas();
      },
      error: (error: any) => {
        this.error.set(error.message || 'Error al cargar las cuentas');
      }
    });
  }

  protected onSearchChange(term: string): void {
    this.searchTerm.set(term);
    this.filterCuentas();
  }

  protected clearSearch(): void {
    this.searchTerm.set('');
    this.filterCuentas();
  }

  private filterCuentas(): void {
    const term = this.searchTerm().toLowerCase().trim();
    if (!term) {
      this.filteredCuentas.set(this.cuentas());
      return;
    }

    const filtered = this.cuentas().filter((cuenta: Cuenta) => 
      cuenta.numeroCuenta.toLowerCase().includes(term) ||
      (cuenta.clienteNombre && cuenta.clienteNombre.toLowerCase().includes(term)) ||
      cuenta.clienteIdentificacion.toLowerCase().includes(term) ||
      cuenta.tipoCuenta.toLowerCase().includes(term)
    );
    this.filteredCuentas.set(filtered);
  }

  protected openCreateModal(): void {
    this.isCreateModalOpen.set(true);
  }

  protected closeCreateModal(): void {
    this.isCreateModalOpen.set(false);
  }

  protected openEditModal(cuenta: Cuenta): void {
    this.selectedCuenta.set(cuenta);
    this.isEditModalOpen.set(true);
  }

  protected closeEditModal(): void {
    this.isEditModalOpen.set(false);
    this.selectedCuenta.set(null);
  }

  protected openDeleteModal(cuenta: Cuenta): void {
    this.selectedCuenta.set(cuenta);
    this.isDeleteModalOpen.set(true);
  }

  protected closeDeleteModal(): void {
    this.isDeleteModalOpen.set(false);
    this.selectedCuenta.set(null);
  }

  protected onCreateCuenta(cuentaData: CuentaRequest): void {
    this.cuentaService.createCuenta(cuentaData).subscribe({
      next: (newCuenta: Cuenta) => {
        this.successMessage.set('Cuenta creada exitosamente');
        this.closeCreateModal();
        this.loadCuentas();
        if (this.createCuentaForm) {
          this.createCuentaForm.setSubmitting(false);
        }
      },
      error: (error: any) => {
        this.error.set(error.message || 'Error al crear la cuenta');
        if (this.createCuentaForm) {
          this.createCuentaForm.setSubmitting(false);
        }
      }
    });
  }

  protected onEditCuenta(cuentaData: CuentaRequest): void {
    const cuenta = this.selectedCuenta();
    if (!cuenta?.id) return;

    this.cuentaService.updateCuenta(cuenta.id, cuentaData).subscribe({
      next: (updatedCuenta: Cuenta) => {
        this.successMessage.set('Cuenta actualizada exitosamente');
        this.closeEditModal();
        this.loadCuentas();
        if (this.editCuentaForm) {
          this.editCuentaForm.setSubmitting(false);
        }
      },
      error: (error: any) => {
        this.error.set(error.message || 'Error al actualizar la cuenta');
        if (this.editCuentaForm) {
          this.editCuentaForm.setSubmitting(false);
        }
      }
    });
  }

  protected confirmDelete(): void {
    const cuenta = this.selectedCuenta();
    if (!cuenta?.id) return;

    this.cuentaService.deleteCuenta(cuenta.id).subscribe({
      next: () => {
        this.successMessage.set('Cuenta eliminada exitosamente');
        this.closeDeleteModal();
        this.loadCuentas();
      },
      error: (error: any) => {
        this.error.set(error.message || 'Error al eliminar la cuenta');
      }
    });
  }

  protected formatDate(dateString: string): string {
    if (!dateString) return '';
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
      });
    } catch {
      return dateString;
    }
  }

  protected trackByCuentaId(index: number, cuenta: Cuenta): any {
    return cuenta.id;
  }

  protected clearMessages(): void {
    this.error.set('');
    this.successMessage.set('');
  }

  protected getEstadoBadgeClass(cuenta: Cuenta): string {
    return cuenta.estado ? 'badge--success' : 'badge--danger';
  }

  protected getEstadoText(cuenta: Cuenta): string {
    return cuenta.estado ? 'Activa' : 'Inactiva';
  }
}
