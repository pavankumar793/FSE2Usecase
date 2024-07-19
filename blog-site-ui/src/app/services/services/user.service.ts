import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { throwError, BehaviorSubject, Observable } from 'rxjs';
import { UserRegister, UserLogin } from '../../models/user.model';
import { SERVICE_URI } from '../../../service.uri';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private http: HttpClient) { 
    let currentUser = null;
    if (typeof window !== 'undefined' && window.localStorage) {
      const storedUser = localStorage.getItem('currentUser');
      currentUser = storedUser ? JSON.parse(storedUser) : null;
    }
    this.currentUserSubject = new BehaviorSubject<any>(currentUser);
    this.currentUser = this.currentUserSubject.asObservable();
  }

  register(data: UserRegister) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'https://fse2appgateway.azurewebsites.net' });
    return this.http.post(SERVICE_URI.register, data, { headers: headers, responseType: 'text' }).pipe(
      catchError(this.handleError)
    );
  }

  login(data: UserLogin) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'https://fse2appgateway.azurewebsites.net' });
    return this.http.post(SERVICE_URI.login, data, { headers: headers, responseType: 'text' }).pipe(
      map(user => {
        if (typeof window !== 'undefined') {
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
          return user;
        }
        return null;
      }),
      catchError(this.handleError)
    );
  }

  private handleError(error: any) {
    let message = 'An error occurred. Please try again later.';
    if (error.error.includes("Email is already in use")){
      message = "Email is already in use";
    } else if (error.error.includes("Username is already in use")){
      message = "Username is already in use";
    } else if (error.error.includes("Incorrect password")){
      message = "Incorrect password";
    } else if (error.error.includes("Incorrect username")){
      message = "Incorrect username";
    }
    console.error('An error occurred:', message);
    return throwError(() => new Error(message));
  }
}
