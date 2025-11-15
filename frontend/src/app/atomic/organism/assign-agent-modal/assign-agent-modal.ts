import { Component, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from "../../atoms/button/button";
import { SelectComponent, SelectOption } from '../../atoms/select/select';
import { FormsModule } from '@angular/forms';

export interface Agent {
  id: number;
  name: string;
  email: string;
  department: string;
}

@Component({
  selector: 'app-assign-agent-modal',
  standalone: true,
  imports: [CommonModule, ButtonComponent, SelectComponent, FormsModule ],
  templateUrl: './assign-agent-modal.html',
  styleUrls: ['./assign-agent-modal.css'],
})
export class AssignAgentModalComponent implements OnChanges {
  @Input() isOpen: boolean = false;
  @Input() mode: 'assign' | 'reassign' = 'assign';
  @Input() agents: Agent[] = [];
  @Input() currentAgentId?: number;

  @Output() close = new EventEmitter<void>();
  @Output() confirm = new EventEmitter<number>();

  selectedAgentId: number | null = null;
  agentOptions: SelectOption[] = [];
  modalTitle: string = '';

  ngOnChanges(): void {
    this.updateModalTitle();
    this.updateAgentOptions();
    
    if (this.mode === 'reassign' && this.currentAgentId) {
      this.selectedAgentId = this.currentAgentId;
    } else {
      this.selectedAgentId = null;
    }
  }

  updateModalTitle(): void {
    this.modalTitle = this.mode === 'assign' ? 'Asignar Agente' : 'Reasignar Agente';
  }

  updateAgentOptions(): void {
    this.agentOptions = this.agents.map((agent) => ({
      value: agent.id,
      label: agent.department ? `${agent.name} - ${agent.department}` : agent.name,
    }));
  }

  onAgentSelected(agentId: string | number): void {
    this.selectedAgentId = Number(agentId);
  }

  onConfirm(): void {
    if (this.selectedAgentId) {
      this.confirm.emit(this.selectedAgentId);
      this.closeModal();
    }
  }

  closeModal(): void {
    this.selectedAgentId = null;
    this.close.emit();
  }

  onBackdropClick(event: MouseEvent): void {
    if (event.target === event.currentTarget) {
      this.closeModal();
    }
  }
}