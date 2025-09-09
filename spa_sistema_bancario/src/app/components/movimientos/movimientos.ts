import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
interface Movimiento {
  fecha: Date;
  cuenta: string;
  cliente: string;
  tipo: 'Deposito' | 'Retiro';
  valor: number;
  saldo: number;
  estado: 'completado' | 'pendiente' | 'fallido';
}
@Component({
  selector: 'app-movimientos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './movimientos.html',
  styleUrl: './movimientos.scss'
})
export class Movimientos {
  movimientos: Movimiento[] = [
    {
      fecha: new Date('2023-02-10'),
      cuenta: '478758',
      cliente: 'Jose Lema',
      tipo: 'Deposito',
      valor: 575.00,
      saldo: 2000.00,
      estado: 'completado'
    },
    {
      fecha: new Date('2023-02-08'),
      cuenta: '225487',
      cliente: 'Marianela Montalvo',
      tipo: 'Deposito',
      valor: 600.00,
      saldo: 3000.00,
      estado: 'completado'
    },
    {
      fecha: new Date('2023-02-08'),
      cuenta: '495878',
      cliente: 'Juan Osorio',
      tipo: 'Retiro',
      valor: -540.00,
      saldo: 0.00,
      estado: 'completado'
    },
    {
      fecha: new Date('2023-02-08'),
      cuenta: '496825',
      cliente: 'Montserrat Sanchez',
      tipo: 'Retiro',
      valor: -2000.00,
      saldo: 0.00,
      estado: 'fallido'
    }
  ];
}
