import { Component, OnInit } from '@angular/core';
import { HttpHandlerService } from '../../services/http-handler.service';

@Component({
  selector: 'app-my-log',
  templateUrl: './my-log.component.html',
  styleUrl: './my-log.component.scss',
})
export class MyLogComponent implements OnInit {
  userId: any;
  taskDetails: any;

  constructor(private httpHandler: HttpHandlerService) {}

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId');

    this.httpHandler.getLogById(this.userId).subscribe({
      next: (response: any) => {
        this.taskDetails = response.data;
        console.log(this.taskDetails);
        
      }
    })
  }
}
