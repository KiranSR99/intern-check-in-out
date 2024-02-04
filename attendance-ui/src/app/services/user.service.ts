// user.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private userNameSubject = new BehaviorSubject<string>('DefaultUsername');
  public userName$ = this.userNameSubject.asObservable();

  updateUserName(newUserName: string): void {
    this.userNameSubject.next(newUserName);
  }
}
