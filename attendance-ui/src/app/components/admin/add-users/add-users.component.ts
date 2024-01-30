import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; 

@Component({
  selector: 'app-add-users',
  templateUrl: './add-users.component.html',
  styleUrls: ['./add-users.component.scss']
})
export class AddUsersComponent {
  userDetails: FormGroup; 
  roles: any[] = [
    { id: 1, name: 'Admin' },
    { id: 2, name: 'User' },
   
  ];

  constructor(
    private toastr: ToastrService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.userDetails = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      username: ['', Validators.required],
      phoneNum: ['', Validators.required],
      password: ['', Validators.required],
      roleId: ['', Validators.required]
    });
  }

  cancel() {
    this.router.navigate(['/']);
  }

  onSubmit() {
    if (this.userDetails.valid) {
      // Perform form submission logic here
      this.toastr.success("User added successfully");
      this.router.navigate(['/']);
    } else {
      this.userDetails.markAllAsTouched();
    }
  }
}
