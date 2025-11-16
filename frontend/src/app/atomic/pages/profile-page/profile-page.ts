import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DashboardLayoutComponent, UserInfo } from '../../layout/dashboard-layout/dashboard-layout';
import { ButtonComponent } from '../../atoms/button/button';
import { InputComponent } from '../../atoms/input/input';
import { EmployeeService, EmployeeResponseDTO } from '../../../core/services/employee.service';

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [CommonModule, FormsModule, DashboardLayoutComponent, ButtonComponent, InputComponent],
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

  nameValue = '';
  emailValue = '';
  departmentValue = '';

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
      this.nameValue = employee.name;
      this.emailValue = employee.email;
      this.departmentValue = employee.department;
    }
  }

  toggleEdit(): void {
    this.isEditing = !this.isEditing;
    this.errorMessage = '';
    this.successMessage = '';
    if (!this.isEditing) {
      // Reset values if canceling
      this.loadUserInfo();
    }
  }

  handleSave(): void {
    if (!this.nameValue.trim() || !this.emailValue.trim() || !this.departmentValue.trim()) {
      this.errorMessage = 'Por favor completa todos los campos';
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.emailValue)) {
      this.errorMessage = 'Por favor ingresa un email válido';
      return;
    }

    this.isLoading = true;

    const { nameValue, emailValue, departmentValue } = this;
    this.employeeService.updateEmployee(this.employeeId, { 
      name: nameValue, 
      email: emailValue,
      role: this.userInfo.role,
      department: departmentValue 
    }).subscribe({
      next: (updatedEmployee) => {
        console.log('✓ SUCCESS:', updatedEmployee);
        localStorage.setItem('employeeData', JSON.stringify(updatedEmployee));
        this.userInfo = {
          name: updatedEmployee.name,
          email: updatedEmployee.email,
          role: updatedEmployee.role,
          department: updatedEmployee.department
        };
        this.nameValue = updatedEmployee.name;
        this.emailValue = updatedEmployee.email;
        this.departmentValue = updatedEmployee.department;
        this.successMessage = 'Perfil actualizado exitosamente';
        this.isEditing = false;
        this.isLoading = false;
      },
      error: (error) => {
        console.log('✗ ERROR:', error.status, error.message);
        console.log('Error details:', error);
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