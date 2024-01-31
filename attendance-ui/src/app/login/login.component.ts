// login.component.ts

import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import { HttpHandlerService } from '../services/http-handler.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  loginDetail: FormGroup;

  constructor(private fb: FormBuilder, private httpHandlerService: HttpHandlerService, private router: Router) {
    this.loginDetail = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', Validators.required],
    });
  }
  token: string = '';

 
    login() {
      this.httpHandlerService.loginUser(this.loginDetail.value).subscribe(
        (response: any) => {
          this.token = response.data.token;
          localStorage.setItem("token", this.token);
          localStorage.setItem("roleDetails", JSON.stringify(response.data.role));
          localStorage.setItem("userId", response.data.userId);
          this.router.navigate(['/dashboard']);
        },
        (error: any) => {
          console.log('Error in backend', error);
       
        }
      );
    }
}
