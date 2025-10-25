import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

type StatusBadgeVariant = 'status' | 'priority' | 'category';

@Component({
  selector: 'app-status-badge',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './status-badge.html',
  styleUrl: './status-badge.css',
})
export class StatusBadgeComponent {
  @Input() text: string = '';
  @Input() variant: StatusBadgeVariant = 'status';

  private variants = {
    status: {
      class: 'bg-yellow-100 text-yellow-700 border-yellow-300',
    },
    priority: {
      class: 'bg-orange-100 text-orange-700 border-orange-300',
    },
    category: {
      class: 'bg-purple-100 text-purple-700 border-purple-300',
    },
  };

  get style(): string {
    return this.variants[this.variant].class + ' px-4 py-1.5 rounded-full text-sm font-semibold border';
  }
}
