import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { ToastrModule } from 'ngx-toastr';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { BaseComponent } from './components/base/base.component';
import { SideNavComponent } from './components/side-nav/side-nav.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { AddLogComponent } from './interns/add-log/add-log.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InternLogComponent } from './supervisor/log-details/intern-log/intern-log.component';
import { UserProfileComponent } from './common/user-profile/user-profile.component';
import { AddUsersComponent } from './admin/add-users/add-users.component';
import { UpdateUsersComponent } from './admin/update-users/update-users.component';
import { UserDetailsComponent } from './admin/user-details/user-details.component';
import { EditProfileComponent } from './common/edit-profile/edit-profile.component';
import { ChangePasswordComponent } from './common/change-password/change-password.component';
import { ForgotPasswordComponent } from './common/forgot-password/forgot-password.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './interceptors/auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    BaseComponent,
    SideNavComponent,
    DashboardComponent,
    LoginComponent,
    LandingPageComponent,
    UserDetailsComponent,
    AddUsersComponent,
    UpdateUsersComponent,
    InternLogComponent,
    AddLogComponent,
    UserProfileComponent,
    EditProfileComponent,
    ChangePasswordComponent,
    ForgotPasswordComponent

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    CommonModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
  ],

  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
