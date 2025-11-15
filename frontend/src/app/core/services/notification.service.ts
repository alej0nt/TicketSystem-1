import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface NotificationResponseDTO {
  id: number;
  employeeId: number;
  message: string;
  isRead: boolean;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private apiUrl = `${environment.apiBaseURL}/notifications`;

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getNotificationsByEmployee(employeeId: number): Observable<NotificationResponseDTO[]> {
    return this.http.get<NotificationResponseDTO[]>(
      `${this.apiUrl}/employee/${employeeId}`, 
      { headers: this.getHeaders() }
    );
  }

  markAsRead(notificationId: number): Observable<NotificationResponseDTO> {
    return this.http.patch<NotificationResponseDTO>(
      `${this.apiUrl}/${notificationId}/read`,
      {},
      { headers: this.getHeaders() }
    );
  }
}
