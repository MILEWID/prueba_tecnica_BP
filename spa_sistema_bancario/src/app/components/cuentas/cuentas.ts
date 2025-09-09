import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
interface Cuenta {
  numero: string;
  cliente: string;
  tipo: string;
  saldo: number;
  estado: 'activa' | 'pendiente' | 'cerrada';
}
@Component({
  selector: 'app-cuentas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cuentas.html',
  styleUrl: './cuentas.scss'
})
export class Cuentas {
  cuentas: Cuenta[] = [
    {
      numero: '478758',
      cliente: 'Jose Lema',
      tipo: 'Corriente',
      saldo: 2000.00,
      estado: 'activa'
    },
    {
      numero: '225487',
      cliente: 'Marianela Montalvo',
      tipo: 'Ahorros',
      saldo: 3000.00,
      estado: 'activa'
    },
    {
      numero: '495878',
      cliente: 'Juan Osorio',
      tipo: 'Ahorros',
      saldo: 0.00,
      estado: 'cerrada'
    },
    {
      numero: '496825',
      cliente: 'Montserrat Sanchez',
      tipo: 'Ahorros',
      saldo: 0.00,
      estado: 'pendiente'
    }
  ];
}
