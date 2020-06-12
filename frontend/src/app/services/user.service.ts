import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable, throwError} from 'rxjs';
import {User} from '../dtos/user';
import {catchError, tap} from 'rxjs/operators';
import {UserLogin} from '../dtos/user-login';
import {ChangePassword} from '../dtos/change-password';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  error: boolean = false;
  errorMessage: string = '';
  private userBaseUri: string = this.globals.backendUri + '/users';
  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getAllUsers(): Observable<User[]> {
    console.log('Load all users.');
    return this.httpClient.get<User[]>(this.userBaseUri + '/all');
  }

  getUsersByParameters(user: User): Observable<User[]> {
    const params = new HttpParams()
    .set('userCode', user.userCode)
    .set('firstName', user.firstName)
    .set('lastName', user.lastName)
    .set('email', user.email);
    return this.httpClient.get<User[]>(this.userBaseUri + '/', {params});
  }

  getUserByUserCode(userCode: string): Observable<User> {
    console.log('Load user by UserCode');
    return this.httpClient.get<User>(this.userBaseUri + '/' + userCode);
  }

  getCurrentUser(): Observable<User> {
    console.log('Load user by UserCode');
    return this.httpClient.get<User>(this.userBaseUri + '/my-profile');
  }

  save(user: User): Observable<User> {
    if (user.admin) {
      return this.httpClient.post<User>(this.userBaseUri + '/administrators', user);
    }
    console.log('saving user in the database');
    return this.httpClient.post<User>(this.userBaseUri + '/customers', user);
  }

  update(user: User) {
    console.log('Updating user ' + user.userCode + ' in the database');
      return this.httpClient.put(this.userBaseUri + '/update', user);
  }

  delete(user: User) {
    console.log('Deleting user ' + user.userCode + ' in the database');
    return this.httpClient.delete(this.userBaseUri + '/' + user.userCode);
  }

  blockUser(userCode: string) {
    console.log('Block user by UserCode');
    return this.httpClient.get<User>(this.userBaseUri + '/block/' + userCode);
  }

  unblockUser(userCode: string) {
    console.log('Unblock user by UserCode');
    return this.httpClient.get<User>(this.userBaseUri + '/unblock/' + userCode);
  }

  resetPasswordRequest(email: string) {
    return this.httpClient.post(this.globals.backendUri + '/reset-password/' + email, {});
  }

  resetPasswordAuth(resetPasswordCode: string) {
    return this.httpClient.get<UserLogin>(this.globals.backendUri + '/reset-password/' + resetPasswordCode);
  }

  changePasswordCustomer(changePassword: ChangePassword) {
    return this.httpClient.put(this.userBaseUri + '/change-password/', changePassword);
  }
}
