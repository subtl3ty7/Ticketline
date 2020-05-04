import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable, throwError} from 'rxjs';
import {User} from '../dtos/user';
import {catchError, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  private userBaseUri: string = this.globals.backendUri + '/users';
  constructor(private httpClient: HttpClient, private globals: Globals) {
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
    console.log('Load user by UserCode');
    return this.httpClient.get<User>(this.userBaseUri + '/' + userCode).pipe(
      tap(data => console.log('User ' + JSON.stringify(data))),
      catchError(this.handleError)
    );
  }
}
