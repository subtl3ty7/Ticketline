import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable, throwError} from 'rxjs';
import {User} from '../dtos/user';
import {catchError, tap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {ChangePassword} from '../dtos/change-password';

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
    console.log('Load user by UserCode');
    return this.httpClient.get<User>(this.userBaseUri + '/' + userCode).pipe(
      tap(data => console.log('User ' + JSON.stringify(data))),
      catchError(this.handleError)
    );
  }
  save(user: User): Observable<User> {
    if (user.admin) {
      return this.httpClient.post<User>(this.userBaseUri + '/administrators', user).pipe(
        tap(data => console.log('All ' + JSON.stringify(data))),
        catchError(this.handleError)
      );
    }
    console.log(user);
    return this.httpClient.post<User>(this.userBaseUri + '/customers', user).pipe(
      tap(data => console.log('All ' + JSON.stringify(data))),
      catchError(this.handleError)
    );
  }
  blockUser(userCode: string) {
    console.log('Block user by UserCode');
    return this.httpClient.get<User>(this.userBaseUri + '/block/' + userCode).pipe(
      catchError(this.handleError)
    );
  }
  unblockUser(userCode: string) {
    console.log('Unblock user by UserCode');
    return this.httpClient.get<User>(this.userBaseUri + '/unblock/' + userCode).pipe(
      catchError(this.handleError)
    );
  }
  resetPassword(userCode: string, oldPassword: string, newPassword: string) {
    console.log('Reset password for ' + userCode);
    // encoding
    return this.httpClient.post<ChangePassword>(this.userBaseUri + '/reset-password',
      new ChangePassword(userCode, oldPassword, newPassword)).pipe(
      tap(data => console.log('All ' + JSON.stringify(data))),
      catchError(this.handleError)
    );
  }
}
