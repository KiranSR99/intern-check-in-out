import { LoginComponent } from './login/login.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BaseComponent } from './components/base/base.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AddUsersComponent } from './admin/add-users/add-users.component';
import { UpdateUsersComponent } from './admin/update-users/update-users.component';
import { UserDetailsComponent } from './admin/user-details/user-details.component';

import { AddLogComponent } from './interns/add-log/add-log.component';

import { InternLogComponent } from './supervisor/log-details/intern-log/intern-log.component';
import { authGuard } from './guards/auth.guard';
import { negateAuthGuard } from './guards/negate-auth.guard';


const routes: Routes = [
  { path: '', redirectTo: '', pathMatch: 'full' },
  { 
    path: '', 
    component: LandingPageComponent, 
    canActivate: [negateAuthGuard] 
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [negateAuthGuard]
   },
  {
    path: 'app',
    component: BaseComponent,
    canActivate: [authGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      {
        path: 'user-mgnt',
        children: [
          { path: 'user-list', component: UserDetailsComponent },
          { path: 'add-user', component: AddUsersComponent },
          { path: 'edit-user/:id', component: UpdateUsersComponent },
          { path: '**', redirectTo: 'user-list', pathMatch: 'full' },
        ],
      },
      {
        path: 'log-mgnt',
        children: [
          {path: 'add-log', component: AddLogComponent},
          {path: 'intern-log', component: InternLogComponent},
          {
            path: '**',
            redirectTo: 'intern-log',
            pathMatch: 'full',
          },
        ],
      },
      {
        path: 'super-mgnt',
        children: [
        ],
      },
      {
        path: '**',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
