import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { GlobalApiHandler } from '../models/global-api-handler.model';
import { Observable } from 'rxjs';
import { LogsDetails } from '../models/logs.model';
import { UserList } from '../models/UserList.model';

@Injectable({
  providedIn: 'root'
})
export class HttpHandlerService {
  saveCheckOut(request: any) {
    throw new Error('Method not implemented.');
  }

  constructor(private http: HttpClient) { }

  public apiUrl = `http://localhost:8899/api/v1`;

  loginUser(loginDetail: any): Observable<GlobalApiHandler<FormGroup>> {
    return this.http.post<GlobalApiHandler<FormGroup>>(
      `${this.apiUrl}/login`,
      loginDetail
    );
  }

  saveLog(user: any): Observable<GlobalApiHandler<LogsDetails>>{
    return this.http.post<GlobalApiHandler<LogsDetails>>(`${this.apiUrl}/task/saveTasks`,user);
  }



  addUser(data: any): Observable<GlobalApiHandler<UserList>> {
    return this.http.post<GlobalApiHandler<UserList>>(`${this.apiUrl}/users/saveUser`, data);
  }

  getAllUsers(): Observable<GlobalApiHandler<UserList>> {
    return this.http.get<GlobalApiHandler<UserList>>(`${this.apiUrl}/users/getAll`,)

  }

  deleteID(id: number): Observable<GlobalApiHandler<any>> {
    return this.http.delete<GlobalApiHandler<any>>(`${this.apiUrl}/users/delete/${id}`)            

}
  
getAllLog(): Observable<any>{
  return this.http.get<any>(`${this.apiUrl}/task/getAllTasks`)
}
  
updateUser(data: any): Observable<GlobalApiHandler<any>>{

  return this.http.put<GlobalApiHandler<any>>(`${this.apiUrl}/users/update`,data);

}


  getUserById(id: number): Observable<GlobalApiHandler<UserList>> {
  return this.http.get<GlobalApiHandler<UserList>>(`${this.apiUrl}/users/getById/${id}`);
}

forgotPassword(email: any): Observable<any>{
  return this.http.post<any>(`${this.apiUrl}/forgot-password/send-otp`, email);
}

checkOtp(data: any): Observable<GlobalApiHandler<any>>{
  return this.http.post<GlobalApiHandler<any>>(`${this.apiUrl}/forgot-password/validate-otp`, data);
}

updatePassword(data: any){
  return this.http.put<any>(`${this.apiUrl}/forgot-password/update-password` , data)
}

}


