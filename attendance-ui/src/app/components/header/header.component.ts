import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { HttpHandlerService } from '../../services/http-handler.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  userRole: any;
  userName: any;
  userId: any;

  constructor(
    private router: Router,
    private userService: UserService,
    private httpHandler: HttpHandlerService
  ) {}

  ngOnInit(): void {
    this.userRole = localStorage.getItem('role');
    this.userId = localStorage.getItem('userId');

    if (this.userRole) {
      this.userRole = JSON.parse(this.userRole);
    }

    //To get user detail
    this.httpHandler.getUserById(this.userId).subscribe({
      next: (response: any) => {
        this.userName = response.data.fullName;
      },
    });

    //getting the username from the userService
    this.userService.userName$.subscribe((newUserName) => {
      this.userName = newUserName;
    });
  }

  logout() {
    // Remove token from local storage
    localStorage.clear();
    this.router.navigate(['/']);
  }

  onProfileClick(userId: any){
    this.router.navigate(['/app/user-mgnt/user-profile/', userId])
  }
}
