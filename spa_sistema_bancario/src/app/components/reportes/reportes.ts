import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
interface ReporteDetalle {
  fecha: Date;
  descripcion: string;
  tipo: 'Deposito' | 'Retiro';
  valor: number;
  saldo: number;
}
@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reportes.html',
  styleUrl: './reportes.scss'
})
export class Reportes {
  reporteGenerado = false;
  reporteDetalle: ReporteDetalle[] = [
    {
      fecha: new Date('2023-02-01'),
      descripcion: 'Saldo inicial',
      tipo: 'Deposito',
      valor: 5000.00,
      saldo: 5000.00
    },
    {
      fecha: new Date('2023-02-10'),
      descripcion: 'Depósito por transferencia',
      tipo: 'Deposito',
      valor: 575.00,
      saldo: 5575.00
    },
    {
      fecha: new Date('2023-02-15'),
      descripcion: 'Retiro cajero automático',
      tipo: 'Retiro',
      valor: -575.00,
      saldo: 5000.00
    }
  ];
}
