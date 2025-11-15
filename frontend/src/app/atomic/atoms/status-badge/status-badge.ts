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
  getClass: (text: string) => {
    const map: Record<string, string> = {
      'ABIERTO':     'bg-green-100 text-green-700 border-green-300',
      'EN_PROGRESO': 'bg-blue-100 text-blue-700 border-blue-300',
      'CERRADO':     'bg-red-100 text-red-700 border-red-300',
      'RESUELTO':    'bg-indigo-100 text-indigo-700 border-indigo-300',
    };

    return map[text] || 'bg-gray-100 text-gray-700 border-gray-300'; // default
  }
},

    priority: {
      class: 'bg-orange-100 text-orange-700 border-orange-300',
    },
    category: {
      class: 'bg-purple-100 text-purple-700 border-purple-300',
    },
  };

  get style(): string {
  if (this.variant === 'status') {
    return (
      this.variants.status.getClass(this.text) +
      ' px-4 py-1.5 rounded-full text-sm font-semibold border'
    );
  }

  // prioridad y categoría quedan iguales
  return (
    this.variants[this.variant].class +
    ' px-4 py-1.5 rounded-full text-sm font-semibold border'
  );
}

}
