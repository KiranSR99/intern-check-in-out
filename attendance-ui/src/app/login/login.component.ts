import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { HttpHandlerService } from '../services/http-handler.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{

button: any;
myform!: FormGroup;



  constructor( private router: Router, private formBuilder: FormBuilder,private httpHandlerService: HttpHandlerService)  { }
ngOnInit(): void {
    this.myform=this.formBuilder.group({
      username : ['', Validators.required],
      password : ['', Validators.required]
    });
}
token : string = ''
loginUser() {
  this.httpHandlerService.loginUser(this.myform.value).subscribe(
    (response: any) => {
      this.token= response.data.token;
      localStorage.setItem("token", this.token)
      if (!this.token) {
        alert('Invalid Credentials');
        } else {
         this.router.navigate(['/login']);
          }
    },
    (error: any) => {
      console.log('error in backend');
    }
  );
  
}
}
