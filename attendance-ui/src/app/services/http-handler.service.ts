import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { GlobalApiHandler } from '../models/global-api-handler.model';
import { Observable } from 'rxjs';
import { LogsDetails } from '../models/logs.model';
import { UserList } from '../models/UserList.model';
import { internLog } from '../models/internLog.model';

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

  //To logout by user
  logout(): Observable<any> {
    return this.http.post(`${this.apiUrl}/logout`, {});
  }

  //To save tasks by Intern
  saveLog(user: any): Observable<GlobalApiHandler<LogsDetails>> {
    return this.http.post<GlobalApiHandler<LogsDetails>>(
      `${this.apiUrl}/task/saveTasks`,
      user
    );
  }

  //To get the task according to task Id
  getLogById(id: number): Observable<GlobalApiHandler<LogsDetails>> {
    return this.http.get<GlobalApiHandler<LogsDetails>>(
      `${this.apiUrl}/task/getTaskById/` + id
    );
  }

  //To get tasks of a single Intern
  getLogOfOneUser(id: number, page: number, size: number): Observable<GlobalApiHandler<LogsDetails>> {
    return this.http.get<GlobalApiHandler<LogsDetails>>(
      `${this.apiUrl}/task/getTaskOfOneUser/${id}?page=${page - 1}&size=${size}`
    );
  }

  updateLog(data: any): Observable<GlobalApiHandler<LogsDetails>> {
    return this.http.put<GlobalApiHandler<LogsDetails>>(
      `${this.apiUrl}/task/updateTask`,
      data
    );
  }

  //To show all tasks
  showAllTasks(): Observable<any> {
    return this.http.get(`${this.apiUrl}/task/getAllTasks`);
  }

  addUser(data: any): Observable<GlobalApiHandler<UserList>> {
    return this.http.post<GlobalApiHandler<UserList>>(
      `${this.apiUrl}/users/saveUser`,
      data
    );
  }

  //To show the list of all the users
  getAllUsers(
    size: number,
    page: number
  ): Observable<GlobalApiHandler<UserList>> {
    return this.http.get<GlobalApiHandler<UserList>>(
      `${this.apiUrl}/users/getAll?size=${size}&page=${page - 1}`
    );
  }

  //To show all interns
  getAllInters(): Observable<any> {
    return this.http.get(`${this.apiUrl}/users/getAllInterns`);
  }

  //To get Check-in and Check-out time
  getCheckInOutTime(): Observable<any> {
    return this.http.get(`${this.apiUrl}/schedule/fetchAll`);
  }

  //To show all the details of Intern including tasks, check-in, check-out, etc...
  getAllLog(
    name: any, date: any,
    size: number,
    page: number
  ): Observable<GlobalApiHandler<internLog>> {
    return this.http.get<GlobalApiHandler<internLog>>(
      `${this.apiUrl}/schedule/details?fullName=${name}&dateParam=${date}&size=${size}&page=${page - 1}`
    );
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
  showAllLeaveRequests(size: number, page: number): Observable<any> {
    return this.http.get(
      `${this.apiUrl}/leave/get?size=${size}&page=${page - 1}`
    );
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

  //To check-out by the Intern
  checkOut(data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/schedule/checkOut`, data);
  }

  //To get all supervisors
  getAllSupervisors(): Observable<any> {
    return this.http.get(`${this.apiUrl}/users/getAllSupervisors`);
  }

  //To get interns of supervisor
  showInternsOfSupervisor(): Observable<any> {
    return this.http.get(`${this.apiUrl}/users/getInternsOfSupervisor`);
  }

  //To get all the interns
  showAllInterns(): Observable<any> {
    return this.http.get(`${this.apiUrl}/users/getAllInterns`);
  }

  //To get the status of check in of intern
  showInternCheckInStatus(userId: number): Observable<any> {
    return this.http.get(
      `${this.apiUrl}/schedule/getStatusOfCheckin/${userId}`
    );
  }
}
