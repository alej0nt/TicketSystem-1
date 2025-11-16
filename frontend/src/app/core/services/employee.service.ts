import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface EmployeeResponseDTO {
  id: number;
  name: string;
  email: string;
  role: string;
  department: string;
}

export interface EmployeeCreateDTO {
  name: string;
  email: string;
  password: string;
  department: string;
}

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = `${environment.apiBaseURL}/employees`;

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    
    if (!token) {
      console.warn('No token found in localStorage');
      return new HttpHeaders().set('Content-Type', 'application/json');
    }
    
    return new HttpHeaders()
      .set('Authorization', `Bearer ${token}`)
      .set('Content-Type', 'application/json');
  }

  getEmployeeById(id: number): Observable<EmployeeResponseDTO> {
    return this.http.get<EmployeeResponseDTO>(
      `${this.apiUrl}/${id}`, 
      { headers: this.getHeaders() }
    );
  }

  updateEmployee(id: number, employeeData: Partial<EmployeeResponseDTO>): Observable<EmployeeResponseDTO> {
    console.log('Updating employee with token:', localStorage.getItem('authToken')?.substring(0, 20) + '...');
    return this.http.put<EmployeeResponseDTO>(
      `${this.apiUrl}/${id}`, 
      employeeData, 
      { headers: this.getHeaders() }
    );
  }

  getAgents(): Observable<EmployeeResponseDTO[]> {
    const params = new HttpParams().set('role', 'AGENT');
    return this.http.get<EmployeeResponseDTO[]>(
      `${this.apiUrl}`, 
      { headers: this.getHeaders(), params }
    );
  }

  getAllEmployees(): Observable<EmployeeResponseDTO[]> {
    return this.http.get<EmployeeResponseDTO[]>(
      this.apiUrl, 
      { headers: this.getHeaders() }
    );
  }

  createEmployee(payload: EmployeeCreateDTO): Observable<EmployeeResponseDTO> {
    return this.http.post<EmployeeResponseDTO>(
      this.apiUrl, 
      payload, 
      { headers: this.getHeaders() }
    );
  }

  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/${id}`, 
      { headers: this.getHeaders() }
    );
  }

  getEmployeeByEmail(email: string): Observable<EmployeeResponseDTO> {
    return this.http.get<EmployeeResponseDTO>(
      `${this.apiUrl}/email/${encodeURIComponent(email)}`, 
      { headers: this.getHeaders() }
    );
  }
}