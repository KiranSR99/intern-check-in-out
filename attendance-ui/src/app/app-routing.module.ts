import { LoginComponent } from './login/login.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BaseComponent } from './components/base/base.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UserDetailsComponent } from './components/admin/user-details/user-details.component';
import { AddUsersComponent } from './components/admin/add-users/add-users.component';
import { UpdateUsersComponent } from './components/admin/update-users/update-users.component';

const routes: Routes = [
  { path: '', redirectTo: '', pathMatch: 'full' },
  { path: '', component: LandingPageComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'app',
    component: BaseComponent,
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
