import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'],
})
export class UserDetailsComponent {
  userDetailsData: any[] = [];

  constructor(
    private httpHandler: HttpHandlerService,
    private toast: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchUserDetails();
  }

  fetchUserDetails() {
    this.httpHandler.getAllUsers().subscribe(
      (response: any) => {
        console.log('Data fetched successfully');
        this.userDetailsData = response.data;
      },
      (error: any) => {
        console.error('Error fetching user details:', error);
      }
    );
  }

  onDeleteUserClick(id: number) {
    if (confirm('Do you want to delete data?')) {
      this.httpHandler.deleteUserById(id).subscribe({
        next: (response: any) => {
          this.toast.showSuccess('Data Deleted Successfully');
          console.log('Data Deleted successfully:', response);

          this.fetchUserDetails();
        },
        error: (error: any) => {
          this.toast.showError(error.error.data);
          console.log('Error deleting data:', error.error.data);
        },
      });
    }
  }

  addUser() {
    this.router.navigate(['app/user-mgnt/add-user']);
  }

  onUpdateClick(userId: any) {
    this.router.navigate(['/app/user-mgnt/update-user/', userId]);
  }
}
