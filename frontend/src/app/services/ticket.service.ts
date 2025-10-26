import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CategoryResponseDTO } from "./category.service";

export interface TicketResponseDTO {
    id: number;
    employeeId: number;
    category: CategoryResponseDTO;
    title: string;
    description: string;
    priority: string;
    state: string;
    creationDate: string;
    closingDate: string;
}

export interface TicketCreateDTO {
    employeeId: number;
    title: string;
    description: string;
    categoryId: number;
    priority: string;
}
@Injectable({
    providedIn: 'root'
})
export class TicketService {
    private apiUrl = `${environment.apiBaseURL}/tickets`
    constructor(private http: HttpClient) { }

    getTicket(ticketId: number): Observable<TicketResponseDTO> {
        const token = localStorage.getItem('authToken');
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
        return this.http.get<TicketResponseDTO>(`${this.apiUrl}/${ticketId}`, { headers });
    }

    getAllTickets(): Observable<TicketResponseDTO[]> {
        const token = localStorage.getItem('authToken');
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
        return this.http.get<TicketResponseDTO[]>(this.apiUrl, { headers });
    }

    createTicket(ticketData: TicketCreateDTO): Observable<TicketResponseDTO> {
        const token = localStorage.getItem('authToken');
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
        return this.http.post<TicketResponseDTO>(this.apiUrl, ticketData, { headers });
    }
}