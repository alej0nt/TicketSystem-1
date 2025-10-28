import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { CategoryResponseDTO } from "./category.service";
import { formatDate } from "../utils/date.utils";

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

    private getHeaders(): HttpHeaders {
        const token = localStorage.getItem('authToken');
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
    }

    getTicket(ticketId: number): Observable<TicketResponseDTO> {
        return this.http.get<TicketResponseDTO>(`${this.apiUrl}/${ticketId}`, { headers: this.getHeaders() }).pipe(
                    map(ticket => ({
                        ...ticket,
                        creationDate: formatDate(ticket.creationDate) 
                    })));
    }

    getAllTickets(): Observable<TicketResponseDTO[]> {
        return this.http.get<TicketResponseDTO[]>(this.apiUrl, { headers: this.getHeaders() });
    }

    createTicket(ticketData: TicketCreateDTO): Observable<TicketResponseDTO> {

        return this.http.post<TicketResponseDTO>(this.apiUrl, ticketData, { headers: this.getHeaders() });
    }

    updateTicketState(ticketId: number, newState: string): Observable<TicketResponseDTO> {
        return this.http.put<TicketResponseDTO>(`${this.apiUrl}/${ticketId}/state`, { state: newState }, { headers: this.getHeaders() });
    }
    deleteTicket(ticketId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${ticketId}`, { headers: this.getHeaders() });
    }
}