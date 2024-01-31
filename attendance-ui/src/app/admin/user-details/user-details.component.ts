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
    
   
  }

  fetchUserDetails() {
    this.httpHandler.getAllUsers().subscribe(
      (data: any) => {
        this.userDetailsData = data;
      },
      (error:any) => {
        console.error('Error fetching user details:', error);
      }
    );
  }

  addUser() {
    this.router.navigate(['app/user-mgnt/add-user']);
  }

  updateUserById(id: any) {
    this.router.navigate(['', id]);
  }
}
