import { Component, inject, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

export interface TicketCardData {
  id: number;
  title: string;
  description: string;
  priority: string;
  state: string;
  category: string;
  creationDate: string;
}

@Component({
  selector: 'app-ticket-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ticket-card.html',
  styleUrl: './ticket-card.css'
})
export class TicketCardComponent {
  @Input() ticket!: TicketCardData;

  private router: Router = inject(Router);

  navigateToTicket(): void {
    this.router.navigate(['/ticket', this.ticket.id]);
  }

  getStateColor(state: string): string {
    const stateColors: { [key: string]: string } = {
      'ABIERTO': 'bg-blue-100 text-blue-700 border-blue-300',
      'EN_PROCESO': 'bg-yellow-100 text-yellow-700 border-yellow-300',
      'RESUELTO': 'bg-green-100 text-green-700 border-green-300',
      'CERRADO': 'bg-gray-100 text-gray-700 border-gray-300'
    };
    return stateColors[state] || 'bg-gray-100 text-gray-700 border-gray-300';
  }

  getPriorityColor(priority: string): string {
    const priorityColors: { [key: string]: string } = {
      'BAJA': 'bg-green-100 text-green-700 border-green-300',
      'MEDIA': 'bg-yellow-100 text-yellow-700 border-yellow-300',
      'ALTA': 'bg-orange-100 text-orange-700 border-orange-300',
      'CRITICA': 'bg-red-100 text-red-700 border-red-300'
    };
    return priorityColors[priority] || 'bg-gray-100 text-gray-700 border-gray-300';
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-ES', { 
      year: 'numeric', 
      month: 'short', 
      day: 'numeric' 
    });
  }
}
