import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastService } from '../../services/toast.service';

@Component({
    selector: 'app-update-users',
    templateUrl: './update-users.component.html',
    styleUrls: ['./update-users.component.scss']
})

export class UpdateUsersComponent implements OnInit {
    userDetails: FormGroup;
    userId: any;

    roles: any[] = [
        { id: 'ADMIN', name: 'Admin' },
        { id: 'SUPERVISOR', name: 'Supervisor' },
        { id: 'INTERN', name: 'Intern' }
    ];

    constructor(
        private toast: ToastService,
        private router: Router,
        private fb: FormBuilder,
        private http: HttpHandlerService,
        private activatedRoute: ActivatedRoute
    ) {
        this.userDetails = this.fb.group({
            fullName: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            phone: ['', Validators.required],
            password: ['', Validators.required],
            role: ['', Validators.required],
            fieldType: ['']
        });
    }

    ngOnInit(): void {
        this.activatedRoute.params.subscribe((params: any) => {
            this.userId = params['userId'];
            this.http.getUserById(this.userId).subscribe(
                (response) => {
                    this.patchingData(response.data);
                    // this.userId(response.data)
                }
            );
        });
    }


    patchingData(data: any) {
        this.userDetails.patchValue({
            fullName: data.fullName,
            email: data.email,
            phone: data.phone,
            password: data.password
        })
    }

    cancel() {
        this.router.navigate(['/']);
    }

    onSubmit() {
        if (this.userDetails.valid) {
            this.http.updateUser(this.userDetails.value).subscribe({
                next: (response: any) => {
                    console.log('User added successfully');
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
        if (roleId !== 'INTERN') {
            this.userDetails.patchValue({ fieldType: '' });
        }
    }

    showFieldInput(): boolean {
        const roleId = this.userDetails.get('role')?.value;
        return roleId == 'INTERN';
    }
}