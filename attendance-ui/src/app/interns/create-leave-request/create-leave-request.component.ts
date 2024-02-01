import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-create-leave-request',
  templateUrl: './create-leave-request.component.html',
  styleUrl: './create-leave-request.component.scss'
})
export class CreateLeaveRequestComponent {
  leaveDetail!: FormGroup

  constructor(
    private toast: ToastService,
    private router: Router,
    private fb: FormBuilder,
    private http: HttpHandlerService
  ) {
    this.leaveDetail = this.fb.group({
      noOfDays: ['', Validators.required],
      startDate: ['', [Validators.required]],
      endDate: ['', Validators.required],
      reason: ['', Validators.required],
      internId: ['', Validators.required]
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
          console.error('Error adding user:', err);
          this.toast.showError('Error adding user');
        }
      });
    } else {
      this.leaveDetail.markAllAsTouched();
    }
  }

}
