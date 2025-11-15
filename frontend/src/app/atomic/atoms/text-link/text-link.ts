import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
type TextLinkVariants = 'baseType' | 'breadcumb';
@Component({
  selector: 'app-text-link',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './text-link.html',
  styleUrl: './text-link.css'
})
export class TextLinkComponent {
  @Input() text: string = '';
  @Input() href: string = '#';
  @Input() variant: TextLinkVariants = 'baseType';

  private variants= {
    baseType: 'text-gray-200 hover:text-gray-300 text-sm sm:text-base',
    breadcumb: 'hover:text-indigo-600 transition-colors'
  }

  get currentVariant(): string {
    return this.variants[this.variant];
  }
}
