import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AvatarComponent } from '../../atoms/avatar/avatar';

type AvatarColor = 'blue' | 'green' | 'indigo' | 'purple' | 'orange' | 'pink' | 'teal';

@Component({
  selector: 'app-ticket-meta-info',
  standalone: true,
  imports: [CommonModule, AvatarComponent],
  templateUrl: './ticket-meta-info.html',
  styleUrl: './ticket-meta-info.css'
})
export class TicketMetaInfoComponent {
  @Input() agent?: { name: string; initials: string; role: string};
  @Input() createdAt?: string;
  @Input() closedAt?: string | null;
}
