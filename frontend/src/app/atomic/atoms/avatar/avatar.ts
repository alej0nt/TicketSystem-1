import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

type AvatarSize = 'sm' | 'md' | 'lg' | 'xl';
type AvatarColor = 'blue' | 'green' | 'indigo' | 'purple' | 'orange' | 'pink' | 'teal';

@Component({
  selector: 'app-avatar',
  imports: [CommonModule],
  templateUrl: './avatar.html',
  styleUrl: './avatar.css'
})
export class AvatarComponent {
  @Input() initials : string = '';
  @Input() size: AvatarSize = 'md';
  @Input() color: AvatarColor = 'indigo';

  private variants = {
    sizes: {
      sm: 'w-8 h-8 text-sm',
      md: 'w-12 h-12 text-base',
      lg: 'w-16 h-16 text-lg',
      xl: 'w-20 h-20 text-xl'
    },
    colors: {
      blue: 'bg-gradient-to-br from-blue-500 to-purple-500',
      green: 'bg-gradient-to-br from-green-500 to-teal-500',
      indigo: 'bg-gradient-to-br from-indigo-500 to-purple-600',
      purple: 'bg-gradient-to-br from-purple-500 to-pink-500',
      orange: 'bg-gradient-to-br from-orange-500 to-red-500',
      pink: 'bg-gradient-to-br from-pink-500 to-rose-500',
      teal: 'bg-gradient-to-br from-teal-500 to-cyan-500'
    }
  }

  get displayInitials(): string {
    if (!this.initials) return '?';
    return this.initials.substring(0, 2).toUpperCase();
  }
  get sizeClass(): string {
    return this.variants.sizes[this.size];
  }
  get colorClass(): string {
    return this.variants.colors[this.color];
  }
  get style(): string {
    return `${this.sizeClass} ${this.colorClass} rounded-full flex items-center justify-center text-white font-bold shadow-md`;
  }
}
