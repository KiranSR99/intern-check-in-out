import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { HttpHandlerService } from '../../services/http-handler.service';
// import { HttpHandlerService } from '../../../services/http-handler.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'] 
})
export class UserDetailsComponent {
  userDetailsData: any[] = []; 

  constructor(
    private httpHandler: HttpHandlerService,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.fetchUserDetails();
  }
  
  fetchUserDetails() {
    this.httpHandler.getAllUsers().subscribe(
      (data: any) => {
        console.log("Data fetched successfully")
        this.userDetailsData = data.data;
      },
      (error:any) => {
        console.error('Error fetching user details:', error);
      }
    );
  }

  deleteID(id: number) {
    if (confirm('Do you want to delete data?')) {
      this.httpHandler.deleteID(id).subscribe({
        next: (response: any)  => {
          this.toastr.success('Data Deleted Successfully');
          console.log('Data Deleted successfully:', response);

          this.fetchUserDetails(); 
        },
        error: (error: any) => {
          this.toastr.error(error.error.data);
          console.log('Error deleting data:', error.error.data);
        }
      });
    }
  }



  addUser() {
    this.router.navigate(['app/user-mgnt/add-user']);
  }

  updateUserById(id: any) {
    this.router.navigate(['', id]);
  }
}
