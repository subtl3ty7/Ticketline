import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Router} from '@angular/router';
import {Observable, throwError} from 'rxjs';
import {DetailedEvent} from '../dtos/detailed-event';
import {catchError} from 'rxjs/operators';
import {Show} from '../dtos/show';

@Injectable({
  providedIn: 'root'
})
export class ShowService {
  private showBaseUri: string = this.globals.backendUri + '/shows';

  constructor( private httpClient: HttpClient,
               private globals: Globals,
               private router: Router) { }
  getShowByShowId(showId: number): Observable<Show> {
    console.log('Load show by showId');
    return this.httpClient.get<Show>(this.showBaseUri + '/' + showId).pipe(
      catchError(this.handleError)
    );
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
}
