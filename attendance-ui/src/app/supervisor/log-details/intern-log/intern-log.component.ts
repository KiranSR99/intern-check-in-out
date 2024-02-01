import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-intern-log',
  templateUrl: './intern-log.component.html',
  styleUrl: './intern-log.component.scss'
})
export class InternLogComponent implements OnInit {
  userRole: any;
  isCheckedIn: boolean = false;

  ngOnInit(): void {
      this.userRole = localStorage.getItem('role');
      if(this.userRole){
        this.userRole = JSON.parse(this.userRole);
      }      
  }

  onCheckInClick(){
    this.isCheckedIn = true;
  }

  onCheckOutClick(){
    this.isCheckedIn = false;
  }

}
