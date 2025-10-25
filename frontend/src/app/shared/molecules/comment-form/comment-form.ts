import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule } from '@angular/forms';
import { TextareaComponent } from '../../atoms/textarea/textarea';
import { ButtonComponent } from '../../atoms/button/button';

@Component({
  selector: 'app-comment-form',
  standalone: true,
  imports: [CommonModule, FormsModule, TextareaComponent, ButtonComponent],
  templateUrl: './comment-form.html',
  styleUrl: './comment-form.css'
})
export class CommentFormComponent {
  public text: string = '';
  @Output() send = new EventEmitter<string>();

  submit() {
    if (!this.text.trim()) return;
    this.send.emit(this.text.trim());
    this.text = '';
  }
}
