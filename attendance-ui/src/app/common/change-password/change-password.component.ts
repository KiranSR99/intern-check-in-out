import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  passwordDetails: any;
  isLoggedIn: boolean = true;
  passwordStrengthSuggestions: string[] = [];
  passwordVisibility: boolean = false;
  confirmPasswordVisibility: boolean = false;


  constructor(private formBuilder: FormBuilder,
              private http: HttpHandlerService,
              private toast: ToastService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.passwordDetails = this.formBuilder.group({
      email: [localStorage.getItem("email")],
      newPassword: ['', Validators.required],
      confirmPassword: ['', Validators.required, [this.confirmPasswordValidator.bind(this)]]
    });
  }

  confirmPasswordValidator(control: AbstractControl): Promise<{ [key: string]: boolean } | null> {
    this.passwordStrengthSuggestions = this.checkPasswordStrength(control.value);
    const newPassword = this.passwordDetails.get('newPassword').value;
    const confirmPassword = control.value;

    return new Promise((resolve) => {
      // Simulate an asynchronous operation
      setTimeout(() => {
        // Checking if the passwords match
        const isValid = newPassword === confirmPassword;
        // Resolve the promise with the result
        resolve(isValid ? null : { 'passwordMismatch': true });
      }, 0);
    });
  }

  checkOldPassword(control: AbstractControl): { [key: string]: boolean } | null {
    const oldPassword = control.value;

    // Check if the old password is correct (you may need to call an API to verify this)
    const isOldPasswordCorrect = true; // Replace with actual logic

    return this.isLoggedIn && isOldPasswordCorrect ? null : { 'incorrectOldPassword': true };
  }

  changePassword(passwordDetails: any) {

    console.log(passwordDetails);
    this.http.updatePassword(passwordDetails).subscribe({
      next: (response: any) => {
        console.log(response);
        this.toast.showSuccess('Password changed successfully');
        localStorage.removeItem('email');
        this.router.navigate(['/login']);
      },
      error: (err: any) => {
        console.log(err);
        this.toast.showError(err.error.message);
      }
    });
  }
  
  checkPasswordStrength(password: string): string[] {
    const suggestions: string[] = [];

    if (!/(?=.*[a-z])(?=.*[A-Z])/.test(password)) {
      suggestions.push('Add Uppercase and one lowercase letter');
    }
    if (!/\d/.test(password)) {
      suggestions.push('Minimum one digit');
    }

    if (!/[@#$%^&*-_+={}:;"'|<,>.?/~]/.test(password)) {
      suggestions.push('Special character ');
    }

    if (password.length < 8) {
      suggestions.push('Must be 8 characters');
    }

    return suggestions;
  }

  togglePasswordVisibility(): void {
    this.passwordVisibility = !this.passwordVisibility;
  }
 
  toggleConfirmPasswordVisibility(): void {
    this.confirmPasswordVisibility = !this.confirmPasswordVisibility;
  }

  isChangePasswordEnabled(): boolean {
    // Check if the form is valid
    if (!this.passwordDetails.valid) {
      return false;
    }

    // Check if the password meets specific criteria
    const password = this.passwordDetails.get('newPassword')?.value;
    if (!/(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&*-_+={}:;"'|<,>.?/~])/.test(password)) {
      return false;
    }

    return true;
  }
}
