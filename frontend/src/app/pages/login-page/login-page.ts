import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginLayoutComponent } from '../../shared/layout/login-layout/login-layout';
import { LoginCredentials } from '../../shared/organisms/login-form/login-form';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, LoginLayoutComponent],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css'
})
export class LoginPageComponent {

  handleLoginAttempt(credentials: LoginCredentials): void {
    console.log('Login attempt:', { email: credentials.email });
    // Aquí puedes agregar la lógica de autenticación
  }

  handleRegisterClick(): void {
    console.log('Register clicked');
    // Aquí puedes agregar la navegación a registro
  }
}
