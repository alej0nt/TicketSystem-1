import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

type ButtonVariant = 'roundedBlue' | 'roundedWhite';
@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './button.html',
  styleUrl: './button.css',
})
export class ButtonComponent {
  @Input() text: string = 'Iniciar sesión';
  @Input() disabled: boolean = false;
  @Output() buttonClick = new EventEmitter<void>();
  @Input() variant: ButtonVariant= 'roundedBlue';

  private variants= {
    roundedBlue: "w-full py-3 sm:py-4 bg-gradient-to-r from-blue-700 to-blue-600 text-white font-semibold rounded-full hover:from-blue-600 hover:to-blue-500 transition-all duration-300 disabled:opacity-50 disabled:cursor-not-allowed text-sm sm:text-base",
    roundedWhite: "w-full py-3 sm:py-4 border-2 border-gray-300 rounded-full font-semibold text-gray-200 hover:bg-gray-50 hover:text-gray-800 transition-all duration-300 flex items-center justify-center gap-3 disabled:opacity-50 disabled:cursor-not-allowed text-sm sm:text-base"
  };

  get style(): string {
    return this.variants[this.variant];
  }
  handleClick(): void {
    if (!this.disabled) {
      this.buttonClick.emit();
    }
  }
}
