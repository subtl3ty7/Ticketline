import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable, throwError} from 'rxjs';
import {User} from '../dtos/user';
import {catchError, tap} from 'rxjs/operators';
import {UserLogin} from '../dtos/user-login';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  error: boolean = false;
  errorMessage: string = '';
  private userBaseUri: string = this.globals.backendUri + '/users';
  constructor(private httpClient: HttpClient, private globals: Globals) {
  }
  private handleError(err: HttpErrorResponse) {
    let errorMessage;
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
  getCurrentUser(): Observable<User> {
    console.log('Load user by UserCode');
    return this.httpClient.get<User>(this.userBaseUri + '/my-profile').pipe(
      tap(data => console.log('User ' + JSON.stringify(data))),
      catchError(this.handleError)
    );
  }
  save(user: User): Observable<User> {
    if (user.admin) {
      return this.httpClient.post<User>(this.userBaseUri + '/administrators', user).pipe(
        catchError(this.handleError)
      );
    }
    console.log('saving user in the database');
    return this.httpClient.post<User>(this.userBaseUri + '/customers', user).pipe(
      catchError(this.handleError)
    );
  }
  update(user: User) {
    console.log('Updating user ' + user.userCode + ' in the database');
      return this.httpClient.put(this.userBaseUri + '/update', user).pipe(
        catchError(this.handleError)
      );
  }
  delete(user: User) {
    console.log('Deleting user ' + user.userCode + ' in the database');
    return this.httpClient.delete(this.userBaseUri + '/delete/' + user.userCode).pipe(
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
  resetPasswordRequest(email: string) {
    return this.httpClient.post(this.globals.backendUri + '/reset-password/' + email, {}).pipe(
      catchError(this.handleError)
    );
  }
  resetPasswordAuth(resetPasswordCode: string) {
    return this.httpClient.get<UserLogin>(this.globals.backendUri + '/reset-password/' + resetPasswordCode).pipe(
      catchError(this.handleError)
    );
  }
}
