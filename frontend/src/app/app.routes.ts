import { Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page';
import { TicketPageComponent } from './pages/ticket-page/ticket-page';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'ticket/:id',
    component: TicketPageComponent
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  }
];
