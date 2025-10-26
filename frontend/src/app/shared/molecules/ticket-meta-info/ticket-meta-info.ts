import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AvatarComponent } from '../../atoms/avatar/avatar';
import { TicketData } from '../../../pages/ticket-page/ticket-page';

type AvatarColor = 'blue' | 'green' | 'indigo' | 'purple' | 'orange' | 'pink' | 'teal';

@Component({
  selector: 'app-ticket-meta-info',
  standalone: true,
  imports: [CommonModule, AvatarComponent],
  templateUrl: './ticket-meta-info.html',
  styleUrl: './ticket-meta-info.css'
})
export class TicketMetaInfoComponent {
  @Input() assignment!: TicketData['assignment'];
  @Input() createdAt!: string;
  @Input() closedAt!: string;
}
