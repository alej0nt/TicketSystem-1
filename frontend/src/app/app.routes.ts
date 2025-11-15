import { Routes } from '@angular/router';
import { LoginPageComponent } from './atomic/pages/login-page/login-page';
import { TicketPageComponent } from './atomic/pages/ticket-page/ticket-page';
import { UserDashboardPageComponent } from './atomic/pages/user-dashboard-page/user-dashboard-page';
import { AdminDashboardPageComponent } from './atomic/pages/admin-dashboard-page/admin-dashboard-page';
import { AgentDashboardPageComponent } from './atomic/pages/agent-dashboard-page/agent-dashboard-page';
import { AgentTicketPageComponent } from './atomic/pages/agent-ticket-page/agent-ticket-page';
import { ProfilePageComponent } from './atomic/pages/profile-page/profile-page';
import { TicketRecordPageComponent } from './atomic/pages/ticket-record-page/ticket-record-page';

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
    path: 'dashboard/admin',
    component: AdminDashboardPageComponent,
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'dashboard/agent',
    component: AgentDashboardPageComponent,
    data: { roles: ['AGENT'] }
  },
  {
    path: 'agent/ticket/:id',
    component: AgentTicketPageComponent,
    data: { roles: ['AGENT'] }
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
    path: 'ticket/:ticketId/record',
    component: TicketRecordPageComponent,
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  }
];
