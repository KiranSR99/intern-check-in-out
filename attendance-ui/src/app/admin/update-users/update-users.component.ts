import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastService } from '../../services/toast.service';

@Component({
    selector: 'app-update-users',
    templateUrl: './update-users.component.html',
    styleUrls: ['./update-users.component.scss'],
})
export class UpdateUsersComponent implements OnInit {
    userDetails: FormGroup;
    userId: any;

    roles: any[] = [
        { id: 'ADMIN', name: 'Admin' },
        { id: 'SUPERVISOR', name: 'Supervisor' },
        { id: 'INTERN', name: 'Intern' },
    ];

    constructor(
        private toast: ToastService,
        private router: Router,
        private fb: FormBuilder,
        private http: HttpHandlerService,
        private activatedRoute: ActivatedRoute
    ) {
        this.userDetails = this.fb.group({
            id: null,
            fullName: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            phone: ['', Validators.required],
            fieldType: [''],
        });
    }

    ngOnInit(): void {
        this.activatedRoute.params.subscribe((params: any) => {
            this.userId = +params['id'];
            this.http.getUserById(this.userId).subscribe((response) => {
                this.patchingData(response.data);
            });
        });
    }

    patchingData(data: any) {
        this.userDetails.patchValue({
            id: this.userId,
            fullName: data.fullName,
            email: data.email,
            phone: data.phone,
            fieldType: data.fieldType,
        });
    }

    cancel() {
        this.router.navigate(['/app/user-mgnt/user-list']);
    }

    onSubmit(data: any) {
        if (this.userDetails.valid) {
<<<<<<< HEAD
            const updatedUserData = {
                userId: this.userId,
                userDetails: this.userDetails.value
            };

            this.http.updateUser(updatedUserData).subscribe({
=======
            this.http.updateUser(data).subscribe({
>>>>>>> c00b90e770a8cf1dfab09800fd9c6fc0eaf544a6
                next: (response: any) => {
                    console.log('User updated successfully');
                    this.toast.showSuccess('User updated successfully');
                    this.router.navigate(['/app/user-mgnt/user-list']);
                },
                error: (err) => {
                    console.error('Error updating user:', err);
                    this.toast.showError('Error updating user');
                },
            });
        } else {
            this.userDetails.markAllAsTouched();
        }
    }
<<<<<<< HEAD

=======
>>>>>>> c00b90e770a8cf1dfab09800fd9c6fc0eaf544a6

    onRoleChange(event: Event) {
        const roleId = (event.target as HTMLSelectElement).value;
        if (roleId !== 'INTERN') {
            this.userDetails.patchValue({ fieldType: null });
        }
    }

    showFieldInput(): boolean {
        const roleId = this.userDetails.get('role')?.value;
        return roleId === 'INTERN';
    }
}
