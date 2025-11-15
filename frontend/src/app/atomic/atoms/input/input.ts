import { Component, Input, Output, EventEmitter } from '@angular/core';
import { IconComponent } from '../icon/icon';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'input-atom',
  templateUrl: './input.html',
  standalone: true,
  imports: [IconComponent, FormsModule, CommonModule]
})
export class InputComponent {
  @Input() id?: string;
  @Input() type: 'text' | 'email' | 'password' | 'number' | 'tel' | 'url' | 'search' | 'date'= 'text';
  @Input() placeholder: string = '';
  @Input() value: string = '';
  @Input() size: 'sm' | 'md' | 'lg' = 'md';
  @Input() state: 'default' | 'success' | 'error' = 'default';
  @Input() disabled: boolean = false;
  @Input() readonly: boolean = false;
  @Input() required: boolean = false;
  @Input() extraClass: string = '';
  @Input() forceLight: boolean = false;

  
  // Para input con iconos
  @Input() iconLeft?: string; // nombre del icono Lucide
  @Input() iconRight?: string;
  @Input() iconLeftSvg?: string; // SVG crudo
  @Input() iconRightSvg?: string;

  @Output() valueChange = new EventEmitter<string>();
  @Output() inputEvent = new EventEmitter<Event>();

  onInput(event: Event): void {
    const target = event.target as HTMLInputElement;
    this.value = target.value;
    this.valueChange.emit(this.value);
    this.inputEvent.emit(event);
  }

  get inputClass(): string {
    let base = 'block w-full border rounded-lg focus:outline-none ';
    
    // Tamaños
    if (this.size === 'sm') {
      base += 'p-2 text-xs ';
    } else if (this.size === 'lg') {
      base += 'p-4 text-base ';
    } else {
      base += 'p-2.5 text-sm '; // default
    }

    // Padding adicional para iconos
    if (this.iconLeft || this.iconLeftSvg) {
      base += this.size === 'sm' ? 'ps-8 ' : 'ps-10 ';
    }
    if (this.iconRight || this.iconRightSvg) {
      base += this.size === 'sm' ? 'pe-8 ' : 'pe-10 ';
    }

    // Estados
    if (this.disabled) {
      base += 'bg-gray-100 border-gray-300 text-gray-400 cursor-not-allowed dark:bg-white-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-gray-400 ';
    } else if (this.state === 'success') {
      base += 'bg-green-50 border-green-500 text-green-900 placeholder-green-700 focus:ring-green-500 focus:border-green-500 dark:bg-gray-700 dark:border-green-500 dark:text-green-400 dark:placeholder-green-500 ';
    } else if (this.state === 'error') {
      base += 'bg-red-50 border-red-500 text-red-900 placeholder-red-700 focus:ring-red-500 focus:border-red-500 dark:bg-gray-700 dark:border-red-500 dark:text-red-500 dark:placeholder-red-500 ';
    } else {
      if (this.forceLight) {
    // Forzar input claro siempre
    base += 'bg-white border-gray-300 text-gray-900 placeholder-gray-500 ';
  } else {
    // Comportamiento normal con dark mode
    base += 'bg-gray-50 border-gray-300 text-gray-900 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 ';
  }
    }

    return base + this.extraClass;
  }

  get containerClass(): string {
    return 'relative ' + (this.iconLeft || this.iconLeftSvg || this.iconRight || this.iconRightSvg ? '' : '');
  }

  get iconSize(): string {
    return this.size === 'sm' ? 'w-3 h-3' : 'w-4 h-4';
  }
}
