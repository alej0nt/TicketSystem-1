import { Component, Input, Output, EventEmitter, forwardRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import {ButtonComponent} from '../../atoms/button/button';

type InputFieldVariants = 'baseType' | 'baseTypeIconPass';

@Component({
  selector: 'app-input-field-showbutton',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './input-field-showbutton.html',
  styleUrl: './input-field-showbutton.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputFieldShowButtonComponent),
      multi: true
    }
  ]
})
export class InputFieldShowButtonComponent implements ControlValueAccessor {
  @Input() type: string = 'text';
  @Input() placeholder: string = '';
  @Input() showToggle: boolean = false;
  @Output() toggleClick = new EventEmitter<void>();
  @Input() variant: InputFieldVariants = 'baseType';
  passwordVisible: boolean= false;
  iconClass: string = "fa-solid fa-eye"

  value: string = '';
  disabled: boolean = false;

  private variants= {
    baseType: {
      class: 'w-full px-6 py-3 sm:py-4 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent bg-white text-gray-800 placeholder-gray-400 text-base sm:text-lg',
      iconClass: ''
    },
    baseTypeIconPass: {
      class: 'w-full pl-10 pr-6 py-3 sm:py-4 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent bg-white text-gray-800 placeholder-gray-400 text-base sm:text-lg',
      iconClass: 'fa-solid fa-lock absolute left-3 top-1/2 -translate-y-1/2 text-gray-400'
    }
  }
  private onChange: (value: string) => void = () => {};
  private onTouched: () => void = () => {};

  get currentVariant(): string {
    return this.variants[this.variant].class;
  }
  get currentIcon():string {
    return this.variants[this.variant].iconClass;
  }
  get class(): string {
    return this.passwordVisible ? 'fa-solid fa-eye-slash' : 'fa-solid fa-eye';
  }
  get inputType(): string {
    return this.passwordVisible ? 'text' : 'password';
  }
  writeValue(value: string): void {
    this.value = value || '';
  }

  registerOnChange(fn: (value: string) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  onInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.value = input.value;
    this.onChange(this.value);
  }

  onBlur(): void {
    this.onTouched();
  }


  handleToggle(): void {
    this.passwordVisible = !this.passwordVisible;
    this.toggleClick.emit();
  }
}
