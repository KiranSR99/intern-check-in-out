import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss',
})
export class UserProfileComponent {
  userProfile: any;
  userId: any;
  id: any;
  roleName: any;

  constructor(
    private activatedRoute: ActivatedRoute,
    private http: HttpHandlerService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.id = localStorage.getItem('userId');

    this.activatedRoute.params.subscribe((params) => {
      this.userId = params['userId'];

      this.getUserProfile(this.id);

      const role = localStorage.getItem('role');

      if (role) {
        this.roleName = JSON.parse(role);
      }
    });
  }

  getUserProfile(userId: any): void {
    this.http.getUserById(userId).subscribe({
      next: (response: any) => {
        this.userProfile = response.data;
        console.log('User profile:', this.userProfile);
      },
      error: (error: any) => {
        console.log('Error fetching user profile:', error);
      },
    });
  }
  editProfileById(userId: any): void {
    console.log('Button clicked. Navigating to profile-edit.');
    this.router.navigate(['/app/user-mgnt/edit-profile/', this.id]);
  }

  cancel() {
    this.router.navigate(['/app/user-mgnt/show-user']);
  }
}
