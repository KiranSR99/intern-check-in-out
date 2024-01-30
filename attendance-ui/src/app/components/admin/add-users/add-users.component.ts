import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

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
    private fb: FormBuilder
  ) {
    this.userDetails = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      username: ['', Validators.required],
      phoneNum: ['', Validators.required],
      password: ['', Validators.required],
      roleId: ['', Validators.required],
      field: ['', Validators.required]
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

  onRoleChange(event: Event) {
    const roleId = (event.target as HTMLSelectElement).value;
    // Reset the field value when a new role is selected
    if (roleId !== '3') { // If not Intern role
      this.userDetails.patchValue({ field: '' });
    }
  }

  showFieldInput(): boolean {
    // Show the field input only when Intern role is selected
    return this.userDetails.get('roleId')?.value === '3';
  }
}
