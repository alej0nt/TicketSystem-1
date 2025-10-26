import { Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page';
import { TicketPageComponent } from './pages/ticket-page/ticket-page';
import { UserDashboardPageComponent } from './pages/user-dashboard-page/user-dashboard-page';

import { ProfilePageComponent } from './pages/profile-page/profile-page';
import { authGuard } from './services/auth-guard.service';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'dashboard/user',
    component: UserDashboardPageComponent,
    canActivate: [authGuard],
    data: { roles: ['USER'] }
  },
  {
    path: 'profile',
    component: ProfilePageComponent,
    canActivate: [authGuard]
  },
  {
    path: 'ticket/:id',
    component: TicketPageComponent,
    canActivate: [authGuard]
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  }
];
