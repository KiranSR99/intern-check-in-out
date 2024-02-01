import { Component, OnInit } from '@angular/core';
import { HttpHandlerService } from '../../../services/http-handler.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-intern-log',
  templateUrl: './intern-log.component.html',
  styleUrl: './intern-log.component.scss'
})
export class InternLogComponent implements OnInit {
  userRole: any;
  isCheckedIn: boolean = false;
  intern : any;
  searchText: any;
  
  id: any;
  userId: any;


constructor( private http : HttpHandlerService, private route: Router){}

  ngOnInit(): void {
    this.showInternLog();
    this.showInternName(this.id);
    this.userId = localStorage.getItem('userId');
    this.userRole = localStorage.getItem('role');
      if(this.userRole){
        this.userRole = JSON.parse(this.userRole);
      } 
      
      
  }

  showInternLog(){
    this.http.getAllLog().subscribe(
      (result: any)=>{
        this.intern = result.data;
        console.log("fetch data successfully",result);
      },
      (error: any)=>{
        console.error("Error fetching data", error);
      }
    );
  }

  showInternName(id : any){
    this.http.getUserById(id).subscribe(
      (result: any)=>{
        this.intern = result.data.fullName;
        console.log("Fetch data successfully", result);
        
      },
      (error: any)=>{
        console.error("Error fetching data", error);
      }
    );
  }

  onCheckInClick(){
    this.isCheckedIn = true;

    this.http.checkIn(this.userId).subscribe(
      (result: any)=>{
        console.log("Check in successfully", result);
      },
      (error: any)=>{
        console.error("Error", error);
      }
     
      
    );
  }

  onCheckOutClick(){
    this.isCheckedIn = false;
  }

  onClickAddTask(){
    this.route.navigate(['app/log-mgnt/add-log']);
  }

  onViewClick(){

  }

  onEditClick(id: number){
    this.route.navigate(['app/log-mgnt/edit-log']);
  }

  onDeleteClick(){}
}
