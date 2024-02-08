import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import { HttpHandlerService } from '../services/http-handler.service';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})


export class LoginComponent implements OnInit {
  button: any;
  loginDetail!: FormGroup;
  token: string = '';
  passwordVisibility: boolean = false;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private httpHandlerService: HttpHandlerService,
    private toast: ToastService
  ) {}
  
  ngOnInit(): void {
    this.loginDetail = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onLoginClick(loginDetail: any){
    this.httpHandlerService.loginUser(loginDetail).subscribe(
      (response: any) => {
        this.token = response.data.token;
        localStorage.setItem("token", this.token);
        localStorage.setItem("role", JSON.stringify(response.data.role));
        localStorage.setItem("userId", response.data.userId);
        this.router.navigate(['/app/dashboard']);
        console.log("Login successful.");
        
      },
      (error: any) => {
        console.log('Error in backend', error);
        this.toast.showError("Invalid Credentials");
      }
    );
    
  }
  
  togglePasswordVisibility(): void {
    this.passwordVisibility = !this.passwordVisibility;
  }

  
}
