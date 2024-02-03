import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';

@Component({
  selector: 'app-intern-log',
  templateUrl: './intern-log.component.html',
  styleUrl: './intern-log.component.scss',
})
export class InternLogComponent implements OnInit {
  userRole: any;
  isCheckedIn: boolean = false;
  intern: any;
  searchText: any;
  id: any;
  userId: any;
  internLogs: any;

  constructor(private http: HttpHandlerService, private route: Router) {}

  ngOnInit(): void {
    this.showInternLog();
    // this.showInternName(this.id);
    this.userId = localStorage.getItem('userId');
    this.userRole = localStorage.getItem('role');
    if (this.userRole) {
      this.userRole = JSON.parse(this.userRole);
    }
  }

  showInternLog() {
    
    this.http.getAllLog().subscribe(
      (result: any) => {
        this.intern = result;
        console.log('fetch data successfully', result);
      },
      (error: any) => {
        console.error('Error fetching data', error);
      }
    );
  }

  onCheckInClick() {
    this.isCheckedIn = true;

    const chekckInReqBody = {
      userId: this.userId,
    };

    this.http.checkIn(chekckInReqBody).subscribe(
      (result: any) => {
        console.log('Check in successfully', result);
      },
      (error: any) => {
        console.error('Error', error);
      }
    );
  }

  onCheckOutClick() {
    this.isCheckedIn = false;

    const checkOutReqBody = {
      userId: this.userId
    }

    this.http.checkOut(checkOutReqBody).subscribe(
      (result: any) => {
        console.log("Checked out successfully", result);
        this.isCheckedIn = false;
        this.showInternLog(); // Refresh the log to show the updated check-out time
      },
      (error: any) => {
        console.error('Error during check-out', error);
      }
    );
  }

  onClickAddTask() {
    this.route.navigate(['app/log-mgnt/add-log']);
  }

  onViewClick() {}

  onEditClick(id: number) {
    this.route.navigate(['app/log-mgnt/edit-log/', id]);
  }

  
}
