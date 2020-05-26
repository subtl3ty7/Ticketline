import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable, throwError} from 'rxjs';
import {DetailedEvent} from '../dtos/detailed-event';
import {SimpleEvent} from '../dtos/simple-event';
import {catchError, tap} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})

export class EventService {
  error: boolean = false;
  errorMessage: string = '';
  private eventBaseUri: string = this.globals.backendUri + '/events';
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
  getTop10Events(): Observable<SimpleEvent[]> {
    console.log('Load top 10 events.');
    return this.httpClient.get<SimpleEvent[]>(this.eventBaseUri + '/top10');
  }
  getAllEvents(): Observable<SimpleEvent[]> {
    console.log('Load all events.');
    return this.httpClient.get<SimpleEvent[]>(this.eventBaseUri + '/all').pipe(
      catchError(this.handleError)
    );
  }
  getDetailedEventByUserCode(eventCode: string): Observable<DetailedEvent> {
    console.log('Load event by EventCode');
    return this.httpClient.get<DetailedEvent>(this.eventBaseUri + '/' + eventCode);
  }

  getDetailedEventsBy(name: string, startsAt: string, endsAt: string, startPrice: string,
                      endPrice: string, type: string, category: string, prices: string) {
  }

  getDetailedEventsByName(name: string): Observable<DetailedEvent[]> {
    console.log('Load events by event name');
    console.log('url: ' + this.eventBaseUri + '?name=' + name);
    return this.httpClient.get<DetailedEvent[]>(this.eventBaseUri + '?name=' + name).pipe(
      catchError(this.handleError)
    );
  }

  getSimpleEventsByArtistId(artistId: number) {
    console.log('Load events by artist id');
    console.log('url: ' + this.eventBaseUri + '?artistId=' + artistId);
    return this.httpClient.get<SimpleEvent[]>(this.eventBaseUri + '?artistId=' + artistId).pipe(
      catchError(this.handleError)
    );
  }
}
