import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { GlobalApiHandler } from '../models/global-api-handler.model';
import { Observable } from 'rxjs';
import { LogsDetails } from '../models/logs.model';
import { UserList } from '../models/UserList.model';

@Injectable({
  providedIn: 'root',
})
export class HttpHandlerService {
  saveCheckOut(request: any) {
    throw new Error('Method not implemented.');
  }

  constructor(private http: HttpClient) {}

  public apiUrl = `http://localhost:8899/api/v1`;

  //To login to the system by user
  loginUser(loginDetail: any): Observable<GlobalApiHandler<FormGroup>> {
    return this.http.post<GlobalApiHandler<FormGroup>>(
      `${this.apiUrl}/login`,
      loginDetail
    );
  }

  //To save tasks by Intern
  saveLog(user: any): Observable<GlobalApiHandler<LogsDetails>> {
    return this.http.post<GlobalApiHandler<LogsDetails>>(
      `${this.apiUrl}/task/saveTasks`,
      user
    );
  }

  getLogById(id: number): Observable<GlobalApiHandler<LogsDetails>> {
    return this.http.get<GlobalApiHandler<LogsDetails>>(
      `${this.apiUrl}/task/getTaskById/` + id
    );
  }

  updateLog(data: any): Observable<GlobalApiHandler<LogsDetails>> {
    return this.http.put<GlobalApiHandler<LogsDetails>>(
      `${this.apiUrl}/task/updateTask`,
      data
    );
  }

  addUser(data: any): Observable<GlobalApiHandler<UserList>> {
    return this.http.post<GlobalApiHandler<UserList>>(
      `${this.apiUrl}/users/saveUser`,
      data
    );
  }

  //To show the list of all the users
  getAllUsers(): Observable<GlobalApiHandler<UserList>> {
    return this.http.get<GlobalApiHandler<UserList>>(
      `${this.apiUrl}/users/getAll`
    );
  }

  //To get the tasks of all Inters
  getAllLog(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/task/getAllTasks`);
  }

  //To delete user by Id
  deleteUserById(id: number): Observable<GlobalApiHandler<any>> {
    return this.http.delete<GlobalApiHandler<any>>(
      `${this.apiUrl}/users/delete/${id}`
    );
  }

  updateUser(data: any): Observable<GlobalApiHandler<any>> {
    return this.http.put<GlobalApiHandler<any>>(
      `${this.apiUrl}/users/update`,
      data
    );
  }

  //To get a single user by Id
  getUserById(id: number): Observable<GlobalApiHandler<UserList>> {
    return this.http.get<GlobalApiHandler<UserList>>(
      `${this.apiUrl}/users/getById/${id}`
    );
  }

  //To send leave request
  sendLeaveRequest(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/leave/create`, data);
  }

  //To fetch the leave request of a intern
  showInternLeaveRequests(userId: any): Observable<any> {
    return this.http.get(`${this.apiUrl}/leave/getIntern/${userId}`);
  }

  //To show all the leave request for Supervisor
  showAllLeaveRequests(): Observable<any> {
    return this.http.get(`${this.apiUrl}/leave/get`);
  }

  //To approve the leave request by Supervisor
  approveLeaveRequest(id: any): Observable<any> {
    return this.http.patch(`${this.apiUrl}/leave/approved-leave/${id}`, id);
  }

  //To decline the leave request by Supervisor
  declineLeaveRequest(id: any): Observable<any> {
    return this.http.patch(`${this.apiUrl}/leave/decline-leave/${id}`, id);
  }

  //To implement the forget password functionality
  forgotPassword(email: any): Observable<any> {
    return this.http.post<any>(
      `${this.apiUrl}/forgot-password/send-otp`,
      email
    );
  }

  //To generate the OTP to change password
  checkOtp(data: any): Observable<GlobalApiHandler<any>> {
    return this.http.post<GlobalApiHandler<any>>(
      `${this.apiUrl}/forgot-password/validate-otp`,
      data
    );
  }

  //To update the password
  updatePassword(data: any) {
    return this.http.put<any>(
      `${this.apiUrl}/forgot-password/update-password`,
      data
    );
  }

  //To check-in by the Intern
  checkIn(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/schedule/checkIn`, data);
  }
}
