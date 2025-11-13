import { Component, EventEmitter, Input, input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicketInfoComponent } from '../../organisms/ticket-info/ticket-info';
import { CommentsSectionComponent } from '../../organisms/comments-section/comments-section';
import { TextLinkComponent } from "../../atoms/text-link/text-link";
import { ButtonComponent } from "../../atoms/button/button";
import { TicketData } from '../../../pages/ticket-page/ticket-page';

@Component({
  selector: 'app-ticket-layout',
  standalone: true,
  imports: [CommonModule, TicketInfoComponent, CommentsSectionComponent, TextLinkComponent, ButtonComponent],
  templateUrl: './ticket-layout.html',
  styleUrls: ['./ticket-layout.css'],
})
export class TicketLayoutComponent {
  @Input() ticketObj!: TicketData;

  @Output() assign = new EventEmitter<void>();
  @Output() reassign = new EventEmitter<void>();
  @Output() resolve = new EventEmitter<void>();
  @Output() close = new EventEmitter<void>();
  @Output() reopen = new EventEmitter<void>();
  @Output() delete = new EventEmitter<void>();
}
