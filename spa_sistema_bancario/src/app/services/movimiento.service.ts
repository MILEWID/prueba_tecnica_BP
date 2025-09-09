import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { 
  Movimiento, 
  MovimientoDTO, 
  MovimientoRequest, 
  MovimientoOperacionResponse,
  ReporteRequest,
  ApiResponse 
} from '../models/movimiento.interface';

@Injectable({
  providedIn: 'root'
})
export class MovimientoService {
  private apiUrl = 'http://localhost:8081/api/movimientos';

  constructor(private http: HttpClient) {}

  // Obtener todos los movimientos
  getMovimientos(): Observable<MovimientoDTO[]> {
    return this.http.get<MovimientoDTO[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  // Realizar una operación (depósito o retiro)
  realizarMovimiento(numeroCuenta: string, valor: number): Observable<MovimientoOperacionResponse> {
    const params = new HttpParams()
      .set('numeroCuenta', numeroCuenta)
      .set('movimiento', valor.toString());

    return this.http.post<{ resultado: string }>(`${this.apiUrl}/operacion`, null, { params }).pipe(
      map(response => ({ resultado: response.resultado })),
      catchError(this.handleError)
    );
  }

  // Eliminar movimiento
  deleteMovimiento(id: string): Observable<{ resultado: string }> {
    return this.http.delete<{ resultado: string }>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener reporte de movimientos
  obtenerReporte(identificacion: string, desde: string, hasta: string): Observable<MovimientoDTO[]> {
    const params = new HttpParams()
      .set('identificacion', identificacion)
      .set('desde', desde)
      .set('hasta', hasta);

    return this.http.get<MovimientoDTO[]>(`${this.apiUrl}/reporte`, { params }).pipe(
      catchError(this.handleError)
    );
  }

  // Generar reporte PDF
  generarReportePdf(identificacion: string, desde: string, hasta: string): Observable<{ resultado: string }> {
    const params = new HttpParams()
      .set('identificacion', identificacion)
      .set('desde', desde)
      .set('hasta', hasta);

    return this.http.get<{ resultado: string }>(`${this.apiUrl}/reporte/pdf`, { params }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Ha ocurrido un error desconocido';
    
    if (error.error instanceof ErrorEvent) {
      // Error del lado del cliente
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Error del lado del servidor
      if (error.error?.message) {
        errorMessage = error.error.message;
      } else if (error.error?.details) {
        errorMessage = error.error.details;
      } else {
        errorMessage = `Error ${error.status}: ${error.message}`;
      }
    }
    
    return throwError(() => new Error(errorMessage));
  }
}
