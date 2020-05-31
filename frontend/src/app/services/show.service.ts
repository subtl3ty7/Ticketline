import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable, throwError} from 'rxjs';
import {DetailedEvent} from '../dtos/detailed-event';
import {SimpleEvent} from '../dtos/simple-event';
import {catchError, tap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {Show} from '../dtos/show';

@Injectable({
  providedIn: 'root'
})

export class ShowService {
  error: boolean = false;
  errorMessage: string = '';
  private eventBaseUri: string = this.globals.backendUri + '/shows';

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

  getShowsByEventLocationId(eventLocationId: number): Observable<Show[]> {
    console.log('Find shows by event location id ' + eventLocationId);
    console.log(this.eventBaseUri + '?eventLocationId=' + eventLocationId);
    return this.httpClient.get<Show[]>(this.eventBaseUri + '?eventLocationId=' + eventLocationId);
  }

  getDetailedShowsBy(name: string, type: string, category: string, showStartsAt: string, showEndsAt: string, duration: string, startPrice: string) {
    console.log('Load events advanced');
    console.log('url: ' + this.eventBaseUri + 'eventName=' + name + '&type=' + type + '&category=' + category + '&startsAt=' + showStartsAt + '&endsAt=' + showEndsAt + '&showDuration=' + duration);
    return this.httpClient.get<SimpleEvent[]>(this.eventBaseUri + 'eventName=' + name + '&type=' + type + '&category=' + category + '&startsAt=' + showStartsAt + '&endsAt=' + showEndsAt + '&showDuration=' + duration).pipe(
      catchError(this.handleError)
    );
  }
}
