import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-edit-log',
  templateUrl: './edit-log.component.html',
  styleUrl: './edit-log.component.scss'
})
export class EditLogComponent {
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

  onClickUpdateLog(){
    
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
