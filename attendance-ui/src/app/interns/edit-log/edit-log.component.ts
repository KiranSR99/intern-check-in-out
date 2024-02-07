import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-edit-log',
  templateUrl: './edit-log.component.html',
  styleUrl: './edit-log.component.scss',
})
export class EditLogComponent {
  logDetails: FormGroup = new FormGroup<any>({});
  submitted = false;
  showDeleteButton = false;
  userId: any;
  taskId: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private http: HttpHandlerService,
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private toastr: ToastService
  ) {}

  ngOnInit(): void {
    this.onInitLogDetails();

    this.userId = localStorage.getItem('userId');
    this.route.params.subscribe((params) => {
      this.taskId = params['taskId'];
      console.log('My task id ' + this.taskId);

      this.editLog(this.taskId);
    });
  }

  onInitLogDetails() {
    this.logDetails = this.formBuilder.group({
      task: ['', Validators.required],
      status: ['', Validators.required],
      timeTaken: ['', Validators.required],
      problem: ['', Validators.required],
      userId: this.userId,
     taskId: this.taskId,
     // multiLogDetails: this.formBuilder.array([]),
    });
  }

  get multiLogDetails() {
    return this.logDetails.get('multiLogDetails') as FormArray;
  }

  addmultiLogDetails() {
    this.multiLogDetails.push(
      this.formBuilder.group({
        task: ['', Validators.required],
        status: ['', Validators.required],
        timeTaken: ['', Validators.required],
        problem: ['', Validators.required],
        userId: this.userId,
        taskId: this.taskId,
      })
    );
    console.log('form details:', this.logDetails.value);
    this.showDeleteButton = true;
  }

  editLog(id: number) {
    console.log('Fetching log data for ID:', id);
    this.http.getLogById(id).subscribe(
      (response: any) => {
        console.log('Individual log fetched successfully:', response);
        this.populateForm(response.data);
      },
      (error: any) => {
        console.log('Error fetching data:', error);
      }
    );
  }
  
  
  populateForm(data: any) {
    // Set the values for the main form
    
    this.logDetails.patchValue({
      taskId: data.taskId,
      task: data.task,
      status: data.status,
      timeTaken: data.timeTaken,
      problem: data.problem,
      userId: data.userId,
    });

    const logDetailsArray = this.logDetails.get('multiLogDetails') as FormArray;

    // Add a new group for each another detail
    // data.multiLogDetails.forEach((multiLogDetails: any) => {
    //   logDetailsArray.push(
    //     this.formBuilder.group({
    //       id: multiLogDetails.id,
    //       task: multiLogDetails.task,
    //       status: multiLogDetails.status,
    //       timeTaken: multiLogDetails.timeTaken,
    //       problem: multiLogDetails.problem,
    //     })
    //   );
    // });
  }

  onClickUpdateLog(data: any) {
    console.log(data.value);
    this.http.updateLog(this.logDetails.value).subscribe(
      (response: any) => {
        console.log('Data is updated successfully');
        this.toastr.showSuccess('Log Update Sucessfully');
        this.router.navigate(['/app/log-mgnt/my-log']);
        this.onInitLogDetails();
        this.logDetails.reset();
      },
      (error: any) => {
        console.log('error');
      }
    );
    this.location.back();
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
