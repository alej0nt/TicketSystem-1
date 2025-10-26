import { Component } from '@angular/core';
import { TicketLayoutComponent } from '../../shared/layout/ticket-layout/ticket-layout';
import { TicketResponseDTO, TicketService } from '../../services/ticket.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgIf } from '@angular/common';
import { AssignmentService } from '../../services/assignment.service';
import { EmployeeResponseDTO } from '../../services/employee.service';
import { forkJoin } from 'rxjs';

export interface TicketData {
  ticket: TicketResponseDTO,
  assignment: { id: number, agent: EmployeeResponseDTO, date: string }
};

@Component({
  selector: 'app-ticket-page',
  standalone: true,
  imports: [TicketLayoutComponent, NgIf],
  templateUrl: './ticket-page.html',
  styleUrl: './ticket-page.css'
})
export class TicketPageComponent {
  ticketData!: TicketData;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ticketService: TicketService,
    private assignmentService: AssignmentService
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let ticketId = +params['id'];
      if (ticketId) {
        // Ejecuta ambas peticiones de ticket y de asignacion en paralelo espera a que ambas terminen
        forkJoin({
          ticket: this.ticketService.getTicket(ticketId),
          assignment: this.assignmentService.getAssignmentByTicketId(ticketId)
        }).subscribe({
          next: ({ ticket, assignment }) => {
            this.ticketData = {
              ticket,
              assignment: {
                id: assignment.id,
                agent: assignment.employee,
                date: assignment.assignmentDate
              }
            };
            console.log('Ticket data:', this.ticketData);
          },
          error: (err) => {
            console.error('Error loading ticket or assignment:', err);
          }
        });

      } else {
        //this.router.navigate(['/not-found']);
        console.error('Invalid ticket ID');
      }
    })
  }
}
