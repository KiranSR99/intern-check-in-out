import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  userRole: any;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.userRole = localStorage.getItem('role');

    if (this.userRole) {
      this.userRole = JSON.parse(this.userRole);
    }
  }

  logout() {
    // Remove token from local storage
    localStorage.clear();
    this.router.navigate(['/']);
  }

  onLeaveRequestClick() {
    if(this.userRole == 'INTERN'){
      this.router.navigate(['/app/log-mgnt/leave-request'])
    }
    else{
      this.router.navigate(['/app/log-mgnt/leave-request-list'])
    }
  }
}
