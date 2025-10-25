import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommentItemComponent } from '../../molecules/comment-item/comment-item';
import { CommentFormComponent } from '../../molecules/comment-form/comment-form';

interface CommentModel {
  author: string;
  role: string;
  date: string;
  text: string;
  color: string;
}

@Component({
  selector: 'app-comments-section',
  standalone: true,
  imports: [CommonModule, CommentItemComponent, CommentFormComponent],
  templateUrl: './comments-section.html',
  styleUrl: './comments-section.css'
})
export class CommentsSectionComponent {
  comments: CommentModel[] = [
    {
      author: 'María González',
      role: 'Senior Developer',
      date: '11 sep 2025, 3:45 PM',
      text:
        'He revisado el problema y parece estar relacionado con la caché del navegador. He limpiado la sesión del usuario y ahora debería poder acceder correctamente.',
      color: 'blue',
    },
    {
      author: 'Carlos Ramírez',
      role: 'DevOps Engineer',
      date: '11 sep 2025, 4:20 PM',
      text:
        'Confirmado. El problema estaba en la configuración del servidor de autenticación. Ya está corregido en el ambiente de producción.',
      color: 'green',
    },
    {
      author: 'María González',
      role: 'Senior Developer',
      date: '11 sep 2025, 5:10 PM',
      text:
        'Excelente trabajo Carlos. He verificado que el usuario ya puede acceder sin problemas. Voy a monitorear durante las próximas 24 horas para asegurar que no haya recurrencia del error.',
      color: 'blue',
    },
  ];

  onAddComment(text: string) {
    const now = new Date().toLocaleString('es-CO', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
    this.comments = [
      ...this.comments,
      { author: 'Tú', role: 'You', date: now, text, color: 'indigo' },
    ];
  }
}

