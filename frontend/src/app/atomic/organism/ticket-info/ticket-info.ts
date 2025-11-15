import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatusBadgeComponent } from '../../atoms/status-badge/status-badge';
import { TicketMetaInfoComponent } from '../../molecules/ticket-meta-info/ticket-meta-info';
import { TicketResponseDTO } from '../../../core/services/ticket.service';
import { TicketData } from '../../pages/ticket-page/ticket-page';

@Component({
  selector: 'app-ticket-info',
  standalone: true,
  imports: [CommonModule, StatusBadgeComponent, TicketMetaInfoComponent],
  templateUrl: './ticket-info.html',
  styleUrl: './ticket-info.css'
})
export class TicketInfoComponent {
  @Input() ticketObj!: TicketData;
}
