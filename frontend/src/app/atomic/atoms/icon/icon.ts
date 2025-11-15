import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { LucideAngularModule, User, Mail, Lock, Eye, X } from 'lucide-angular';
@Component({
  selector: 'icon-atom',
  templateUrl: './icon.html',
  standalone: true,
  imports: [CommonModule, LucideAngularModule]
})
export class IconComponent {
  @Input() name!: string;
  @Input() svg?: string;
  @Input() size: string = 'w-5 h-5';
  @Input() extraClass: string = ''; // clases de color, animación, etc.

  readonly icons = {
    User: User,
    Mail: Mail,
    Lock: Lock,
    Eye: Eye,
    X: X
  }
}
