import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommentItemComponent } from '../../molecules/comment-item/comment-item';
import { CommentFormComponent } from '../../molecules/comment-form/comment-form';
import { CommentResponseDTO, CommentService } from '../../../core/services/comment.service';


@Component({
  selector: 'app-comments-section',
  standalone: true,
  imports: [CommonModule, CommentItemComponent, CommentFormComponent],
  templateUrl: './comments-section.html',
  styleUrl: './comments-section.css'
})
export class CommentsSectionComponent {
  @Input() ticketId!: number;
  comments: CommentResponseDTO[] = [];

  constructor(private commentService: CommentService) {}

  ngOnInit() {
    this.commentService.getCommentsByTicketId(this.ticketId).subscribe({
      next: (comments) => {
        this.comments = comments;
        console.log(comments);
        
      },
      error: (err) => {
        console.error('Error loading comments:', err);
      }
    });
  }

  onAddComment(text: string) {
    this.commentService.createComment({
      ticketId: this.ticketId,
      employeeId: JSON.parse(localStorage.getItem('employeeData') || '{}').id,
      text: text
    }).subscribe({
      next: (newComment) => {
        this.comments.push(newComment);
      },
      error: (err) => {
        console.error('Error adding comment:', err);
      }
    });
  }
}

