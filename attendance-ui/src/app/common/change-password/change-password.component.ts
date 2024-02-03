import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';

import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss'],
})
export class ChangePasswordComponent implements OnInit {
  passwordDetails: any;
  isLoggedIn: boolean = true; // Assume user is already logged in, set to false if old password is entered

  constructor(
    private formbuilder: FormBuilder,
    private http: HttpHandlerService,
    private toast: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.passwordDetails = this.formbuilder.group({
      email: [localStorage.getItem('email')],
      newPassword: ['', Validators.required],
      confirmPassword: [
        '',
        Validators.required,
        [this.confirmPasswordValidator.bind(this)],
      ],
    });
  }

  confirmPasswordValidator(
    control: AbstractControl
  ): Promise<{ [key: string]: boolean } | null> {
    const newPassword = this.passwordDetails.get('newPassword').value;
    const confirmPassword = control.value;

    return new Promise((resolve) => {
      // Simulate an asynchronous operation
      setTimeout(() => {
        // Checking if the passwords match
        const isValid = newPassword === confirmPassword;
        // Resolve the promise with the result
        resolve(isValid ? null : { passwordMismatch: true });
      }, 0);
    });
  }

  checkOldPassword(
    control: AbstractControl
  ): { [key: string]: boolean } | null {
    const oldPassword = control.value;

    // Check if the old password is correct (you may need to call an API to verify this)
    const isOldPasswordCorrect = true; // Replace with actual logic

    return this.isLoggedIn && isOldPasswordCorrect
      ? null
      : { incorrectOldPassword: true };
  }

  changePassword(passwordDetails: any) {
    console.log(passwordDetails);
    this.http.updatePassword(passwordDetails).subscribe({
      next: (response: any) => {
        console.log(response);
        this.toast.success('Password changed successfully');
        localStorage.clear();
        this.router.navigate(['/']);
      },
      error: (err: any) => {
        console.log(err);
        this.toast.error(err.error.message);
      },
    });
  }
}
