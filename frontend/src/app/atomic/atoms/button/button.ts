import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { BUTTON_STYLES } from './button.config';
import { ButtonSize, ButtonColor, ButtonVariant, ButtonRounded } from './button.type';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'button-atom',
  templateUrl: './button.html',
  standalone: true,
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ButtonComponent {
  @Input() text = '';
  @Input() type: 'button' | 'submit' | 'reset' = 'button';
  @Input() size: ButtonSize = 'md';
  @Input() color: ButtonColor = 'blue';
  @Input() variant: ButtonVariant = 'solid';
  @Input() rounded: ButtonRounded = 'lg';
  @Input() shadow = false;
  @Input() disabled = false;
  @Input() extraClass = '';

  @Output() buttonClick = new EventEmitter<void>();

  private readonly styles = BUTTON_STYLES;

  onButtonClick(event: Event): void {
    console.log('Button clicked, disabled:', this.disabled);
    if (!this.disabled) {
      this.buttonClick.emit();
    }
  }

  get buttonClass(): string {
    const classes: string[] = [
      this.styles.base,
      this.styles.sizes[this.size],
      this.styles.rounded[this.rounded],
      this.styles.variants[this.variant][this.color],
      this.styles.effects.hover
    ];

    if (this.shadow) {
      classes.push(this.styles.shadows[this.color]);
    }

    if (this.disabled) {
      classes.push(this.styles.disabled);
    }

    if (this.extraClass) {
      classes.push(this.extraClass);
    }

    return classes.filter(Boolean).join(' ');
  }
}