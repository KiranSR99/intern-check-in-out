import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { HttpHandlerService } from '../../services/http-handler.service';

@Component({
  selector: 'app-add-users',
  templateUrl: './add-users.component.html',
  styleUrls: ['./add-users.component.scss']
})
export class AddUsersComponent {
  userDetails: FormGroup;
  roles: any[] = [
    { id: 1, name: 'Admin' },
    { id: 2, name: 'Supervisor' },
    { id: 3, name: "Intern" }
  ];

  constructor(
    private toastr: ToastrService,
    private router: Router,
    private fb: FormBuilder,
    private http: HttpHandlerService
  ) {
    this.userDetails = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      password: ['', Validators.required],
      role: ['', Validators.required],
      fieldType: ['', Validators.required]
    });
  }

  cancel() {
    this.router.navigate(['/']);
  }

  onSubmit(data:any) {
    this.http.addUser(data).subscribe({
      next: (response: any)=>{
        console.log("user added successfully")
      }
    })
    if (this.userDetails.valid) {
      this.toastr.success("User added successfully");
      this.router.navigate(['/app/user-mgnt/user-list']);
    } else {
      this.userDetails.markAllAsTouched();
    }
  }

  onRoleChange(event: Event) {
    const roleId = (event.target as HTMLSelectElement).value;
    // Reset the field value when a new role is selected
    if (roleId !== '3') { // If not Intern role
      this.userDetails.patchValue({ field: '' });
    }
  }

  showFieldInput(): boolean {
    const roleId = this.userDetails.get('roleId')?.value;
    console.log('Selected Role ID:', roleId);
    return roleId === 3; 
  }
  
  
}
