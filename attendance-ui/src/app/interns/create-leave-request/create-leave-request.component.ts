import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-create-leave-request',
  templateUrl: './create-leave-request.component.html',
  styleUrls: ['./create-leave-request.component.scss'],
})
export class CreateLeaveRequestComponent implements OnInit {
  leaveDetail!: FormGroup;
  internId: any;

  constructor(
    private toast: ToastService,
    private router: Router,
    private fb: FormBuilder,
    private http: HttpHandlerService
  ) { }

  ngOnInit(): void {
    this.internId = parseInt(localStorage.getItem('userId') || '0', 10);

    // Initialization
    this.leaveDetail = this.fb.group({
      noOfDays: ['', Validators.required],
      startDate: ['', [Validators.required, this.dateValidator]],
      endDate: ['', [Validators.required, this.dateValidator]],
      reason: ['', Validators.required],
      internId: this.internId,
    });
  }

  dateValidator(control: FormControl): { [key: string]: boolean } | null {
    const selectedDate: Date = new Date(control.value);
    const today: Date = new Date();
    today.setHours(0, 0, 0, 0);

    if (selectedDate >= today) {
      if (selectedDate.getDay() !== 5 && selectedDate.getDay() !== 6) {
        return null;
      }
    }

    return { invalidDate: true };
  }

  cancel() {
    this.router.navigate(['/']);
  }

  onRequestClick() {
    if (this.leaveDetail.valid) {
      this.http.sendLeaveRequest(this.leaveDetail.value).subscribe({
        next: (response: any) => {
          this.toast.showSuccess('Leave request sent successfully.');
          this.router.navigate(['/app/log-mgnt/leave-request']);
        },
        error: (err) => {
          this.toast.showError('Failed to send leave request.');
        },
      });
    } else {
      this.leaveDetail.markAllAsTouched();
    }
  }
}
