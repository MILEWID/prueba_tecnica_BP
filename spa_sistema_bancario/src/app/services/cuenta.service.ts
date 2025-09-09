import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { map, catchError, finalize } from 'rxjs/operators';
import { Cuenta, CuentaRequest, CuentaResponse, ApiResponse } from '../models/cuenta.interface';

@Injectable({
  providedIn: 'root'
})
export class CuentaService {
  private readonly baseUrl = 'http://localhost:8081/api/cuentas';
  private loadingSubject = new BehaviorSubject<boolean>(false);
  private cuentasSubject = new BehaviorSubject<Cuenta[]>([]);

  public loading$ = this.loadingSubject.asObservable();
  public cuentas$ = this.cuentasSubject.asObservable();

  constructor(private http: HttpClient) {}

  getCuentas(): Observable<Cuenta[]> {
    this.setLoading(true);
    return this.http.get<Cuenta[]>(this.baseUrl).pipe(
      map(cuentas => {
        this.cuentasSubject.next(cuentas);
        return cuentas;
      }),
      catchError(this.handleError),
      finalize(() => this.setLoading(false))
    );
  }

  getCuentaById(id: string): Observable<Cuenta | null> {
    if (!id.trim()) {
      return throwError(() => new Error('El ID es requerido'));
    }
    this.setLoading(true);
    return this.http.get<Cuenta>(`${this.baseUrl}/${id}`).pipe(
      map(cuenta => cuenta || null),
      catchError(this.handleError),
      finalize(() => this.setLoading(false))
    );
  }

  getCuentasByClienteIdentificacion(identificacion: string): Observable<Cuenta[]> {
    if (!identificacion.trim()) {
      return throwError(() => new Error('La identificación del cliente es requerida'));
    }
    this.setLoading(true);
    return this.http.get<Cuenta[]>(`${this.baseUrl}/cliente/${identificacion}`).pipe(
      map(cuentas => cuentas || []),
      catchError(this.handleError),
      finalize(() => this.setLoading(false))
    );
  }

  createCuenta(cuentaData: CuentaRequest): Observable<Cuenta> {
    this.setLoading(true);
    return this.http.post<Cuenta>(this.baseUrl, cuentaData).pipe(
      map(cuenta => {
        this.refreshCuentasList();
        return cuenta;
      }),
      catchError(this.handleError),
      finalize(() => this.setLoading(false))
    );
  }

  updateCuenta(id: string, cuentaData: CuentaRequest): Observable<Cuenta> {
    this.setLoading(true);
    return this.http.put<Cuenta>(`${this.baseUrl}/${id}`, cuentaData).pipe(
      map(cuenta => {
        this.refreshCuentasList();
        return cuenta;
      }),
      catchError(this.handleError),
      finalize(() => this.setLoading(false))
    );
  }

  deleteCuenta(id: string): Observable<boolean> {
    this.setLoading(true);
    return this.http.delete(`${this.baseUrl}/${id}`).pipe(
      map(() => {
        this.refreshCuentasList();
        return true;
      }),
      catchError(this.handleError),
      finalize(() => this.setLoading(false))
    );
  }

  private refreshCuentasList(): void {
    this.getCuentas().subscribe({
      next: () => {},
      error: (error) => console.error('Error al refrescar la lista de cuentas:', error)
    });
  }

  private setLoading(loading: boolean): void {
    this.loadingSubject.next(loading);
  }

  private handleError = (error: HttpErrorResponse): Observable<never> => {
    let errorMessage = 'Ha ocurrido un error inesperado';
    
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      switch (error.status) {
        case 400:
          errorMessage = error.error?.message || 'Datos inválidos';
          break;
        case 404:
          errorMessage = 'Cuenta no encontrada';
          break;
        case 409:
          errorMessage = 'Ya existe una cuenta con ese número';
          break;
        case 500:
          errorMessage = 'Error interno del servidor';
          break;
        case 0:
          errorMessage = 'No se puede conectar con el servidor. Verifique su conexión.';
          break;
        default:
          errorMessage = error.error?.message || `Error del servidor: ${error.status}`;
      }
    }
    
    console.error('Error en CuentaService:', error);
    return throwError(() => new Error(errorMessage));
  };
}
