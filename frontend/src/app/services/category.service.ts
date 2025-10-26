import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";

export interface CategoryResponseDTO {
    id: number;
    name: string;
    description: string;
}

@Injectable({
    providedIn: 'root'
})
export class CategoryService {
    private apiUrl = `${environment.apiBaseURL}/categories`
    constructor(private http: HttpClient) {}

    getAllCategories(): Observable<CategoryResponseDTO[]> {
        const token = localStorage.getItem('authToken');
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
        return this.http.get<CategoryResponseDTO[]>(this.apiUrl, { headers });
    }
}