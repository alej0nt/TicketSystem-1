import { Component, Output, EventEmitter, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../atoms/button/button';
import { InputComponent } from '../../atoms/input/input';

interface FilterParams {
  fromDate: string;
  toDate: string;
}

@Component({
  selector: 'app-ticket-record-filter',
  standalone: true,
  imports: [CommonModule, ButtonComponent, InputComponent],
  templateUrl: './ticket-record-filter.html',
  styleUrl: './ticket-record-filter.css'
})
export class TicketRecordFilterComponent {
  @Input() fromDate: string = '';
  @Input() toDate: string = '';

  @Output() search = new EventEmitter<FilterParams>();
  @Output() clear = new EventEmitter<void>();

  // SVG de icono de calendario para usar en inputs
  calendarIconSvg = '<i class="fas fa-calendar text-gray-500"></i>';

  onSearch() {
    this.search.emit({
      fromDate: this.fromDate,
      toDate: this.toDate
    });
  }

  onClear() {
    this.fromDate = '';
    this.toDate = '';
    this.clear.emit();
  }
}
