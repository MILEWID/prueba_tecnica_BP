export interface Cuenta {
  id?: string;
  clienteNombre?: string;
  clienteIdentificacion: string;
  numeroCuenta: string;
  tipoCuenta: string;
  saldoInicial: number;
  estado: boolean;
}

export interface CuentaResponse {
  data: Cuenta[];
  message: string;
  success: boolean;
  total: number;
}

export interface CuentaRequest {
  clienteIdentificacion: string;
  numeroCuenta: string;
  tipoCuenta: string;
  saldoInicial: number;
  estado: boolean;
}

export interface ApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
  errors?: string[];
}
