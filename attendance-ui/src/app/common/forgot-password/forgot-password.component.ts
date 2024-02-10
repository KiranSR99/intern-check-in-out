import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
})
export class ForgotPasswordComponent implements OnInit{

  constructor(private http:HttpHandlerService,
              private formBuilder: FormBuilder,
              private toast: ToastService,
              private router: Router){}
  emailDetail: any;
  ngOnInit(): void {
    this.emailDetail = this.formBuilder.group({
      email: ['', Validators.required],
    });
  }
  forgotPassword(email: any) {
    console.log(email);

    this.http.forgotPassword(email).subscribe(
      (response: any) => {
        this.emailDetail.reset();
        this.toast.showShow('OTP sent in your email');
        this.router.navigate(['/enter-otp']);
        console.log('email: ', email.email);
        console.log('response: ', response);
        localStorage.setItem('email', email.email);
      },
      (error: any) => {
        console.log(error);
        this.toast.showError(error.error.message);
      }
    );
  }

  back(){
    this.router.navigate(['/login']);
  }
  
}
