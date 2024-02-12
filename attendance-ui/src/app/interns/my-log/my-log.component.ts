import { Component, OnInit } from '@angular/core';
import { HttpHandlerService } from '../../services/http-handler.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-log',
  templateUrl: './my-log.component.html',
  styleUrl: './my-log.component.scss',
})
export class MyLogComponent implements OnInit {
  page: number = 1;
  size: number = 10;
  userId: any;
  taskDetails: any;
  checkInOutDetail: any;
  pageDetails: any;

  constructor(
    private httpHandler: HttpHandlerService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId');

    this.showLogOfOneUser(this.page, this.size);

    //to get intern check in and out time
    this.httpHandler.getCheckInOutTime().subscribe({
      next: (response: any) => {
        this.checkInOutDetail = response.data;
      },
    });
  }

  showLogOfOneUser(page: number, size: number){
    this.httpHandler
    .getLogOfOneUser(this.userId, page, size)
    .subscribe({
      next: (response: any) => {
        this.taskDetails = response.data.content;
        this.pageDetails = response.data;
        console.log(this.taskDetails);
      },
    });
  }

  onEditClick(taskId: any) {
    this.router.navigate(['/app/log-mgnt/edit-log/', taskId]);
  }
}
