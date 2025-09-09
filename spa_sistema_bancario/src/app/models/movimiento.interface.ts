export interface Movimiento {
  id?: string;
  fecha: Date;
  cliente: string;
  numeroCuenta: string;
  tipoMovimiento?: string;
  saldoInicial: number;
  valor: number;
  saldo: number;
  estado: boolean;
}

export interface MovimientoDTO {
  id?: string;
  fecha: Date;
  cliente: string;
  numeroCuenta: string;
  saldoInicial: number;
  estado: boolean;
  movimiento: number;
  saldoDisponible: number;
}

export interface MovimientoRequest {
  numeroCuenta: string;
  valor: number;
}

export interface MovimientoOperacionResponse {
  resultado: string;
}

export interface ReporteRequest {
  identificacion: string;
  desde: string;
  hasta: string;
}

export interface ApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
  errors?: string[];
}
