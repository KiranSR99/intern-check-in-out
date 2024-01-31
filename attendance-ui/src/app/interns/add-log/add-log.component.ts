import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';

@Component({
  selector: 'app-add-log',
  templateUrl: './add-log.component.html',
  styleUrl: './add-log.component.scss'
})
export class AddLogComponent {

  logDetails: FormGroup = new FormGroup<any>({});
  submitted = false;
  showDeleteButton = false;


  constructor(
    private formBuilder: FormBuilder,
    private httpHandlerService: HttpHandlerService,
    private router: Router,
    private location: Location){}

  ngOnInit(): void {
   this.onInitLogDetails();
  }

  onInitLogDetails(){
    this.logDetails =this.formBuilder.group({
      task: ['', Validators.required],
      status: ['',Validators.required],
      timetaken: ['', Validators.required],
      problem: ['', Validators.required],
      multiLogDetails: this.formBuilder.array([])
    });
  }

  get multiLogDetails() {
    return (this.logDetails.get('multiLogDetails') as FormArray);
  }
  

  onClickSaveLog(user: any){
    console.log(this.logDetails.value);
    console.log(this.multiLogDetails.value);
    this.httpHandlerService.saveLog(user).subscribe(
      (response: any) => {
        console.log(response);
       // this.toastr.success("Log Add Sucessfully");
        this.onInitLogDetails();
       // this.router.navigate(['/app/event-mgnt/view-event']);
        this.logDetails.reset();
      },
      (error) => {
        console.error(error);
      },
      () => {
        console.log('completed!!')
      }
    );
      this.location.back();
  }

  addmultiLogDetails() {
    this.multiLogDetails.push(this.formBuilder.group({
      task: ['', Validators.required],
      status: ['',Validators.required],
      timetaken: ['', Validators.required],
      problem: ['', Validators.required],
    })
    );
    console.log('form details:', this.logDetails.value);
    this.showDeleteButton = true;
  }

  onDeleteButtonClick(i: number) {
    if (this.multiLogDetails.length > 0) {
      this.multiLogDetails.removeAt(i);
    } else {
      this.showDeleteButton = false;

    }
  }

  cancel(){
    this.location.back()
  }

}
