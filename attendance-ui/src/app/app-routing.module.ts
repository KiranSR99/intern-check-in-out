import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserDetailsComponent } from './components/admin/user-details/user-details.component';
import { AddUsersComponent } from './components/admin/add-users/add-users.component';
import { UpdateUsersComponent } from './components/admin/update-users/update-users.component';

const routes: Routes = [
  {
    path: 'user-management',
    children: [
      {
        path: 'user-list',
        component: UserDetailsComponent,
      },
      {
        path: 'add-users',
        component: AddUsersComponent,
      },
      {
        path: 'update-users',
        component: UpdateUsersComponent,
      }

    ]
  }







];




import { LoginComponent } from './login/login.component';
import { LandingPageComponent } from './landing-page/landing-page.component';


const routes: Routes = [
  {
    path: '', component: LandingPageComponent},
  
  { path: 'login', component: LoginComponent },

  
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
