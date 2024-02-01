import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-leave-request',
  templateUrl: './leave-request.component.html',
  styleUrl: './leave-request.component.scss'
})
export class LeaveRequestComponent implements OnInit {
  userId: any;

  constructor(private router: Router){}

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId');
  }

onComposeNewClick(userId: any){
  this.router.navigate(['/app/log-mgnt/create-new/', userId]);
}

}
