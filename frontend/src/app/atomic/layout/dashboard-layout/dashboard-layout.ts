import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AvatarComponent } from '../../atoms/avatar/avatar';
import { NotificationBellComponent } from '../../molecules/notification-bell/notification-bell';

export interface UserInfo {
  name: string;
  email: string;
  role: string;
  department: string;
}

@Component({
  selector: 'app-dashboard-layout',
  standalone: true,
  imports: [CommonModule, AvatarComponent, NotificationBellComponent],
  templateUrl: './dashboard-layout.html',
  styleUrl: './dashboard-layout.css'
})
export class DashboardLayoutComponent {
  @Input() userInfo!: UserInfo;
  @Output() logout = new EventEmitter<void>();

  showUserMenu = false;

  constructor(private router: Router) {}

  toggleUserMenu(): void {
    this.showUserMenu = !this.showUserMenu;
  }

  handleLogout(): void {
    this.logout.emit();
  }

  goToProfile(): void {
    this.showUserMenu = false;
    this.router.navigate(['/profile']);
  }

  getUserInitials(): string {
    if (!this.userInfo?.name) return '??';
    const names = this.userInfo.name.split(' ');
    if (names.length >= 2) {
      return names[0][0] + names[1][0];
    }
    return names[0].substring(0, 2);
  }
}
