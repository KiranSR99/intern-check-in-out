import { Component } from '@angular/core';
import { HttpHandlerService } from '../services/http-handler.service';
import { FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-enter-otp',
  templateUrl: './enter-otp.component.html',
  styleUrls: ['./enter-otp.component.scss'],
})
export class EnterOtpComponent {
  constructor(
    private http: HttpHandlerService,
    private formbuilder: FormBuilder,
    private toast: ToastrService,
    private router: Router
  ) {}
  otpDetails: any;
  ngOnInit(): void {
    this.otpDetails = this.formbuilder.group({
      otp: ['', Validators.required],
    });
  }
  testOtp(data: any) {
  const email = localStorage.getItem('email');

  // Add email to the data object
  data.email = email;

  console.log(data);

  // Logic of sending email and password through email
  this.http.checkOtp(data).subscribe({
    next: (response: any) => {
      this.otpDetails.reset();
      this.toast.success("Otp Valid");
      this.router.navigate(['/change-password']);
    },
    error: (error: any) => {
      console.log(error);
      this.toast.error(error.error.message);
    },
  });
}

  
}
