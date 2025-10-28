import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Injectable } from "@angular/core";
import { TicketResponseDTO } from "./ticket.service";
import { EmployeeResponseDTO } from "./employee.service";
import { catchError, map, Observable, of, throwError } from "rxjs";
import { formatDate } from "../utils/date.utils";

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

    private getHeaders(): HttpHeaders {
        const token = localStorage.getItem('authToken');
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
    }

    getAssignmentByTicketId(ticketId: number): Observable<AssignmentRespondeDTO | null> {
        return this.http.get<AssignmentRespondeDTO>(`${this.apiUrl}/ticket/${ticketId}`, { headers: this.getHeaders() }).pipe(
            map(assignment => ({
                ...assignment,
                assignmentDate: formatDate(assignment.assignmentDate) 
            })),
            // Si el assignment no existe, devolvemos null en lugar de un error
            catchError(err => err.status === 404 ? of(null) : throwError(() => err))
        );
    }
}