import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';
<<<<<<< HEAD
=======
import { LogsDetails } from '../../models/logs.model';
>>>>>>> f790e1133606b643692ad8ea6e86f0dcb9ba9f42

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
<<<<<<< HEAD
    private toast: ToastService,
    private location: Location){}
=======
    private location: Location,
    private toast: ToastService
  ) {}
>>>>>>> f790e1133606b643692ad8ea6e86f0dcb9ba9f42

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

<<<<<<< HEAD
  onClickSaveLog(user: any){
    if (this.logDetails.valid) {
      this.http.saveLog(this.logDetails.value).subscribe({
        next: (response: any) => {
          console.log('User added successfully');
          this.toast.showSuccess('User added successfully');
          this.router.navigate(['/app/log-mgnt/intern-log']);
        },
        error: (err) => {
          console.error('Error adding user:', err);
          this.toast.showError('Error adding logs');
        }
      });
    } else {
      this.logDetails.markAllAsTouched();
    }
=======
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

    this.httpHandlerService.saveLog(logDetailsData).subscribe(
      (response: any) => {
        console.log(response);
        this.toast.showSuccess('Log Add Successfully');
        this.logDetails.reset();
      },
      (error) => {
        console.error(error);
      }
    );
    // this.location.back();
>>>>>>> f790e1133606b643692ad8ea6e86f0dcb9ba9f42
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
