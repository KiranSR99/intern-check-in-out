import { Component, ViewChild, ElementRef } from '@angular/core';
import { HttpHandlerService } from '../services/http-handler.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-enter-otp',
  templateUrl: './enter-otp.component.html',
  styleUrls: ['./enter-otp.component.scss'],
})
export class EnterOtpComponent {
  @ViewChild('otp2') otp2: ElementRef | undefined;
  @ViewChild('otp3') otp3: ElementRef | undefined;
  @ViewChild('otp4') otp4: ElementRef | undefined;
  @ViewChild('otp5') otp5: ElementRef | undefined;
  @ViewChild('otp6') otp6: ElementRef | undefined;

  constructor(
    private http: HttpHandlerService,
    private formBuilder: FormBuilder,
    private toast: ToastService,
    private router: Router
  ) {}
  otpDetails: any;
  ngOnInit(): void {
    this.otpDetails = this.formBuilder.group({
      otp1: ['', Validators.required],
      otp2: ['', Validators.required],
      otp3: ['', Validators.required],
      otp4: ['', Validators.required],
      otp5: ['', Validators.required],
      otp6: ['', Validators.required],
    });
  }

  onOtpInput(event: any, nextInput: number) {
    const inputLength = event.target.value.length;
    if (inputLength === 1 && nextInput <= 6) {
      switch (nextInput) {
        case 2:
          this.otp2?.nativeElement.focus();
          break;
        case 3:
          this.otp3?.nativeElement.focus();
          break;
        case 4:
          this.otp4?.nativeElement.focus();
          break;
        case 5:
          this.otp5?.nativeElement.focus();
          break;
        case 6:
          this.otp6?.nativeElement.focus();
          break;
        default:
          break;
      }
    }
  }

  testOtp(data: any) {
    const email = localStorage.getItem('email');
    const concatenatedOTP = Object.values(data).join('');
    
    data.email = email;
    data.otp = concatenatedOTP;

    this.http.checkOtp(data).subscribe({
      next: (response: any) => {
        this.otpDetails.reset();
        this.toast.showSuccess("OTP Validation Successful.");
        this.router.navigate(['/change-password']);
      },
      error: (error: any) => {
        console.log(error);
        this.toast.showError(error.error.message);
      },
    });
  }
}
