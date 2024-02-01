import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

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

  onClickAddTask(){
    //this.route.navigate(['/app/log-mgnt/add-log'])
  }

}
