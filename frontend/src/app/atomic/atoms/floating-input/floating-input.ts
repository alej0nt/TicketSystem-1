import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'floating-input-atom',
  templateUrl: './floating-input.html',
  standalone: true
})
export class FloatingInputComponent {
  @Input() id: string = '';
  @Input() name: string = '';
  @Input() type: 'text' | 'email' | 'password' | 'number' | 'tel' | 'url' = 'text';
  @Input() label: string = '';
  @Input() value: string = '';
  @Input() placeholder: string = ' ';
  @Input() required: boolean = false;
  @Input() disabled: boolean = false;
  @Input() readonly: boolean = false;
  @Input() extraClass: string = '';
  
  @Output() valueChange = new EventEmitter<string>();
  @Output() inputEvent = new EventEmitter<Event>();
  @Output() focusEvent = new EventEmitter<FocusEvent>();
  @Output() blurEvent = new EventEmitter<FocusEvent>();

  onInput(event: Event): void {
    const target = event.target as HTMLInputElement;
    this.value = target.value;
    this.valueChange.emit(this.value);
    this.inputEvent.emit(event);
  }

  onFocus(event: FocusEvent): void {
    this.focusEvent.emit(event);
  }

  onBlur(event: FocusEvent): void {
    this.blurEvent.emit(event);
  }

  get inputClass(): string {
    return `block py-2.5 px-0 w-full text-sm bg-transparent border-0 border-b-2 appearance-none focus:outline-none focus:ring-0 peer ${this.getColorClasses()} ${this.extraClass}`;
  }

  get labelClass(): string {
    return `absolute text-sm duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto ${this.getLabelColorClasses()}`;
  }

  private getColorClasses(): string {
    if (this.disabled) {
      return 'text-gray-400 border-gray-300 cursor-not-allowed';
    }
    // ✅ TEXTO BLANCO y borde blanco/azul
    return 'text-white border-gray-300 focus:border-blue-500 dark:text-white dark:border-gray-400 dark:focus:border-blue-500';
  }

  private getLabelColorClasses(): string {
    if (this.disabled) {
      return 'text-gray-400';
    }
    // ✅ LABEL BLANCA que se vuelve azul al hacer focus
    return 'text-white peer-focus:text-blue-500 dark:text-white dark:peer-focus:text-blue-400';
  }
}