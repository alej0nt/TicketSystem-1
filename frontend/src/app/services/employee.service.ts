import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface EmployeeResponseDTO {
  id: number;
  name: string;
  email: string;
  role: string;
  department: string;
}

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = `${environment.apiBaseURL}/employees`;

  constructor(private http: HttpClient) {}

  getEmployeeById(id: number): Observable<EmployeeResponseDTO> {
    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<EmployeeResponseDTO>(`${this.apiUrl}/${id}`, { headers });
  }

  updateEmployee(id: number, employeeData: Partial<EmployeeResponseDTO>): Observable<EmployeeResponseDTO> {
    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<EmployeeResponseDTO>(`${this.apiUrl}/${id}`, employeeData, { headers });
  }
}
