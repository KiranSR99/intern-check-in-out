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
  page: number = 1;
  size: number = 10;
  pageDetails: any;
  userDetailsData: any[] = [];
  collection: any;
  p1: string | number | undefined;
  p2: string | number | undefined;

  constructor(
    private httpHandler: HttpHandlerService,
    private toast: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchUserDetails(this.size, this.page);
  }

  fetchUserDetails(size: number, page: number) {
    this.httpHandler.getAllUsers(size, page).subscribe(
      (response: any) => {
        this.userDetailsData = response.data.content;
        this.pageDetails = response.data;
      },
      (error: any) => {
        this.toast.showError(error.error.message);
      }
    );
  }

  onDeleteUserClick(id: number) {
    if (confirm('Do you want to delete data?')) {
      this.httpHandler.deleteUserById(id).subscribe({
        next: (response: any) => {
          this.toast.showSuccess('Data Deleted Successfully');
          console.log('Data Deleted successfully:', response);

          this.fetchUserDetails(this.size, this.page);
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
