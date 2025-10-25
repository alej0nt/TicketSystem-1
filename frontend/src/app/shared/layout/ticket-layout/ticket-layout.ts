import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicketInfoComponent } from '../../organisms/ticket-info/ticket-info';
import { CommentsSectionComponent } from '../../organisms/comments-section/comments-section';
import { TextLinkComponent } from "../../atoms/text-link/text-link";
import { ButtonComponent } from "../../atoms/button/button";

@Component({
  selector: 'app-ticket-layout',
  standalone: true,
  imports: [CommonModule, TicketInfoComponent, CommentsSectionComponent, TextLinkComponent, ButtonComponent],
  templateUrl: './ticket-layout.html',
  styleUrls: ['./ticket-layout.css'],
})
export class TicketLayoutComponent {
  ticketId = '5001';

  // acciones (solo UI por ahora)
  onEdit() {
    console.log('Editar ticket');
  }
  onReassign() {
    console.log('Reasignar');
  }
  onResolve() {
    console.log('Marcar como resuelto');
  }
  onClose() {
    console.log('Cerrar ticket');
  }
  onDelete() {
    console.log('Eliminar ticket');
  }
}
