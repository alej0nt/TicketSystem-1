import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DashboardLayoutComponent, UserInfo } from '../../shared/layout/dashboard-layout/dashboard-layout';
import { EmployeeService, EmployeeResponseDTO } from '../../services/employee.service';

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [CommonModule, FormsModule, DashboardLayoutComponent],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.css'
})
export class ProfilePageComponent implements OnInit {
  userInfo: UserInfo = {
    name: '',
    email: '',
    role: '',
    department: ''
  };

  name = signal('');
  email = signal('');
  department = signal('');
  currentPassword = signal('');
  newPassword = signal('');
  confirmPassword = signal('');

  isEditing = false;
  isLoading = false;
  successMessage = '';
  errorMessage = '';
  employeeId: number = 0;

  private employeeService: EmployeeService = inject(EmployeeService);
  private router: Router = inject(Router);

  ngOnInit(): void {
    this.loadUserInfo();
  }

  loadUserInfo(): void {
    const employeeData = localStorage.getItem('employeeData');
    if (employeeData) {
      const employee = JSON.parse(employeeData);
      this.employeeId = employee.id;
      this.userInfo = {
        name: employee.name,
        email: employee.email,
        role: employee.role,
        department: employee.department
      };
      this.name.set(employee.name);
      this.email.set(employee.email);
      this.department.set(employee.department);
    }
  }

  toggleEdit(): void {
    this.isEditing = !this.isEditing;
    this.errorMessage = '';
    this.successMessage = '';
    if (!this.isEditing) {
      // Reset values if canceling
      this.loadUserInfo();
      this.currentPassword.set('');
      this.newPassword.set('');
      this.confirmPassword.set('');
    }
  }

  handleSave(): void {
    this.errorMessage = '';
    this.successMessage = '';

    // Validate passwords if changing
    if (this.newPassword()) {
      if (this.newPassword() !== this.confirmPassword()) {
        this.errorMessage = 'Las contraseñas no coinciden';
        return;
      }
      if (this.newPassword().length < 6) {
        this.errorMessage = 'La contraseña debe tener al menos 6 caracteres';
        return;
      }
    }

    this.isLoading = true;

    const updateData: Partial<EmployeeResponseDTO> = {
      name: this.name(),
      email: this.email(),
      department: this.department()
    };

    // Note: Password update would need a separate endpoint in real implementation
    // For now, we'll just update the basic info

    this.employeeService.updateEmployee(this.employeeId, updateData).subscribe({
      next: (updatedEmployee) => {
        // Update localStorage
        localStorage.setItem('employeeData', JSON.stringify(updatedEmployee));
        this.userInfo = {
          name: updatedEmployee.name,
          email: updatedEmployee.email,
          role: updatedEmployee.role,
          department: updatedEmployee.department
        };
        this.successMessage = 'Perfil actualizado exitosamente';
        this.isEditing = false;
        this.isLoading = false;
        this.currentPassword.set('');
        this.newPassword.set('');
        this.confirmPassword.set('');
      },
      error: (error) => {
        console.error('Error updating profile:', error);
        this.errorMessage = 'Error al actualizar el perfil. Por favor intenta de nuevo.';
        this.isLoading = false;
      }
    });
  }

  handleLogout(): void {
    localStorage.removeItem('authToken');
    localStorage.removeItem('employeeData');
    this.router.navigate(['/login']);
  }

  goToDashboard(): void {
    const employeeData = localStorage.getItem('employeeData');
    if (employeeData) {
      const employee = JSON.parse(employeeData);
      const role = employee.role;
      switch (role) {
        case 'USER':
          this.router.navigate(['/dashboard/user']);
          break;
        case 'AGENT':
          this.router.navigate(['/dashboard/agent']);
          break;
        case 'ADMIN':
          this.router.navigate(['/dashboard/admin']);
          break;
        default:
          this.router.navigate(['/login']);
      }
    } else {
      this.router.navigate(['/login']);
    }
  }
}
