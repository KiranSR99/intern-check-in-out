import { Component, ElementRef, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';
import { ViewChild } from '@angular/core';

@Component({
  selector: 'app-intern-log',
  templateUrl: './intern-log.component.html',
  styleUrls: ['./intern-log.component.scss'],
})
export class InternLogComponent implements OnInit {

  @ViewChild('datePicker') datePicker!: ElementRef;

  page: number = 1;
  size: number = 5;
  pageDetails: any;
  userRole: any;
  isCheckedIn: boolean = false;
  intern: any;
  searchText: any;
  id: any;
  userId: any;
  internLogs: any;
  internDetails: any;
  internTasks: any;
  internCheckInOut: any;
  combinedData: any[] = [];
  tasks: any[] = [];

  apiResponse: Array<any> = new Array<any>();

  constructor(
    private http: HttpHandlerService,
    private route: Router,
    private toast: ToastService
  ) {}

  ngOnInit(): void {
    // this.showInternLog();
    this.userId = localStorage.getItem('userId');
    this.userRole = localStorage.getItem('role');

    // Retrieve the isCheckedIn state from localStorage
    const isCheckedInString = localStorage.getItem('isCheckedIn');
    this.isCheckedIn = isCheckedInString === 'true';

    if (this.userRole) {
      this.userRole = JSON.parse(this.userRole);
    }
    this.showInternLog(this.size, this.page);
  }

  showInternLog(size: number, page: number) {
    this.http.getAllLog(size, page).subscribe(
      (result: any) => {
        this.apiResponse = result.content;
        this.pageDetails = result;
      },
      (error: any) => {
        console.error('Error fetching data', error);
      }
    );
  }

  // //To show intern name
  // showInternDetails() {
  //   this.http.getAllInters().subscribe({
  //     next: (response: any) => {
  //       this.internDetails = response.data;
  //       console.log(this.internDetails);
  //     },
  //   });
  // }

  // //To show intern tasks
  // showInternTasks() {
  //   this.http.showAllTasks().subscribe({
  //     next: (response: any) => {
  //       this.internTasks = response.data;
  //       console.log(this.internTasks);
  //     },
  //   });
  // }

  //To show intern Check-in Check-out time
  showCheckInOut() {
    this.http.getCheckInOutTime().subscribe({
      next: (response: any) => {
        this.internCheckInOut = response.data;
        console.log(this.internCheckInOut);
      },
      error: (error: any) => {},
    });
  }

  onCheckInClick() {
    const checkInReqBody = {
      userId: this.userId,
    };

    this.http.checkIn(checkInReqBody).subscribe(
      (result: any) => {
        this.toast.showSuccess('Check-in successful.');
        this.showInternLog(this.size, this.page);
        this.showCheckInOut();

        this.isCheckedIn = true;
        localStorage.setItem('isCheckedIn', 'true');
      },
      (error: any) => {
        this.toast.showError(
          "You can't check in again on the same day you have checked out."
        );
      }
    );
  }

  onCheckOutClick() {
    this.isCheckedIn = false;
    localStorage.removeItem('isCheckedIn');

    const checkOutReqBody = {
      userId: this.userId,
    };

    this.http.checkOut(checkOutReqBody).subscribe(
      (result: any) => {
        this.toast.showSuccess('Checked out successfully.');
        this.showInternLog(this.size, this.page);
        this;
        this.showCheckInOut();
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



  openDatePicker() {
    this.datePicker.nativeElement.style.display = 'block';
  }

  // Function to handle date change
  handleDateChange(input: any) {
    const dateValue: string = input.value;
    console.log('Selected date:', dateValue);
    // Add your logic to filter data based on the selected date
  }
}
