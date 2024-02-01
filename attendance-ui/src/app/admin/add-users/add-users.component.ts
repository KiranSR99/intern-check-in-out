import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-add-users',
  templateUrl: './add-users.component.html',
  styleUrls: ['./add-users.component.scss']
})
export class AddUsersComponent {
  userDetails: FormGroup;
  roles: any[] = [
    { id: 'ADMIN', name: 'Admin' },
    { id: 'SUPERVISOR', name: 'Supervisor' },
    { id: 'INTERN', name: 'Intern' }
  ];

  constructor(
    private toast: ToastService,
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
      fieldType: [''],
      primarySupervisor: [''],
      secondarySupervisor: ['']
    });
  }

  cancel() {
    this.router.navigate(['/']);
  }

  onSubmit() {
    if (this.userDetails.valid) {
      this.http.addUser(this.userDetails.value).subscribe({
        next: (response: any) => {
          this.toast.showSuccess('User added successfully');
          this.router.navigate(['/app/user-mgnt/user-list']);
        },
        error: (err) => {
          console.error('Error adding user:', err);
          this.toast.showError('Error adding user');
        }
      });
    } else {
      this.userDetails.markAllAsTouched();
    }
  }

  onRoleChange(event: Event) {
    const roleId = (event.target as HTMLSelectElement).value;

    if (roleId === 'INTERN') {
      this.userDetails.get('fieldType')?.enable();
      this.userDetails.get('primarySupervisor')?.enable();
      this.userDetails.get('secondarySupervisor')?.enable();
    } else {
      this.userDetails.get('fieldType')?.reset();
      this.userDetails.get('fieldType')?.disable();
      this.userDetails.get('primarySupervisor')?.reset();
      this.userDetails.get('primarySupervisor')?.disable();
      this.userDetails.get('secondarySupervisor')?.reset();
      this.userDetails.get('secondarySupervisor')?.disable();
    }
  }

  showFieldInput(): boolean {
    const roleId = this.userDetails.get('role')?.value;
    return roleId === 'INTERN';
  }

  showPrimarySupervisor(): boolean {
    const roleId = this.userDetails.get('role')?.value;
    return roleId === 'INTERN';
  }

  showSecondarySupervisor(): boolean {
    const roleId = this.userDetails.get('role')?.value;
    return roleId === 'INTERN';
  }
}
