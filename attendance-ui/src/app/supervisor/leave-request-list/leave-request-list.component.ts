import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-leave-request-list',
  templateUrl: './leave-request-list.component.html',
  styleUrl: './leave-request-list.component.scss',
})
export class LeaveRequestListComponent {
  currentPage: number = 1;
  itemsPerPage: number = 5;
  userId: any;
  userRole: any;
  responseData: any;

  constructor(
    private router: Router,
    private httpHandler: HttpHandlerService,
    private toast: ToastService
  ) {}

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId');
    this.userRole = localStorage.getItem('role');

    if (this.userRole) {
      this.userRole = JSON.parse(this.userRole);
    }

    this.showLeaveRequests();
  }

  showLeaveRequests() {
    // To show leave requests of an intern
    this.httpHandler.showAllLeaveRequests().subscribe({
      next: (response: any) => {
        this.responseData = response.data;
      },
      error: (error: any) => {
        console.error('Error fetching leave requests:', error);
      },
    });
  }

  onApproveClick(id: any) {
    this.httpHandler.approveLeaveRequest(id).subscribe({
      next: (response: any) => {
        this.toast.showSuccess('Leave request approved.');
        this.showLeaveRequests();
      },
      error: (error: any) => {
        this.toast.showError('Unable to approve leave request');
      },
    });
  }

  onDeclineClick(id: any) {
    this.httpHandler.declineLeaveRequest(id).subscribe({
      next: (response: any) => {
        this.toast.showSuccess('Leave request declined.');
        this.showLeaveRequests();
      },
      error: (error: any) => {
        this.toast.showError('Unable ot decline leave request.');
      },
    });
  }
}
