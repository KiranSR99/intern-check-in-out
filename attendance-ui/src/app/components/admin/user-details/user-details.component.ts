import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
// import { HttpHandlerService } from '../../../services/http-handler.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'] 
})
export class UserDetailsComponent {
  userDetailsData: any[] = []; 

  constructor(
    // private httpHandlerService: HttpHandlerService,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {
    
   
  }

  // fetchUserDetails() {
  //   // this.httpHandlerService.getUserDetails().subscribe(
  //   //   (data: any[]) => {
  //   //     this.userDetailsData = data;
  //   //   },
  //   //   (error) => {
  //   //     console.error('Error fetching user details:', error);
  //   //   }
  //   // );
  // }

  addUser() {
    this.router.navigate(['']);
  }

  editUserById(id: any) {
    this.router.navigate(['', id]);
  }
}
