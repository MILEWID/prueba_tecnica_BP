import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { map, catchError, finalize } from 'rxjs/operators';
import { Client, ClientRequest, ClientResponse, ApiResponse } from '../models/client.interface';
@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private readonly baseUrl = 'http://localhost:8081/api/clientes';
  private loadingSubject = new BehaviorSubject<boolean>(false);
  private clientsSubject = new BehaviorSubject<Client[]>([]);
  public loading$ = this.loadingSubject.asObservable();
  public clients$ = this.clientsSubject.asObservable();
  constructor(private http: HttpClient) {}
  getClients(): Observable<Client[]> {
    this.setLoading(true);
    return this.http.get<Client[]>(this.baseUrl).pipe(
      map(clients => {
        this.clientsSubject.next(clients);
        return clients;
      }),
      catchError(this.handleError),
      finalize(() => this.setLoading(false))
    );
  }
  getClientByCedula(cedula: string): Observable<Client | null> {
    if (!cedula.trim()) {
      return throwError(() => new Error('La cédula es requerida'));
    }
    this.setLoading(true);
    const params = new HttpParams().set('cedula', cedula.trim());
    return this.http.get<ApiResponse<Client>>(`${this.baseUrl}/buscar`, { params }).pipe(
      map(response => {
        if (response.success && response.data) {
          return response.data;
        }
        return null;
      }),
      catchError(this.handleError),
      finalize(() => this.setLoading(false))
    );
  }
  createClient(clientData: ClientRequest): Observable<Client> {
    this.setLoading(true);
    return this.http.post<Client>(this.baseUrl, clientData).pipe(
      map(client => {
        this.refreshClientsList();
        return client;
      }),
      catchError(this.handleError),
      finalize(() => this.setLoading(false))
    );
  }
  updateClient(id: string, clientData: ClientRequest): Observable<Client> {
    this.setLoading(true);
    return this.http.put<Client>(`${this.baseUrl}/${id}`, clientData).pipe(
      map(client => {
        this.refreshClientsList();
        return client;
      }),
      catchError(this.handleError),
      finalize(() => this.setLoading(false))
    );
  }
  deleteClient(id: string): Observable<boolean> {
    this.setLoading(true);
    return this.http.delete(`${this.baseUrl}/${id}`).pipe(
      map(() => {
        this.refreshClientsList();
        return true;
      }),
      catchError(this.handleError),
      finalize(() => this.setLoading(false))
    );
  }
  existsByCedula(cedula: string, excludeId?: number): Observable<boolean> {
    const params = new HttpParams()
      .set('cedula', cedula)
      .set('excludeId', excludeId?.toString() || '');
    return this.http.get<ApiResponse<boolean>>(`${this.baseUrl}/exists`, { params }).pipe(
      map(response => response.data || false),
      catchError(() => throwError(() => new Error('Error al validar la cédula')))
    );
  }
  private refreshClientsList(): void {
    this.getClients().subscribe({
      next: () => {},
      error: (error) => console.error('Error al refrescar la lista de clientes:', error)
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
          errorMessage = 'Cliente no encontrado';
          break;
        case 409:
          errorMessage = 'Ya existe un cliente con esa cédula';
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
    console.error('Error en ClientService:', error);
    return throwError(() => new Error(errorMessage));
  };
}
