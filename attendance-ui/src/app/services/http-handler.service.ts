import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { GlobalApiHandler } from '../models/global-api-handler.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpHandlerService {

  constructor(private http:HttpHandlerService) { }


        
  }


constructor(private http: HttpClient) {}

  public apiUrl = `http://localhost:8899/api/v1`;



  loginUser(myform: any): Observable<GlobalApiHandler<FormGroup>> {
    return this.http.post<GlobalApiHandler<FormGroup>>(`${this.apiUrl}/login`, myform);
  }
}

