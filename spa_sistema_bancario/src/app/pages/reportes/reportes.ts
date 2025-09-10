import { Component, OnInit, signal, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MovimientoService } from '../../services/movimiento.service';
import { ClientService } from '../../services/client.service';
import { MovimientoDTO } from '../../models/movimiento.interface';
import { Client } from '../../models/client.interface';

interface ReporteResumen {
  totalIngresos: number;
  totalEgresos: number;
  saldoFinal: number;
}

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reportes.html',
  styleUrl: './reportes.scss',
  encapsulation: ViewEncapsulation.None
})
export class Reportes implements OnInit {
  protected clientes = signal<Client[]>([]);
  protected identificacionSeleccionada = signal<string>('');
  protected fechaDesde = signal<string>('');
  protected fechaHasta = signal<string>('');
  protected movimientosReporte = signal<MovimientoDTO[]>([]);
  protected resumenReporte = signal<ReporteResumen>({
    totalIngresos: 0,
    totalEgresos: 0,
    saldoFinal: 0
  });
  protected reporteGenerado = signal<boolean>(false);
  protected isLoading = signal<boolean>(false);
  protected error = signal<string>('');
  protected successMessage = signal<string>('');
  protected isGeneratingPdf = signal<boolean>(false);

  constructor(
    private movimientoService: MovimientoService,
    private clientService: ClientService
  ) {}

  ngOnInit(): void {
    this.loadClientes();
    this.setDefaultDates();
  }

  private loadClientes(): void {
    this.clientService.getClients().subscribe({
      next: (clientes: Client[]) => {
        this.clientes.set(clientes);
      },
      error: (error: any) => {
        this.error.set(error.message || 'Error al cargar los clientes');
      }
    });
  }

  private setDefaultDates(): void {
    const today = new Date();
    const firstDayOfMonth = new Date(today.getFullYear(), today.getMonth(), 1);
    
    this.fechaDesde.set(this.formatDateForInput(firstDayOfMonth));
    this.fechaHasta.set(this.formatDateForInput(today));
  }

  private formatDateForInput(date: Date): string {
    return date.toISOString().split('T')[0];
  }

  protected onClienteChange(identificacion: string): void {
    this.identificacionSeleccionada.set(identificacion);
    if (this.reporteGenerado()) {
      this.reporteGenerado.set(false);
    }
  }

  protected onFechaDesdeChange(fecha: string): void {
    this.fechaDesde.set(fecha);
    if (this.reporteGenerado()) {
      this.reporteGenerado.set(false);
    }
  }

  protected onFechaHastaChange(fecha: string): void {
    this.fechaHasta.set(fecha);
    if (this.reporteGenerado()) {
      this.reporteGenerado.set(false);
    }
  }

  protected generarReporte(): void {
    if (!this.identificacionSeleccionada() || !this.fechaDesde() || !this.fechaHasta()) {
      this.error.set('Por favor complete todos los campos');
      return;
    }

    const desde = new Date(this.fechaDesde());
    const hasta = new Date(this.fechaHasta());
    
    if (desde > hasta) {
      this.error.set('La fecha "desde" no puede ser mayor a la fecha "hasta"');
      return;
    }

    this.isLoading.set(true);
    this.error.set('');

    // Formatear fechas para la API (ISO string)
    const fechaDesdeFormatted = `${this.fechaDesde()}T00:00:00`;
    const fechaHastaFormatted = `${this.fechaHasta()}T23:59:59`;

    this.movimientoService.obtenerReporte(
      this.identificacionSeleccionada(),
      fechaDesdeFormatted,
      fechaHastaFormatted
    ).subscribe({
      next: (movimientos: MovimientoDTO[]) => {
        this.movimientosReporte.set(movimientos);
        this.calcularResumen(movimientos);
        this.reporteGenerado.set(true);
        this.isLoading.set(false);
        this.successMessage.set(`Reporte generado exitosamente con ${movimientos.length} movimientos`);
      },
      error: (error: any) => {
        this.error.set(error.message || 'Error al generar el reporte');
        this.isLoading.set(false);
      }
    });
  }

  private calcularResumen(movimientos: MovimientoDTO[]): void {
    let totalIngresos = 0;
    let totalEgresos = 0;
    let saldoFinal = 0;

    movimientos.forEach((movimiento) => {
      if (movimiento.valor > 0) {
        totalIngresos += movimiento.valor;
      } else {
        totalEgresos += Math.abs(movimiento.valor);
      }
      saldoFinal = movimiento.saldo;
    });

    this.resumenReporte.set({
      totalIngresos,
      totalEgresos,
      saldoFinal
    });
  }

  protected generarReportePdf(): void {
    if (!this.identificacionSeleccionada() || !this.fechaDesde() || !this.fechaHasta()) {
      this.error.set('Por favor complete todos los campos');
      return;
    }

    this.isGeneratingPdf.set(true);
    this.error.set('');

    // Formatear fechas para la API
    const fechaDesdeFormatted = `${this.fechaDesde()}T00:00:00`;
    const fechaHastaFormatted = `${this.fechaHasta()}T23:59:59`;

    this.movimientoService.generarReportePdf(
      this.identificacionSeleccionada(),
      fechaDesdeFormatted,
      fechaHastaFormatted
    ).subscribe({
      next: (response) => {
        // El backend devuelve el PDF como Base64
        this.descargarPdfBase64(response.resultado, this.generarNombreArchivo());
        this.successMessage.set('PDF generado y descargado exitosamente');
        this.isGeneratingPdf.set(false);
      },
      error: (error: any) => {
        this.error.set(error.message || 'Error al generar el reporte PDF');
        this.isGeneratingPdf.set(false);
      }
    });
  }

  private descargarPdfBase64(base64Data: string, fileName: string): void {
    try {
      // Convertir Base64 a blob
      const byteCharacters = atob(base64Data);
      const byteNumbers = new Array(byteCharacters.length);
      
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'application/pdf' });
      
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = fileName;
      document.body.appendChild(link);
      link.click();
      
      document.body.removeChild(link);
      window.URL.revokeObjectURL(link.href);
      
    } catch (error) {
      console.error('Error al descargar PDF:', error);
      this.error.set('Error al procesar el archivo PDF');
    }
  }

  private generarNombreArchivo(): string {
    const clienteNombre = this.getClienteNombre().replace(/\s+/g, '_');
    const fecha = new Date().toISOString().split('T')[0];
    return `reporte_movimientos_${clienteNombre}_${this.fechaDesde()}_${this.fechaHasta()}_${fecha}.pdf`;
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

  protected getMovimientoType(valor: number): string {
    return valor >= 0 ? 'Depósito' : 'Retiro';
  }

  protected getMovimientoTypeClass(valor: number): string {
    return valor >= 0 ? 'badge--success' : 'badge--warning';
  }

  protected getMovimientoTypeIcon(valor: number): string {
    return valor >= 0 ? '↗' : '↘';
  }

  protected getCurrentDate(): Date {
    return new Date();
  }

  protected trackMovimiento(index: number, movimiento: MovimientoDTO): any {
    return movimiento.id || index;
  }

  protected getClienteNombre(): string {
    const cliente = this.clientes().find(c => c.identificacion === this.identificacionSeleccionada());
    return cliente ? cliente.nombre : 'Cliente no encontrado';
  }

  protected limpiarFormulario(): void {
    this.identificacionSeleccionada.set('');
    this.reporteGenerado.set(false);
    this.movimientosReporte.set([]);
    this.setDefaultDates();
    this.clearMessages();
  }

  protected clearMessages(): void {
    this.error.set('');
    this.successMessage.set('');
  }
}
