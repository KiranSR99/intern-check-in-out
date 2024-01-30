import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-log',
  templateUrl: './add-log.component.html',
  styleUrl: './add-log.component.scss'
})
export class AddLogComponent {

  logDetails: FormGroup = new FormGroup<any>({});
  submitted = false;
  showDeleteButton = false;


  constructor(private formBuilder: FormBuilder,
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
  

  onClickSaveLog(){}

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
