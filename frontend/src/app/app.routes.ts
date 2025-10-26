import { Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page';
import { TicketPageComponent } from './pages/ticket-page/ticket-page';
import { UserDashboardPageComponent } from './pages/user-dashboard-page/user-dashboard-page';

import { ProfilePageComponent } from './pages/profile-page/profile-page';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'dashboard/user',
    component: UserDashboardPageComponent,
    data: { roles: ['USER'] }
  },
  {
    path: 'profile',
    component: ProfilePageComponent,
  },
  {
    path: 'ticket/:id',
    component: TicketPageComponent,
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  }
];
