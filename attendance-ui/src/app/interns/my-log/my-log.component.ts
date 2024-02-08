import { Component, OnInit } from '@angular/core';
import { HttpHandlerService } from '../../services/http-handler.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-log',
  templateUrl: './my-log.component.html',
  styleUrl: './my-log.component.scss',
})
export class MyLogComponent implements OnInit {
  currentPage: number = 1;
  itemsPerPage: number = 5;
  userId: any;
  taskDetails: any;
  checkInOutDetail: any;

  constructor(private httpHandler: HttpHandlerService, private router: Router) {}

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId');

    this.httpHandler.getLogOfOneUser(this.userId).subscribe({
      next: (response: any) => {
        this.taskDetails = response.data;
        console.log(this.taskDetails);
      },
    });

    //to get intern check in and out time
    this.httpHandler.getCheckInOutTime().subscribe({
      next: (response: any) => {
        this.checkInOutDetail = response.data;
      }
    });
  }

  onEditClick(taskId: any){
    this.router.navigate(['/app/log-mgnt/edit-log/', taskId]);
  }
}
