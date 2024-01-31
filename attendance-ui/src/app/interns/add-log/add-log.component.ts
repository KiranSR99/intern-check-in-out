import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';

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
    private http: HttpHandlerService,
    private router: Router,
    private toast: ToastService,
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
