import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { HttpHandlerService } from '../../services/http-handler.service';
import { UserService } from '../../services/user.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.scss',
})
export class EditProfileComponent {
  teacherDetails: any;
  role: any;
  id: any;
  constructor(
    private formbuilder: FormBuilder,
    private router: Router,
    private location: Location,
    private http: HttpHandlerService,
    private activatedRoutes: ActivatedRoute,
    private toastr: ToastrService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.teacherDetails = this.formbuilder.group({
      fullName: ['', Validators.required],
      email: ['', Validators.required],
      phone: ['', Validators.required],
      id: [''],
    });

    this.populatingFields();
  }

  populatingFields() {
    this.activatedRoutes.params.subscribe((params) => {
      this.id = params['id'];
    });
    this.http.getUserById(this.id).subscribe({
      next: (response: any) => {
        this.patchingData(response.data);
      },
    });
  }

  patchingData(data: any) {
    this.teacherDetails.patchValue({
      fullName: data.fullName,
      email: data.email,
      phone: data.phone,
    });
  }

  onUpdateClick(data: any) {
    data.id = this.id;
    console.log(data);
    this.http.updateUser(data).subscribe({
      next: (response: any) => {
        console.log('User Updated successfully');
        this.toastr.success('Profile Updated successfully');
        this.location.back();
        this.teacherDetails.reset();
      },
      error: (error: any) => {
        console.log('User update api error', error);
      },
    });

    this.userService.updateUserName(data.fullName);
  }
}
