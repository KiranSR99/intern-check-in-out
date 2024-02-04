import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-create-leave-request',
  templateUrl: './create-leave-request.component.html',
  styleUrl: './create-leave-request.component.scss',
})
export class CreateLeaveRequestComponent implements OnInit {
  leaveDetail!: FormGroup;
  internId: any;

  constructor(
    private toast: ToastService,
    private router: Router,
    private fb: FormBuilder,
    private http: HttpHandlerService
  ) {}

  ngOnInit(): void {
    this.internId = parseInt(localStorage.getItem('userId') || '0', 10);

    //Initialization
    this.leaveDetail = this.fb.group({
      noOfDays: ['', Validators.required],
      startDate: ['', [Validators.required]],
      endDate: ['', Validators.required],
      reason: ['', Validators.required],
      internId: this.internId
    });
  }


  cancel() {
    this.router.navigate(['/']);
  }

  onRequestClick() {
    if (this.leaveDetail.valid) {
      this.http.sendLeaveRequest(this.leaveDetail.value).subscribe({
        next: (response: any) => {
          this.toast.showSuccess('Leave request send successfully.');
          this.router.navigate(['/app/log-mgnt/leave-request']);
        },
        error: (err) => {
          this.toast.showError('Leave Request sent failed.');
        },
      });
    } else {
      this.leaveDetail.markAllAsTouched();
    }
  }
}
