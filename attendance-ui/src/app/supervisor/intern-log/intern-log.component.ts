import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';

@Component({
  selector: 'app-intern-log',
  templateUrl: './intern-log.component.html',
  styleUrls: ['./intern-log.component.scss'],
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
    this.userId = localStorage.getItem('userId');
    this.userRole = localStorage.getItem('role');

    // Retrieve the isCheckedIn state from localStorage
    const isCheckedInString = localStorage.getItem('isCheckedIn');
    this.isCheckedIn = isCheckedInString === 'true';

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
    localStorage.setItem('isCheckedIn', 'true');

    const checkInReqBody = {
      userId: this.userId,
    };

    this.http.checkIn(checkInReqBody).subscribe(
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
    localStorage.removeItem('isCheckedIn');

    const checkOutReqBody = {
      userId: this.userId
    };

    this.http.checkOut(checkOutReqBody).subscribe(
      (result: any) => {
        console.log("Checked out successfully", result);
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
