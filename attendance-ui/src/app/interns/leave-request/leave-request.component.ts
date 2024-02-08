import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';

@Component({
  selector: 'app-leave-request',
  templateUrl: './leave-request.component.html',
  styleUrl: './leave-request.component.scss',
})
export class LeaveRequestComponent implements OnInit {
  page: number = 1;
  size: number = 5;
  pageDetails: any;
  userId: any;
  userRole: any;
  responseData: any;

  constructor(
    private router: Router,
    private httpHandler: HttpHandlerService
  ) {}

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId');
    this.userRole = localStorage.getItem('role');

    if (this.userRole) {
      this.userRole = JSON.parse(this.userRole);
    }

    // To show leave requests of an intern
    this.httpHandler.showInternLeaveRequests(this.userId).subscribe({
      next: (response: any) => {
        this.responseData = response.data;
      },
      error: (error: any) => {
        console.error('Error fetching leave requests:', error);
      },
    });
  }

  onComposeNewClick(userId: any) {
    this.router.navigate(['/app/log-mgnt/create-new/', userId]);
  }

  onApproveClick(id: any) {}
}
