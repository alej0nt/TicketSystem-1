import { Component, Output, EventEmitter, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CategoryService, CategoryResponseDTO } from '../../../services/category.service';
import { TicketService, TicketCreateDTO } from '../../../services/ticket.service';

@Component({
  selector: 'app-create-ticket-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-ticket-modal.html',
  styleUrl: './create-ticket-modal.css'
})
export class CreateTicketModalComponent implements OnInit {
  @Output() close = new EventEmitter<void>();
  @Output() ticketCreated = new EventEmitter<void>();

  title = signal('');
  description = signal('');
  categoryId = signal<number | null>(null);
  priority = signal('MEDIA');
  
  categories: CategoryResponseDTO[] = [];
  isLoading = false;
  errorMessage = '';

  priorities = [
    { value: 'BAJA', label: 'Baja', color: 'text-green-600' },
    { value: 'MEDIA', label: 'Media', color: 'text-yellow-600' },
    { value: 'ALTA', label: 'Alta', color: 'text-orange-600' },
    { value: 'CRITICA', label: 'Crítica', color: 'text-red-600' }
  ];

  private categoryService: CategoryService = inject(CategoryService);
  private ticketService: TicketService = inject(TicketService);

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
        if (categories.length > 0) {
          this.categoryId.set(categories[0].id);
        }
      },
      error: (error) => {
        console.error('Error loading categories:', error);
        this.errorMessage = 'Error al cargar las categorías';
      }
    });
  }

  handleSubmit(): void {
    if (!this.title() || !this.description() || !this.categoryId()) {
      this.errorMessage = 'Por favor completa todos los campos';
      return;
    }

    const employeeData = localStorage.getItem('employeeData');
    if (!employeeData) {
      this.errorMessage = 'No se encontró información del usuario';
      return;
    }

    const employee = JSON.parse(employeeData);
    
    const ticketData: TicketCreateDTO = {
      employeeId: employee.id,
      title: this.title(),
      description: this.description(),
      categoryId: this.categoryId()!,
      priority: this.priority()
    };

    this.isLoading = true;
    this.errorMessage = '';

    this.ticketService.createTicket(ticketData).subscribe({
      next: () => {
        this.isLoading = false;
        this.ticketCreated.emit();
        this.handleClose();
      },
      error: (error) => {
        console.error('Error creating ticket:', error);
        this.errorMessage = 'Error al crear el ticket. Por favor intenta de nuevo.';
        this.isLoading = false;
      }
    });
  }

  handleClose(): void {
    this.close.emit();
  }
}
