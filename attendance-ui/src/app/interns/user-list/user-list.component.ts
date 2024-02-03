import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss',
})
export class UserListComponent {
  userDetailsData: any[] = [];
  searchText: any;

  constructor(
    private httpHandler: HttpHandlerService,
    private toast: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchUserDetails();
  }

  fetchUserDetails() {
    this.httpHandler.getAllUsers().subscribe(
      (data: any) => {
        console.log('Data fetched successfully');
        this.userDetailsData = data.data;
      },
      (error: any) => {
        console.error('Error fetching user details:', error);
      }
    );
  }
}
