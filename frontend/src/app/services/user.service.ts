import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable, throwError} from 'rxjs';
import {User} from '../dtos/user';
import {catchError, tap} from 'rxjs/operators';
import {FormGroup} from '@angular/forms';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  error: boolean = false;
  errorMessage: string = '';
  private userBaseUri: string = this.globals.backendUri + '/users';
  constructor(private httpClient: HttpClient, private globals: Globals, private router: Router) {
  }
  private handleError(err: HttpErrorResponse) {
    let errorMessage = '';
    if (err.error instanceof ErrorEvent) {
      errorMessage = `An error occured : ${err.error.message} `;
    } else {
      errorMessage = `Server returned code: ${err.status}, error message is: ${err.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }
  getAllUsers(): Observable<User[]> {
    console.log('Load all users.');
    return this.httpClient.get<User[]>(this.userBaseUri + '/all').pipe(
      tap(data => console.log('All ' + JSON.stringify(data))),
      catchError(this.handleError)
    );
  }
  getUserByUserCode(userCode: string): Observable<User> {
    return null; // to do !!
  }
  save(user: User): Observable<User> {
    console.log(user.userCode);
    return this.httpClient.post<User>(this.userBaseUri + '/customers', user).pipe(
      tap(data => console.log('All ' + JSON.stringify(data))),
      catchError(this.handleError)
    );
  }
}
