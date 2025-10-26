import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Injectable } from "@angular/core";
import { TicketResponseDTO } from "./ticket.service";
import { EmployeeResponseDTO } from "./employee.service";
import { Observable } from "rxjs";

export interface AssignmentRespondeDTO {
    id: number;
    ticket: TicketResponseDTO;
    employee: EmployeeResponseDTO;
    assignmentDate: string;
}

@Injectable({
    providedIn: 'root'
})
export class AssignmentService {
    private apiUrl = `${environment.apiBaseURL}/assignments`;
    constructor(private http: HttpClient) {}

    getAssignmentByTicketId(ticketId: number): Observable<AssignmentRespondeDTO> {
        const token = localStorage.getItem('authToken');
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
        return this.http.get<AssignmentRespondeDTO>(`${this.apiUrl}/ticket/${ticketId}`, { headers });
    }
}