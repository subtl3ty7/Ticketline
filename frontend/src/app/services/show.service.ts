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
  private showBaseUri: string = this.globals.backendUri + '/shows';

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

  getShowsByEventLocationId(eventLocationId: number, pageSize: number): Observable<Show[]> {
    console.log('Find shows by event location id ' + eventLocationId);
    console.log(this.showBaseUri + '?eventLocationId=' + eventLocationId + '&size=' + pageSize);
    return this.httpClient.get<Show[]>(this.showBaseUri + '?eventLocationId=' + eventLocationId + '&size=' + pageSize);
  }

  // tslint:disable-next-line:max-line-length
  getDetailedShowsBy(name: string, type: string, category: string, showStartsAt: string, showEndsAt: string, duration: string, startPrice: string, pageSize: number): Observable<Show[]> {
    console.log('Load events advanced');
    if (duration !== null && duration !== '') {
      duration = 'PT' + duration + 'H';
    }
    // tslint:disable-next-line:max-line-length
    console.log('url: ' + this.showBaseUri + '?' + 'eventName=' + name + '&type=' + type + '&category=' + category + '&startsAt=' + showStartsAt + '&endsAt=' + showEndsAt + '&duration=' + duration + '&price=' + startPrice + '&size=' + pageSize);
    return this.httpClient.get<Show[]>(this.showBaseUri + '?' + 'eventName=' + name + '&type=' + type + '&category=' + category + '&startsAt=' + showStartsAt + '&endsAt=' + showEndsAt + '&duration=' + duration + '&price=' + startPrice + '&size=' + pageSize).pipe(
      catchError(this.handleError)
    );
  }

  getShowById(id: number): Observable<Show> {
    console.log('Find show by id ' + id);
    console.log(this.showBaseUri + '/' + id);
    return this.httpClient.get<Show>(this.showBaseUri + '/' + id);
  }
}
