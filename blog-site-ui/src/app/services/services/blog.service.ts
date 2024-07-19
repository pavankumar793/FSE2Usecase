import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVICE_URI } from '../../../service.uri';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { Blog, BlogRequest } from '../../models/blog.model';

@Injectable({
  providedIn: 'root'
})
export class BlogService {

  userName: string = "";
  jwtToken: string | null = "";

  constructor(private http: HttpClient) { }

  setUserName(username: string) {
    this.userName = username;
  }

  getUserName() {
    return this.userName;
  }

  searchByCategory(category:string) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'https://fse2appgateway.azurewebsites.net' });
    return this.http.get(SERVICE_URI.searchByCategory + category, { headers: headers, responseType: 'text' }).pipe(
      catchError(this.handleError)
    );
  }

  searchByCategoryAndFromToDates(category:string, fromDate:string, toDate:string) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'https://fse2appgateway.azurewebsites.net' });
    return this.http.get(SERVICE_URI.searchByCategory + category + "/" + fromDate + "/" + toDate, { headers: headers, responseType: 'text' }).pipe(
      catchError(this.handleError)
    );
  }

  getBlogsByUser(): Observable<Blog[]> {
    if (typeof window !== 'undefined' && window.localStorage) {
      this.jwtToken = localStorage.getItem('currentUser');
    }
    const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'https://fse2appgateway.azurewebsites.net', 'JwtTokenAuth': this.jwtToken as string });
    return this.http.get<Blog[]>(SERVICE_URI.getBlogsByUser + this.userName, { headers: headers }).pipe(
      catchError(this.handleError)
    );
  }

  addBlog(blogreq: BlogRequest) {
    if (typeof window !== 'undefined' && window.localStorage) {
      this.jwtToken = localStorage.getItem('currentUser');
    }
    const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'https://fse2appgateway.azurewebsites.net', 'JwtTokenAuth': this.jwtToken as string });
    return this.http.post(SERVICE_URI.addBlog, blogreq, { headers: headers, responseType: 'text' }).pipe(
      catchError(this.handleError)
    );
  }

  deleteBlog(blogId: string) {
    if (typeof window !== 'undefined' && window.localStorage) {
      this.jwtToken = localStorage.getItem('currentUser');
    }
    const headers = new HttpHeaders({ 'Content-Type': 'application', 'Access-Control-Allow-Origin': 'https://fse2appgateway.azurewebsites.net', 'JwtTokenAuth': this.jwtToken as string });
    return this.http.delete(SERVICE_URI.deleteBlog + blogId, { headers: headers, responseType: 'text' }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: any) {
    let message = 'An error occurred. Please try again later.';
    return throwError(() => new Error(message));
  }
}
