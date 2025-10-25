import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatusBadgeComponent } from '../../atoms/status-badge/status-badge';
import { TicketMetaInfoComponent } from '../../molecules/ticket-meta-info/ticket-meta-info';

@Component({
  selector: 'app-ticket-info',
  standalone: true,
  imports: [CommonModule, StatusBadgeComponent, TicketMetaInfoComponent],
  templateUrl: './ticket-info.html',
  styleUrl: './ticket-info.css'
})
export class TicketInfoComponent {
  // Ticket (datos quemados de ejemplo)
  ticket = {
    id: '5001',
    title: 'Error en inicio de sesión',
    description:
      'El usuario no puede iniciar sesión con sus credenciales correctas. Ha intentado múltiples veces y el sistema rechaza las credenciales válidas. Este problema comenzó después de la última actualización del sistema.',
    status: 'EN PROGRESO',
    priority: 'PRIORIDAD ALTA',
    category: 'Autenticación',
    agent: { name: 'Empleado #234', initials: 'E4', role: 'Soporte' },
    createdAt: '11 sep 2025, 2:30 PM',
    closedAt: 'No cerrado',
  };
}
