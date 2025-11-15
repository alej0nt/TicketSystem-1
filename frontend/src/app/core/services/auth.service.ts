import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {EmployeeResponseDTO} from './employee.service';

export interface jwtResponseDTO {
  token: string;
  type: string;
  employee: EmployeeResponseDTO;
}

export interface loginRequestDTO{
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService{
  private apiUrl = `${environment.apiBaseURL}/auth/login`;

  constructor(private http: HttpClient) {}

  login(loginRequestDTO: loginRequestDTO): Observable<jwtResponseDTO> {
    return this.http.post<jwtResponseDTO>(this.apiUrl , loginRequestDTO)
  }
}
