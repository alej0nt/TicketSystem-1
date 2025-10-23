import { Component, signal, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InputFieldComponent } from '../../atoms/input-field/input-field';
import { ButtonComponent } from '../../atoms/button/button';
import { TextLinkComponent } from '../../atoms/text-link/text-link';
import {AuthService} from '../../../services/auth.service'

export interface LoginCredentials {
  email: string;
  password: string;
}

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    InputFieldComponent,
    ButtonComponent,
    TextLinkComponent
  ],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css'
})
export class LoginFormComponent {
  @Output() loginAttempt = new EventEmitter<LoginCredentials>();
  @Output() registerClick = new EventEmitter<void>();

  private authService: AuthService = inject(AuthService);
  email = signal('');
  password = signal('');
  showPassword = signal(false);

  togglePassword(): void {
    this.showPassword.update((val) => !val);
  }

  handleLogin(): void {
    if (this.email() && this.password()) {
      this.authService.login({
        email: this.email(),
        password: this.password()
      }).subscribe({
        next: (response) => {
          console.log(response.token);
          this.loginAttempt.emit({
            email: this.email(),
            password: this.password()
          });
        },
        error: (error) => {
          console.error('Login error:', error);
        }
      });
    }
  }

  handleRegister(): void {
    this.registerClick.emit();
  }
}
