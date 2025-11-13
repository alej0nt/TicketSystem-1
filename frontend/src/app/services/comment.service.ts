import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { EmployeeResponseDTO } from "./employee.service";
import { map, Observable } from "rxjs";
import { formatDate } from "../utils/date.utils";

export interface CommentResponseDTO {
    id: number;
    employee: EmployeeResponseDTO;
    text: string;
    creationDate: string;
}

interface CommentCreateDTO {
    ticketId: number;
    employeeId: number;
    text: string;
}
@Injectable({
    providedIn: "root",
})
export class CommentService {
    private apiUrl = `${environment.apiBaseURL}/comments`;
    constructor(private http: HttpClient) { }

    private getHeaders(): HttpHeaders {
        const token = localStorage.getItem('authToken');
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
    }
    getCommentsByTicketId(ticketId: number): Observable<CommentResponseDTO[]> {
        return this.http.get<CommentResponseDTO[]>(`${this.apiUrl}/ticket/${ticketId}`, { headers: this.getHeaders() })
    }

    createComment(commentCreateDTO: CommentCreateDTO): Observable<CommentResponseDTO> {
        return this.http.post<CommentResponseDTO>(this.apiUrl, commentCreateDTO, { headers: this.getHeaders() });
    }
}