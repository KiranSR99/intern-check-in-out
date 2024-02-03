import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
})
export class ForgotPasswordComponent implements OnInit{

  constructor(private http:HttpHandlerService,
              private formbuilder: FormBuilder,
              private toast: ToastrService,
              private router: Router){}
  emailDeatil: any;
  ngOnInit(): void {
    this.emailDeatil = this.formbuilder.group({
      email: ['', Validators.required],
    });
  }
  forgotPassword(email: any) {
    console.log(email);

    this.http.forgotPassword(email).subscribe(
      (response: any) => {
        this.emailDeatil.reset();
        this.toast.success('Otp sent in your email');
        this.router.navigate(['/enter-otp']);
        console.log('email: ', email.email);
        console.log('response: ', response);
        localStorage.setItem('email', email.email);
      },
      (error: any) => {
        console.log(error);
        this.toast.error(error.error.message);
      }
    );
  }

  back(){
    this.router.navigate(['/login']);
  }
  
}
