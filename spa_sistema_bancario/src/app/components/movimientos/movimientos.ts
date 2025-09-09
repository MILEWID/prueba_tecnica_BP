import { Component, OnInit, ViewChild, Input, signal, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MovimientoDTO, MovimientoRequest } from '../../models/movimiento.interface';
import { MovimientoService } from '../../services/movimiento.service';
import { Modal } from '../modal/modal';
import { MovimientoForm } from '../movimiento-form/movimiento-form';

interface ModalConfig {
  title: string;
  size?: 'small' | 'medium' | 'large';
  showCloseButton?: boolean;
  showFooter?: boolean;
  closeOnBackdropClick?: boolean;
  customClass?: string;
}

@Component({
  selector: 'app-movimientos',
  standalone: true,
  imports: [CommonModule, FormsModule, Modal, MovimientoForm],
  templateUrl: './movimientos.html',
  styleUrl: './movimientos.scss',
  encapsulation: ViewEncapsulation.None
})
export class Movimientos implements OnInit {
  @Input() section: string = '';
  @ViewChild('movimientoForm') movimientoForm!: MovimientoForm;

  protected movimientos = signal<MovimientoDTO[]>([]);
  protected filteredMovimientos = signal<MovimientoDTO[]>([]);
  protected searchTerm = signal<string>('');
  protected isLoading = signal<boolean>(false);
  protected error = signal<string>('');
  protected successMessage = signal<string>('');
  protected isCreateModalOpen = signal<boolean>(false);
  protected isDeleteModalOpen = signal<boolean>(false);
  protected selectedMovimiento = signal<MovimientoDTO | null>(null);

  protected readonly createModalConfig: ModalConfig = {
    title: 'Realizar Nuevo Movimiento',
    showFooter: false,
    showCloseButton: true
  };

  protected readonly deleteModalConfig: ModalConfig = {
    title: 'Confirmar Eliminación',
    showFooter: true,
    showCloseButton: true,
    closeOnBackdropClick: false
  };

  constructor(private movimientoService: MovimientoService) {}

  ngOnInit(): void {
    this.loadMovimientos();
  }

  protected loadMovimientos(): void {
    this.isLoading.set(true);
    this.movimientoService.getMovimientos().subscribe({
      next: (movimientos: MovimientoDTO[]) => {
        this.movimientos.set(movimientos);
        this.filterMovimientos();
        this.isLoading.set(false);
      },
      error: (error: any) => {
        this.error.set(error.message || 'Error al cargar los movimientos');
        this.isLoading.set(false);
      }
    });
  }

  protected onSearchChange(term: string): void {
    this.searchTerm.set(term);
    this.filterMovimientos();
  }

  protected clearSearch(): void {
    this.searchTerm.set('');
    this.filterMovimientos();
  }

  private filterMovimientos(): void {
    const term = this.searchTerm().toLowerCase().trim();
    if (!term) {
      this.filteredMovimientos.set(this.movimientos());
      return;
    }

    const filtered = this.movimientos().filter((movimiento: MovimientoDTO) => 
      movimiento.numeroCuenta.toLowerCase().includes(term) ||
      (movimiento.cliente && movimiento.cliente.toLowerCase().includes(term)) ||
      movimiento.movimiento.toString().includes(term)
    );
    this.filteredMovimientos.set(filtered);
  }

  protected openCreateModal(): void {
    this.isCreateModalOpen.set(true);
  }

  protected closeCreateModal(): void {
    this.isCreateModalOpen.set(false);
  }

  protected openDeleteModal(movimiento: MovimientoDTO): void {
    this.selectedMovimiento.set(movimiento);
    this.isDeleteModalOpen.set(true);
  }

  protected closeDeleteModal(): void {
    this.isDeleteModalOpen.set(false);
    this.selectedMovimiento.set(null);
  }

  protected onCreateMovimiento(movimientoData: MovimientoRequest): void {
    this.movimientoService.realizarMovimiento(movimientoData.numeroCuenta, movimientoData.valor).subscribe({
      next: (response) => {
        this.successMessage.set(response.resultado);
        this.closeCreateModal();
        this.loadMovimientos();
        if (this.movimientoForm) {
          this.movimientoForm.setSubmitting(false);
        }
      },
      error: (error: any) => {
        this.error.set(error.message || 'Error al realizar el movimiento');
        if (this.movimientoForm) {
          this.movimientoForm.setSubmitting(false);
        }
      }
    });
  }

  protected confirmDelete(): void {
    const movimiento = this.selectedMovimiento();
    if (!movimiento?.id) return;

    this.movimientoService.deleteMovimiento(movimiento.id).subscribe({
      next: (response) => {
        this.successMessage.set(response.resultado);
        this.closeDeleteModal();
        this.loadMovimientos();
      },
      error: (error: any) => {
        this.error.set(error.message || 'Error al eliminar el movimiento');
      }
    });
  }

  protected formatDate(date: Date | string): string {
    if (!date) return '';
    try {
      const dateObj = typeof date === 'string' ? new Date(date) : date;
      return dateObj.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      });
    } catch {
      return date.toString();
    }
  }

  protected getMovimientoTypeClass(valor: number): string {
    return valor >= 0 ? 'text-green-600' : 'text-red-600';
  }

  protected getMovimientoType(valor: number): string {
    return valor >= 0 ? 'Depósito' : 'Retiro';
  }

  protected getEstadoBadgeClass(estado: boolean): string {
    return estado ? 'badge--success' : 'badge--danger';
  }

  protected getEstadoText(estado: boolean): string {
    return estado ? 'Activo' : 'Inactivo';
  }

  protected trackByMovimientoId(index: number, movimiento: MovimientoDTO): any {
    return movimiento.id;
  }

  protected clearMessages(): void {
    this.error.set('');
    this.successMessage.set('');
  }
}
