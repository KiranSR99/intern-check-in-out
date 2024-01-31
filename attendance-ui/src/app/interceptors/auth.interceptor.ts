import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private route: Router) { }

  intercept(request: HttpRequest<any>,
    next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('token');

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }
    return next.handle(request).pipe(
      tap((event: HttpEvent<any>) => {
        console.log('on http call success from interceptor: ', event);

      }),
      catchError((error: HttpErrorResponse) => {
        console.log('on http call error from interceptor: ', error);

        let errorMessage = 'Handled HTTP error';

        if (error.error.status == 403 || error.error.status == "401 UNAUTHORIZED") {
          this.route.navigate(['/login']);
        }
        return throwError(error);
      })
    );
  }
}
