// import { Component } from '@angular/core';
// import { Router } from '@angular/router';
// import { ToastrService } from 'ngx-toastr';
// import { HttpHandlerService } from '../../services/http-handler.service';

// @Component({
//   selector: 'app-update-users',
//   templateUrl: './update-users.component.html',
//   styleUrls: ['./update-users.component.scss']
// })
// export class UpdateUsersComponent {
//   userDetails: any;

//   constructor(
//     private toastr: ToastrService,
//     private router: Router,
//     private http: HttpHandlerService
//   ) {
//     this.userDetails = {}; // Initialize userDetails
//     this.id = null; // Initialize id
//   }

//   onClickUpdate() {
//     this.http.put<any>(`${this.apiUrl}/${this.userDetails.id}`, this.userDetails)
//       .subscribe({
//         next: (response: any) => {
//           console.log('User updated successfully:', response);
//           this.toastr.success('User updated successfully');
//           this.router.navigate(['/app/user-mgnt/user-list']);
//         },
//         error: (error: any) => {
//           console.error('Error updating user:', error);
//           this.toastr.error('Error updating user');
//         }
//       });
//   }
// }