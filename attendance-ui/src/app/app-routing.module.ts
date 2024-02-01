import { LoginComponent } from './login/login.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BaseComponent } from './components/base/base.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AddUsersComponent } from './admin/add-users/add-users.component';
import { UserDetailsComponent } from './admin/user-details/user-details.component';
import { AddLogComponent } from './interns/add-log/add-log.component';
import { InternLogComponent } from './supervisor/log-details/intern-log/intern-log.component';
import { UserProfileComponent } from './common/user-profile/user-profile.component';
import { EditProfileComponent } from './common/edit-profile/edit-profile.component';
import { ChangePasswordComponent } from './common/change-password/change-password.component';
import { ForgotPasswordComponent } from './common/forgot-password/forgot-password.component';
import { authGuard } from './guards/auth.guard';
import { negateAuthGuard } from './guards/negate-auth.guard';
import { UpdateUsersComponent } from './admin/update-users/update-users.component';

const routes: Routes = [
  { path: '', redirectTo: '', pathMatch: 'full' },
  { path: '', component: LandingPageComponent, canActivate: [negateAuthGuard] },
  { path: 'login', component: LoginComponent, canActivate: [negateAuthGuard] },
  { path: 'change-password', component: ChangePasswordComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
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
          { path: 'update-user/:id', component: UpdateUsersComponent },
          { path: 'user-profile', component: UserProfileComponent },
          { path: 'edit-profile', component: EditProfileComponent },
          { path: '**', redirectTo: 'user-list', pathMatch: 'full' },
        ],
      },
      {
        path: 'log-mgnt',
        children: [
          { path: 'add-log', component: AddLogComponent },
          { path: 'intern-log', component: InternLogComponent },
          {
            path: '**',
            redirectTo: 'intern-log',
            pathMatch: 'full',
          },
        ],
      },
      {
        path: 'super-mgnt',
        children: [],
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
