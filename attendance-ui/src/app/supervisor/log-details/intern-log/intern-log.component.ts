import { Component, OnInit } from '@angular/core';
import { HttpHandlerService } from '../../../services/http-handler.service';

@Component({
  selector: 'app-intern-log',
  templateUrl: './intern-log.component.html',
  styleUrl: './intern-log.component.scss'
})
export class InternLogComponent implements OnInit {
  userRole: any;
  isCheckedIn: boolean = false;
  intern : any;

constructor( private http : HttpHandlerService){}

 

  ngOnInit(): void {
    this.showInternLog();
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
