import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHandlerService } from '../../services/http-handler.service';
import { ToastService } from '../../services/toast.service';
import { CustomDateTimeFormatterPipe } from '../../pipe/date-time-formatter.pipe';
import { DateFilterPipe } from '../../pipe/date-restriction.pipe';

@Component({
  selector: 'app-create-leave-request',
  templateUrl: './create-leave-request.component.html',
  styleUrls: ['./create-leave-request.component.scss'],
  providers: [CustomDateTimeFormatterPipe, DateFilterPipe]
})
export class CreateLeaveRequestComponent implements OnInit {
  leaveDetail!: FormGroup;
  minDate: string | undefined;
  internId: any;

  constructor(
    private toast: ToastService,
    private router: Router,
    private fb: FormBuilder,
    private http: HttpHandlerService,
    private dateFilterPipe: DateFilterPipe // Inject DateFilterPipe
  ) { }

  ngOnInit(): void {
    this.internId = parseInt(localStorage.getItem('userId') || '0', 10);
    this.minDate = new Date(). toISOString().split('T')[0];

    // Initialization
    this.leaveDetail = this.fb.group({
      noOfDays: ['', Validators.required],
      startDate: ['', [Validators.required, this.dateValidator]],
      endDate: ['', [Validators.required, this.dateValidator]],
      reason: ['', Validators.required],
      internId: this.internId,
    });
  }

  cancel() {
    this.router.navigate(['/']);
  }

  onRequestClick() {
    if (this.leaveDetail.valid) {
      const startDate = this.dateFilterPipe.transform(this.leaveDetail.get('startDate')?.value);
      const endDate = this.dateFilterPipe.transform(this.leaveDetail.get('endDate')?.value);
      
      this.http.sendLeaveRequest({ 
        ...this.leaveDetail.value,
        startDate,
        endDate
      }).subscribe({
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

  dateValidator(control: FormControl): { [key: string]: boolean } | null {
    const selectedDate: string = control.value;
    const today = new Date();
    const selected = new Date(selectedDate);
      if (selected < today) {
      return { invalidDate: true };
    }
  
    const dayOfWeek = selected.getDay();
    if (dayOfWeek === 5 || dayOfWeek === 6) {
      return { invalidDay: true };
    }
  
    return null; 
  }
  
  
}
