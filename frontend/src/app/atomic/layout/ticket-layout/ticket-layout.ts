import { Component, EventEmitter, Input, input, Output, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TicketInfoComponent } from '../../organism/ticket-info/ticket-info';
import { CommentsSectionComponent } from '../../organism/comments-section/comments-section';
import { TextLinkComponent } from "../../atoms/text-link/text-link";
import { ButtonComponent } from "../../atoms/button/button";
import { TicketData } from '../../pages/ticket-page/ticket-page';

@Component({
  selector: 'app-ticket-layout',
  standalone: true,
  imports: [CommonModule, TicketInfoComponent, CommentsSectionComponent, TextLinkComponent, ButtonComponent],
  templateUrl: './ticket-layout.html',
  styleUrls: ['./ticket-layout.css'],
})
export class TicketLayoutComponent implements OnInit {
  @Input() ticketObj!: TicketData;

  @Output() assign = new EventEmitter<void>();
  @Output() reassign = new EventEmitter<void>();
  @Output() resolve = new EventEmitter<void>();
  @Output() close = new EventEmitter<void>();
  @Output() reopen = new EventEmitter<void>();
  @Output() delete = new EventEmitter<void>();

  userRole: string | null = null;

  constructor(private router: Router) { }

  ngOnInit() {
    this.loadUserRole();
  }

  loadUserRole() {
    // Obtener el rol del usuario del localStorage o del servicio de autenticación
    const user = localStorage.getItem('employeeData');
    if (user) {
      try {
        const userData = JSON.parse(user);
        this.userRole = userData.role || null;
      } catch (e) {
        console.error('Error parsing user data:', e);
      }
    }
  }

  // Validaciones de permisos
  isAdmin(): boolean {
    return this.userRole === 'ADMIN';
  }

  isAdminOrAgent(): boolean {
    return this.isAdmin() || this.userRole === 'AGENT';
  }

  viewRecords() {
    this.router.navigate([`/ticket/${this.ticketObj.ticket.id}/record`]);
  }
}
