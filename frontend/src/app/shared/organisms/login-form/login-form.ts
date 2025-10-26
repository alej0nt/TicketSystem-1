import { Component, signal, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { InputFieldComponent } from '../../atoms/input-field/input-field';
import { ButtonComponent } from '../../atoms/button/button';
import { TextLinkComponent } from '../../atoms/text-link/text-link';
import {AuthService} from '../../../services/auth.service';
import { InputFieldShowButtonComponent } from '../../molecules/input-field-showbutton/input-field-showbutton';

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
    TextLinkComponent,
    InputFieldShowButtonComponent
  ],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css'
})
export class LoginFormComponent {
  @Output() loginAttempt = new EventEmitter<LoginCredentials>();
  @Output() registerClick = new EventEmitter<void>();

  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);
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
          // Agrego el token al localStorage
          localStorage.setItem('authToken', response.token);
          // Guardar información del empleado
          localStorage.setItem('employeeData', JSON.stringify(response.employee));
          
          // Redirigir según el rol del usuario
          const role = response.employee.role;
          switch (role) {
            case 'USER':
              this.router.navigate(['/dashboard/user']);
              break;
            case 'AGENT':
              //this.router.navigate(['/dashboard/agent']);
              break;
            case 'ADMIN':
              //this.router.navigate(['/dashboard/admin']);
              break;
            default:
              this.router.navigate(['/login']);
          }
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
