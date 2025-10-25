import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

type ButtonVariant = 'roundedBlue' | 'roundedWhite' | 'eyeIcon' | 'eyeIconClosed' | 'comment' | 'actionBlue'
  | 'actionPurple'
  | 'actionGreen'
  | 'actionGray'
  | 'actionRed';

const actionButtonGeneral = 'w-full px-5 py-3 bg-gradient-to-r text-white rounded-2xl text-sm font-bold transition-all shadow-md hover:shadow-lg';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './button.html',
  styleUrl: './button.css',
})
export class ButtonComponent {
  @Input() text: string = '';
  @Input() disabled: boolean = false;
  @Output() buttonClick = new EventEmitter<void>();
  @Input() iconClass?: string;
  @Input() ariaLabel?: string;
  @Input() variant: ButtonVariant = 'roundedBlue';

  private variants = {
    actionPurple: {
      class: actionButtonGeneral + ' from-purple-500 to-pink-500 hover:from-purple-600 hover:to-pink-600 ',
      iconCLass: 'fas fa-user-plus'
    },
    actionGreen: {
      class:
        actionButtonGeneral + ' from-green-500 to-emerald-500 hover:from-green-600 hover:to-emerald-600 ',
      iconCLass: 'fas fa-check-circle'
    },
    actionGray: {
      class:
        actionButtonGeneral + ' from-gray-500 to-gray-600 hover:from-gray-600 hover:to-gray-700 ',
      iconCLass: 'fas fa-archive'
    },
    actionBlue: {
      class: actionButtonGeneral + ' from-blue-500 to-indigo-500 hover:from-blue-600 hover:to-indigo-600 ',
      iconCLass: 'fas fa-edit'
    },
    actionRed: {
      class: actionButtonGeneral + ' from-red-500 to-rose-500 hover:from-red-600 hover:to-rose-600 ',
      iconCLass: 'fas fa-trash-alt'
    },
    roundedBlue: {
      class: "w-full py-3 sm:py-4 bg-gradient-to-r from-blue-700 to-blue-600 text-white font-semibold rounded-full hover:from-blue-600 hover:to-blue-500 transition-all duration-300 disabled:opacity-50 disabled:cursor-not-allowed text-sm sm:text-base",
      iconCLass: ''
    },
    roundedWhite: {
      class: "w-full py-3 sm:py-4 border-2 border-gray-300 rounded-full font-semibold text-gray-200 hover:bg-gray-50 hover:text-gray-800 transition-all duration-300 flex items-center justify-center gap-3 disabled:opacity-50 disabled:cursor-not-allowed text-sm sm:text-base",
      iconCLass: ''
    },
    comment: {
      class: "px-8 py-3 bg-gradient-to-r from-indigo-600 to-purple-600 hover:from-indigo-700 hover:to-purple-700 text-white rounded-2xl font-bold text-base transition-all shadow-lg hover:shadow-xl",
      iconCLass: ''
    },
    eyeIcon: {
      class: 'absolute right-4 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700 transition-colors duration-200',
      iconCLass: 'fa-solid fa-eye'
    },
    eyeIconClosed: {
      class: 'absolute right-4 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700 transition-colors duration-200',
      iconCLass: 'fa-solid fa-eye-slash'
    }
  };

  get currentIconClass(): string {
    return this.iconClass || this.variants[this.variant].iconCLass;
  }
  get style(): string {
    return this.variants[this.variant].class;
  }
  handleClick(): void {
    if (!this.disabled) {
      this.buttonClick.emit();
    }
  }
}
