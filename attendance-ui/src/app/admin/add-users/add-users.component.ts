import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, ValidationErrors, ValidatorFn, FormGroup, Validators, FormControl } from "@angular/forms";
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';

export function strongPasswordValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const password: string = control.value;
    const isStrongPassword: boolean = /^(?=.*[@#])(?=.*\d)[A-Za-z\d@#]+$/.test(password);
    return isStrongPassword ? null : { 'weakPassword': true };
  };
}

@Component({
  selector: 'app-add-users',
  templateUrl: './add-users.component.html',
  styleUrls: ['./add-users.component.scss'],
})
export class AddUsersComponent implements OnInit {
  userDetails: FormGroup;
  supervisors: any;
  passwordVisibility: boolean = false;


  roles: any[] = [
    { id: 'ADMIN', name: 'Admin' },
    { id: 'SUPERVISOR', name: 'Supervisor' },
    { id: 'INTERN', name: 'Intern' },
  ];

  constructor(
    private toast: ToastService,
    private router: Router,
    private fb: FormBuilder,
    private http: HttpHandlerService
  ) {
    this.userDetails = this.fb.group({
      fullName: ['', Validators.required],
      phone: ['', [Validators.required, this.phoneNumberValidator]],
      email: ['', [Validators.required, this.emailPatternValidator]],
      password: ['', [Validators.required, strongPasswordValidator()]],
      role: ['', Validators.required],
      fieldType: [''],
      primarySupervisor: [''],
      secondarySupervisor: [''],
    });
  }

  ngOnInit(): void {
    this.showAllSupervisors();
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
        },
      });
    } else {
      this.userDetails.markAllAsTouched();
    }
  }

  //To get all supervisors
  showAllSupervisors() {
    this.http.getAllSupervisors().subscribe({
      next: (response: any) => {
        this.supervisors = response.data;
        console.log(this.supervisors);
      },
    });
  }

  onRoleChange(event: Event) {
    const roleId = (event.target as HTMLSelectElement).value;
    if (roleId !== 'INTERN') {
      this.userDetails.patchValue({ fieldType: null });
      this.userDetails.patchValue({ primarySupervisor: null });
      this.userDetails.patchValue({ secondarySupervisor: null });
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

  emailPatternValidator(control: FormControl): { [key: string]: boolean } | null {
    const email: string = control.value;
    const emailPattern: RegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    return emailPattern.test(email) ? null : { emailPatternError: true };
  }

  phoneNumberValidator(control: FormControl): { [key: string]: boolean } | null {
    const phone: string = control.value;
    const phonePattern: RegExp = /^\d{10}$/;

    return phonePattern.test(phone) ? null : { phonePatternError: true };
  }

  togglePasswordVisibility(): void {
    this.passwordVisibility = !this.passwordVisibility;
  }
}
