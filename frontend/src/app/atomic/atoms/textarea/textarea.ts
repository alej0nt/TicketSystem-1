import { CommonModule } from '@angular/common';
import { Component, forwardRef, Input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

type TextareaVariant = 'default' | 'comment';

const generalStyles = 'w-full px-5 py-4 bg-white border-2 border-gray-200 rounded-2xl text-gray-800 text-base placeholder-gray-400 focus:outline-none transition-all resize-none shadow-sm';

@Component({
  selector: 'app-textarea',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './textarea.html',
  styleUrl: './textarea.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TextareaComponent),
      multi: true
    }
  ]
})
export class TextareaComponent implements ControlValueAccessor {
  @Input() placeholder: string = 'Escribe aqui...';
  @Input() rows: number = 4;
  @Input() variant: TextareaVariant = 'default';

  value: string = '';
  disabled: boolean = false;

  private variants = {
    default: {
      class: `${generalStyles} focus:border-gray-400 focus:ring-4 focus:ring-gray-100`,
    },
    comment: {
      class: `${generalStyles} focus:border-indigo-500 focus:ring-4 focus:ring-indigo-100`
    }
  }

  get currentVariant(): string {
    return this.variants[this.variant].class;
  }

  // Metodos de ControlValueAccessor para integracion con formularios reactivos
  private onChange: (value: string) => void = () => { };
  private onTouched: () => void = () => { };

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
    const textarea = event.target as HTMLTextAreaElement;
    this.value = textarea.value;
    this.onChange(this.value);
  }

  onBlur(): void {
    this.onTouched();
  }
}
