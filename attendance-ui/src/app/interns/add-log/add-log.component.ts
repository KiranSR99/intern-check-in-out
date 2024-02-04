import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';
import { LogsDetails } from '../../models/logs.model';

@Component({
  selector: 'app-add-log',
  templateUrl: './add-log.component.html',
  styleUrl: './add-log.component.scss',
})
export class AddLogComponent {
  logDetails: FormGroup = new FormGroup<any>({});
  submitted = false;
  showDeleteButton = false;
  userId: any;

  constructor(
    private formBuilder: FormBuilder,
    private http: HttpHandlerService,
    private router: Router,
    private location: Location,
    private toast: ToastService
  ) {}

  ngOnInit(): void {
    this.onInitLogDetails();

    this.userId = localStorage.getItem('userId');

  }

  onInitLogDetails() {
    this.logDetails = this.formBuilder.group({
      task: ['', Validators.required],
      status: ['', Validators.required],
      timeTaken: ['', Validators.required],
      problem: ['', Validators.required],
      userId: this.userId,
      multiLogDetails: this.formBuilder.array([]),
    });
  }

  get multiLogDetails() {
    return this.logDetails.get('multiLogDetails') as FormArray;
  }

  onClickSaveLog() {
    const tasksArray: Array<any> = [];
  
    // Add main task
    tasksArray.push({
      task: this.logDetails.get('task')?.value,
      status: this.logDetails.get('status')?.value,
      timeTaken: this.logDetails.get('timeTaken')?.value,
      problem: this.logDetails.get('problem')?.value,
      userId: this.userId,
    });
  
    // Add multiLogDetails tasks
    this.multiLogDetails.controls.forEach((control: any) => {
      tasksArray.push({
        task: control.get('task')?.value,
        status: control.get('status')?.value,
        timeTaken: control.get('timeTaken')?.value,
        problem: control.get('problem')?.value,
        userId: control.get('userId')?.value
      });
    });
  
    const logDetailsData = { tasks: tasksArray };
    console.log(logDetailsData);
  
    if (this.logDetails.valid) {
      this.http.saveLog(logDetailsData).subscribe(
        (response: any) => {
          console.log(response);
          this.toast.showSuccess('Task Added Successfully');
          this.logDetails.reset();
          this.router.navigate(['/app/log-mgnt/intern-log']);
        },
        (error) => {
          console.error(error);
        }
      );
    }else{
      this.logDetails.markAllAsTouched();
    } 
  }
  
  

  addmultiLogDetails() {
    this.multiLogDetails.push(
      this.formBuilder.group({
        task: ['', Validators.required],
        status: ['', Validators.required],
        timeTaken: ['', Validators.required],
        problem: ['', Validators.required],
        userId: this.userId,
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

  cancel() {
    this.location.back();
  }
}
