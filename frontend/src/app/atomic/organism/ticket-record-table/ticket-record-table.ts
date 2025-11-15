import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicketRecordResponseDTO } from '../../../core/services/ticket.service';
import { formatDate } from '../../../utils/date.utils';
import { StatusBadgeComponent } from '../../atoms/status-badge/status-badge';

@Component({
  selector: 'app-ticket-record-table',
  standalone: true,
  imports: [CommonModule, StatusBadgeComponent],
  templateUrl: './ticket-record-table.html',
  styleUrl: './ticket-record-table.css'
})
export class TicketRecordTableComponent {
  @Input() records: TicketRecordResponseDTO[] = [];
  @Input() isLoading: boolean = false;

  getFormattedDate(isoDate: string): string {
    return formatDate(isoDate);
  }
}
