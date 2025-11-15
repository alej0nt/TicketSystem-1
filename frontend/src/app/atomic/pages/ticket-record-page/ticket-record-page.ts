import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TicketService, TicketRecordResponseDTO } from '../../../core/services/ticket.service';
import { TicketRecordFilterComponent } from '../../organism/ticket-record-filter/ticket-record-filter';
import { TicketRecordTableComponent } from '../../organism/ticket-record-table/ticket-record-table';

@Component({
  selector: 'app-ticket-record-page',
  standalone: true,
  imports: [CommonModule, TicketRecordFilterComponent, TicketRecordTableComponent],
  templateUrl: './ticket-record-page.html',
  styleUrl: './ticket-record-page.css'
})
export class TicketRecordPageComponent implements OnInit {
  ticketId!: number;
  records: TicketRecordResponseDTO[] = [];
  filteredRecords: TicketRecordResponseDTO[] = [];
  
  // Filtros
  fromDate: string = '';
  toDate: string = '';
  
  isLoading: boolean = false;
  error: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ticketService: TicketService
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.ticketId = +params['ticketId'];
      if (this.ticketId) {
        this.loadRecords();
      }
    });
  }

  loadRecords() {
    this.isLoading = true;
    this.error = '';
    
    const fromParam = this.fromDate ? this.convertToISO(this.fromDate) : undefined;
    const toParam = this.toDate ? this.convertToISO(this.toDate) : undefined;

    this.ticketService.getAllTicketRecordsByTicketId(this.ticketId, fromParam, toParam).subscribe({
      next: (data) => {
        this.records = data;
        this.filteredRecords = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar los registros del ticket';
        console.error(err);
        this.isLoading = false;
      }
    });
  }

  onFilterSearch(filterParams: { fromDate: string; toDate: string }) {
    this.fromDate = filterParams.fromDate;
    this.toDate = filterParams.toDate;
    this.loadRecords();
  }

  onFilterClear() {
    this.fromDate = '';
    this.toDate = '';
    this.filteredRecords = this.records;
  }

  goBack() {
    this.router.navigate([`/ticket/${this.ticketId}`]);
  }

  private convertToISO(dateString: string): string {
    // Convierte formato yyyy-MM-dd a formato ISO
    return new Date(dateString).toISOString().split('T')[0];
  }
}
