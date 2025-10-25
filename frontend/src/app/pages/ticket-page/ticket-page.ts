import { Component } from '@angular/core';
import { TicketLayoutComponent } from '../../shared/layout/ticket-layout/ticket-layout';

@Component({
  selector: 'app-ticket-page',
  standalone: true,
  imports: [TicketLayoutComponent],
  templateUrl: './ticket-page.html',
  styleUrl: './ticket-page.css'
})
export class TicketPageComponent {

}
