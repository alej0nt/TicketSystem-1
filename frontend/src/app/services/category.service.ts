import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";

export interface CategoryResponseDTO {
    id: number;
    name: string;
    description: string;
}

export class CategoryService {
    private apiUrl = `${environment.apiBaseURL}/categories`
    constructor(private http: HttpClient) {}

}