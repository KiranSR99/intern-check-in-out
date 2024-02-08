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
  currentPage: number = 1;
  itemsPerPage: number = 5;
  userDetailsData: any[] = [];
  searchText: any;
  userRole: any;
  userId: any;
  supervisor: any;
  internsOfSupervisor: any;

  constructor(
    private httpHandler: HttpHandlerService,
    private toast: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchUserDetails();
    this.showIntersOfSupervisor();

    this.userId = localStorage.getItem('userId');
    this.userRole = localStorage.getItem('role');
    if (this.userRole) {
      this.userRole = JSON.parse(this.userRole);
    }
  }

  fetchUserDetails() {
    this.httpHandler.getAllUsers().subscribe(
      (data: any) => {
        this.userDetailsData = data.data;
        console.log('Data fetched successfully: '+ this.userDetailsData);
      },
      (error: any) => {
        // console.error('Error fetching user details:', error);
      }
    );
  }

  showIntersOfSupervisor() {
    this.httpHandler.showInternsOfSupervisor().subscribe({
      next: (response: any) => {
        this.supervisor = response.data.find(
          (item: any) => item.userId == this.userId
        );

        this.internsOfSupervisor = this.supervisor
          ? this.supervisor.internInfoResponses
          : [];
        console.log('These are the interns:', this.internsOfSupervisor);
      },
      error: (error: any) => {
        this.toast.showError(error.message);
      },
    });
  }

  loadInterns() {
    this.showIntersOfSupervisor();
    this.userDetailsData = this.internsOfSupervisor;
  }
}
