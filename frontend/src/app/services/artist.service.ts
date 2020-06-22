import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable, throwError} from 'rxjs';
import {DetailedEvent} from '../dtos/detailed-event';
import {SimpleEvent} from '../dtos/simple-event';
import {catchError, tap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {Artist} from '../dtos/artist';

@Injectable({
  providedIn: 'root'
})

export class ArtistService {
  error: boolean = false;
  errorMessage: string = '';
  private artistBaseUri: string = this.globals.backendUri + '/artists';
  artistFirstName: string;
  artistLastName: string;
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
  getArtistsByFirstAndLastName(firstName: string, lastName: string, size: number): Observable<Artist[]> {
    console.log('Load artists by first and last name.');
    let params = new HttpParams();
    firstName = firstName === undefined ? '' : firstName;
    lastName = lastName === undefined ? '' : lastName;
    params = params.set('firstName', firstName);
    params = params.set('lastName', lastName);
    params = params.set('size', size.toString());
    const searchForm = '?' + params.toString();

    console.log('Navigating to backend with URI: ' + this.artistBaseUri + searchForm);
    return this.httpClient.get<Artist[]>(this.artistBaseUri + searchForm).pipe(
      catchError(this.handleError)
    );
  }
}
