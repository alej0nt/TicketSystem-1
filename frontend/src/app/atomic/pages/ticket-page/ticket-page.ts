import { Component } from '@angular/core';
import { TicketLayoutComponent } from '../../layout/ticket-layout/ticket-layout';
import { TicketResponseDTO, TicketService } from '../../../core/services/ticket.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgIf } from '@angular/common';
import { AssignmentService } from '../../../core/services/assignment.service';
import { EmployeeResponseDTO, EmployeeService } from '../../../core/services/employee.service';
import { forkJoin } from 'rxjs';
import { AssignAgentModalComponent, Agent } from '../../organism/assign-agent-modal/assign-agent-modal';

export interface TicketData {
  ticket: TicketResponseDTO,
  assignment: { id: number, agent: EmployeeResponseDTO, date: string } | null
};

@Component({
  selector: 'app-ticket-page',
  standalone: true,
  imports: [TicketLayoutComponent, NgIf, AssignAgentModalComponent],
  templateUrl: './ticket-page.html',
  styleUrl: './ticket-page.css'
})
export class TicketPageComponent {
  ticketData!: TicketData;
  
  // Modal state
  isModalOpen: boolean = false;
  modalMode: 'assign' | 'reassign' = 'assign';
  availableAgents: Agent[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ticketService: TicketService,
    private assignmentService: AssignmentService,
    private employeeService: EmployeeService 
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
              assignment: assignment ? {
                id: assignment.id,
                agent: assignment.employee,
                date: assignment.assignmentDate
              } : null
            };
            console.log('Ticket data:', this.ticketData);
          },
          error: (err) => {
            console.error('Error loading ticket or assignment:', err);
          }
        });
      } else {
        console.error('Invalid ticket ID');
      }
    });
  }

  loadAvailableAgents() {
    this.employeeService.getAgents().subscribe({
      next: (agents) => {
        this.availableAgents = agents.map(emp => ({
          id: emp.id,
          name: emp.name,
          email: emp.email,
          department: emp.department
        }));
      },
      error: (err) => {
        console.error('Error loading agents:', err);
      }
    });
  }

  private updateTicketState(newState: string) {
    this.ticketService.updateTicketState(this.ticketData.ticket.id, newState).subscribe({
      next: (updatedTicket) => {
        this.ticketData.ticket.state = updatedTicket.state;

        if (newState == 'CERRADO' || newState == 'RESUELTO') {
          this.ticketData.ticket.closingDate = updatedTicket.closingDate;
        }
      },
      error: (err) => {
        console.error('Error updating ticket state:', err);
      }
    });
  }

  onAssign() {
    this.modalMode = 'assign';
    this.isModalOpen = true;
    this.loadAvailableAgents();
  }

  onReassign() {
    this.modalMode = 'reassign';
    this.isModalOpen = true;
    this.loadAvailableAgents();
  }

  onConfirmAgent(agentId: number) {
    if (this.modalMode === 'assign') {
      this.assignmentService.createAssignmentByTicketId(this.ticketData.ticket.id, agentId).subscribe({
        next: (assignment) => {
          this.ticketData.assignment = {
            id: assignment.id,
            agent: assignment.employee,
            date: assignment.assignmentDate
          };
          this.ticketData.ticket.state = 'EN_PROGRESO';
        },
        error: (err) => {
          console.error('Error asignando agente:', err);
        }
      });
    } else {
      this.assignmentService.reassignEmployee(this.ticketData.ticket.id, agentId).subscribe({
        next: (assignment) => {
          this.ticketData.assignment = {
            id: assignment.id,
            agent: assignment.employee,
            date: assignment.assignmentDate
          };
        },
        error: (err) => {
          console.error('Error reasignando agente:', err);
        }
      });
    }
  }

  closeModal() {
    this.isModalOpen = false;
  }

  onResolve() {
    this.updateTicketState('RESUELTO');
  }

  onClose() {
    this.updateTicketState('CERRADO');
  }

  onDelete() {
    this.ticketService.deleteTicket(this.ticketData.ticket.id).subscribe({
      next: () => {
        this.router.navigate(['/dashboard/user']);
      },
      error: (err) => {
        console.log('Error deleting ticket ', err)
      }
    })
  }

  onReopen() {
    this.updateTicketState('EN_PROGRESO');
  }
}