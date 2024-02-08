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
  page: number = 1;
  size: number = 5;
  pageDetails: any;
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
    this.fetchUserDetails(this.size, this.page);
    this.showIntersOfSupervisor();

    this.userId = localStorage.getItem('userId');
    this.userRole = localStorage.getItem('role');
    if (this.userRole) {
      this.userRole = JSON.parse(this.userRole);
    }
  }

  fetchUserDetails(size: number, page: number) {
    this.httpHandler.getAllUsers(this.size,this.page).subscribe(
      (data: any) => {
        this.userDetailsData = data.data.content;
        this.pageDetails = data.data;
        console.log("Hello this is total no of items = "+ this.pageDetails?.totalElements);
        
      },
      (error: any) => {
        this.toast.showError(error.error.message);
      }
    );
  }

  showIntersOfSupervisor() {
    this.httpHandler.showInternsOfSupervisor().subscribe({
      next: (response: any) => {
        this.supervisor = response.data.content.find(
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
