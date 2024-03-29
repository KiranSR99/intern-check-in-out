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
import { InternLogComponent } from './supervisor/intern-log/intern-log.component';
import { UserProfileComponent } from './common/user-profile/user-profile.component';
import { AddUsersComponent } from './admin/add-users/add-users.component';
import { UpdateUsersComponent } from './admin/update-users/update-users.component';
import { UserDetailsComponent } from './admin/user-details/user-details.component';
import { EditProfileComponent } from './common/edit-profile/edit-profile.component';
import { ChangePasswordComponent } from './common/change-password/change-password.component';
import { ForgotPasswordComponent } from './common/forgot-password/forgot-password.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { LeaveRequestComponent } from './interns/leave-request/leave-request.component';
import { CreateLeaveRequestComponent } from './interns/create-leave-request/create-leave-request.component';
import { SearchFilterPipe } from './pipe/search-filter.pipe';
import { EditLogComponent } from './interns/edit-log/edit-log.component';
import { EnterOtpComponent } from './enter-otp/enter-otp.component';
import { LeaveRequestListComponent } from './supervisor/leave-request-list/leave-request-list.component';
import { DateFormatterPipe } from './pipe/date-formatter.pipe';
import { TimeFormatterPipe } from './pipe/time-formatter.pipe';
import { UserListComponent } from './interns/user-list/user-list.component';
import { AccessDeniedComponent } from './common/access-denied/access-denied.component';
import { CustomDateTimeFormatterPipe } from './pipe/date-time-formatter.pipe';
import { FieldFormatterPipe } from './pipe/field-formatter.pipe';
import { MyLogComponent } from './interns/my-log/my-log.component';
import { DateFilterPipe } from './pipe/date-restriction.pipe';
import { NgxPaginationModule } from 'ngx-pagination';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { MatMenuModule } from '@angular/material/menu';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

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
    InternLogComponent,
    AddLogComponent,
    UserProfileComponent,
    EditProfileComponent,
    ChangePasswordComponent,
    ForgotPasswordComponent,
    EditLogComponent,
    LeaveRequestComponent,
    CreateLeaveRequestComponent,
    SearchFilterPipe,
    UpdateUsersComponent,
    EnterOtpComponent,
    LeaveRequestListComponent,
    DateFormatterPipe,
    TimeFormatterPipe,
    UserListComponent,
    AccessDeniedComponent,
    CustomDateTimeFormatterPipe,
    FieldFormatterPipe,
    MyLogComponent,
    DateFilterPipe,
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
    NgxPaginationModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatMenuModule
  ],

  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    provideAnimationsAsync(),
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
