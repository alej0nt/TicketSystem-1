import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService, NotificationResponseDTO } from '../../../services/notification.service';
import { interval } from 'rxjs';

@Component({
  selector: 'app-notification-bell',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification-bell.html',
  styleUrl: './notification-bell.css'
})
export class NotificationBellComponent implements OnInit {
  notifications = signal<NotificationResponseDTO[]>([]);
  unreadCount = signal(0);
  showDropdown = signal(false);

  constructor(private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.loadNotifications();
    // Actualizar notificaciones cada 30 segundos
    interval(30000).subscribe(() => {
      this.loadNotifications();
    });
  }

  loadNotifications(): void {
    const employeeData = localStorage.getItem('employeeData');
    if (!employeeData) return;

    const employee = JSON.parse(employeeData);
    this.notificationService.getNotificationsByEmployee(employee.id).subscribe({
      next: (notifications) => {
        this.notifications.set(notifications);
        this.unreadCount.set(notifications.filter(n => !n.isRead).length);
      },
      error: (error) => {
        console.error('Error loading notifications:', error);
      }
    });
  }

  toggleDropdown(): void {
    this.showDropdown.update(val => !val);
  }

  markAsRead(notification: NotificationResponseDTO): void {
    if (notification.isRead) return;

    this.notificationService.markAsRead(notification.id).subscribe({
      next: () => {
        this.loadNotifications();
      },
      error: (error) => {
        console.error('Error marking notification as read:', error);
      }
    });
  }

  getTimeAgo(dateString: string): string {
    const date = new Date(dateString);
    const now = new Date();
    const seconds = Math.floor((now.getTime() - date.getTime()) / 1000);

    if (seconds < 60) return 'Hace un momento';
    if (seconds < 3600) return `Hace ${Math.floor(seconds / 60)} min`;
    if (seconds < 86400) return `Hace ${Math.floor(seconds / 3600)} h`;
    return `Hace ${Math.floor(seconds / 86400)} días`;
  }
}
