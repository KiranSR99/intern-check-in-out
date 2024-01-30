import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Toast, ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-users',
  templateUrl: './update-users.component.html',
  styleUrl: './update-users.component.scss'
})
export class UpdateUsersComponent {
userDetails: any;


constructor(
  private toastr: ToastrService,
  private router: Router
)
{}


}