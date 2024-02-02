import { HttpClient } from '@angular/common/http';
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

  //To get a task by id
  getLogById(id: number): Observable<GlobalApiHandler<LogsDetails>> {
    return this.http.get<GlobalApiHandler<LogsDetails>>(
      `${this.apiUrl}/task/getTaskById/` + id
    );
  }

  //To update the task
  updateLog(data: any): Observable<GlobalApiHandler<LogsDetails>> {
    return this.http.put<GlobalApiHandler<LogsDetails>>(
      `${this.apiUrl}/task/updateTask`,
      data
    );
  }

  //To add the user by the admin
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

  //To update the user details
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
  checkIn(userId: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/schedule/checkIn`, userId);
  }

  // checkIn(userId: any): Observable<any> {
  //   // Ensure userId is an integer
  //   const userIdInt = Number(userId);

  //   // Construct the request body as a JSON object with the integer userId
  //   const body = { userId: userIdInt };

  //   // Directly pass the object to the POST method
  //   return this.http.post(`${this.apiUrl}/schedule/checkIn`, body);
  // }
}
