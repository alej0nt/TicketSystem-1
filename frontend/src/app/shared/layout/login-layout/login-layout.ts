import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent, LoginCredentials } from '../../organisms/login-form/login-form';

@Component({
  selector: 'app-login-layout',
  standalone: true,
  imports: [CommonModule, LoginFormComponent],
  templateUrl: './login-layout.html',
  styleUrl: './login-layout.css'
})
export class LoginLayoutComponent {
  @Output() loginAttempt = new EventEmitter<LoginCredentials>();
  @Output() registerClick = new EventEmitter<void>();

  handleLoginAttempt(credentials: LoginCredentials): void {
    this.loginAttempt.emit(credentials);
  }

  handleRegisterClick(): void {
    this.registerClick.emit();
  }
}
