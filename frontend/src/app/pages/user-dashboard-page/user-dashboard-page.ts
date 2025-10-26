import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { DashboardLayoutComponent, UserInfo } from '../../shared/layout/dashboard-layout/dashboard-layout';
import { TicketCardComponent, TicketCardData } from '../../shared/molecules/ticket-card/ticket-card';
import { TicketService, TicketResponseDTO } from '../../services/ticket.service';
import { CreateTicketModalComponent } from '../../shared/organisms/create-ticket-modal/create-ticket-modal';

@Component({
  selector: 'app-user-dashboard-page',
  standalone: true,
  imports: [CommonModule, DashboardLayoutComponent, TicketCardComponent, CreateTicketModalComponent],
  templateUrl: './user-dashboard-page.html',
  styleUrl: './user-dashboard-page.css'
})
export class UserDashboardPageComponent implements OnInit {
  userInfo: UserInfo = {
    name: '',
    email: '',
    role: '',
    department: ''
  };

  tickets: TicketCardData[] = [];
  filteredTickets: TicketCardData[] = [];
  isLoading = true;
  selectedFilter: 'all' | 'open' | 'in_progress' | 'resolved' | 'closed' = 'all';
  showCreateModal = false;

  private ticketService: TicketService = inject(TicketService);
  private router: Router = inject(Router);

  ngOnInit(): void {
    this.loadUserInfo();
    this.loadTickets();
  }

  loadUserInfo(): void {
    // Obtener información del usuario desde localStorage (guardada durante el login)
    const employeeData = localStorage.getItem('employeeData');
    if (employeeData) {
      const employee = JSON.parse(employeeData);
      this.userInfo = {
        name: employee.name,
        email: employee.email,
        role: employee.role,
        department: employee.department
      };
    }
  }

  loadTickets(): void {
    this.isLoading = true;
    this.ticketService.getAllTickets().subscribe({
      next: (tickets: TicketResponseDTO[]) => {
        this.tickets = tickets.map(ticket => ({
          id: ticket.id,
          title: ticket.title,
          description: ticket.description,
          priority: ticket.priority,
          state: ticket.state,
          category: ticket.category.name,
          creationDate: ticket.creationDate
        }));
        this.filteredTickets = this.tickets;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading tickets:', error);
        this.isLoading = false;
      }
    });
  }

  filterTickets(filter: 'all' | 'open' | 'in_progress' | 'resolved' | 'closed'): void {
    this.selectedFilter = filter;
    
    if (filter === 'all') {
      this.filteredTickets = this.tickets;
    } else {
      const stateMap = {
        'open': 'ABIERTO',
        'in_progress': 'EN_PROCESO',
        'resolved': 'RESUELTO',
        'closed': 'CERRADO'
      };
      this.filteredTickets = this.tickets.filter(ticket => ticket.state === stateMap[filter]);
    }
  }

  getTicketCount(filter: 'all' | 'open' | 'in_progress' | 'resolved' | 'closed'): number {
    if (filter === 'all') {
      return this.tickets.length;
    }
    const stateMap = {
      'open': 'ABIERTO',
      'in_progress': 'EN_PROCESO',
      'resolved': 'RESUELTO',
      'closed': 'CERRADO'
    };
    return this.tickets.filter(ticket => ticket.state === stateMap[filter]).length;
  }

  handleLogout(): void {
    localStorage.removeItem('authToken');
    localStorage.removeItem('employeeData');
    this.router.navigate(['/login']);
  }

  openCreateModal(): void {
    this.showCreateModal = true;
  }

  closeCreateModal(): void {
    this.showCreateModal = false;
  }

  handleTicketCreated(): void {
    this.loadTickets();
    this.closeCreateModal();
  }
}
