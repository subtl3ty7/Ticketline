import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable, throwError} from 'rxjs';
import {DetailedEvent} from '../dtos/detailed-event';
import {SimpleEvent} from '../dtos/simple-event';
import {catchError, tap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {EventCategories} from '../dtos/event-categories';
import {EventTypes} from '../dtos/event-types';

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
  getTop10EventsByCategory(category: string): Observable<SimpleEvent[]> {
    console.log('Load top 10 events.');
    return this.httpClient.get<SimpleEvent[]>(this.eventBaseUri + '/top10/' + category);
  }
  getAllEvents(size: number): Observable<SimpleEvent[]> {
    console.log('Load with size ' + size);
    return this.httpClient.get<SimpleEvent[]>(this.eventBaseUri + '/all' + '?size=' + size);
  }
  getDetailedEventByUserCode(eventCode: string): Observable<DetailedEvent> {
    console.log('Load event by EventCode');
    return this.httpClient.get<DetailedEvent>(this.eventBaseUri + '/' + eventCode);
  }


  getSimpleEventsByName(name: string, pageSize: number): Observable<SimpleEvent[]> {
    console.log('Load events by event name');
    console.log('url: ' + this.eventBaseUri + '?name=' + name + '&size=' + pageSize);
    return this.httpClient.get<SimpleEvent[]>(this.eventBaseUri + '?name=' + name + '&size=' + pageSize).pipe(
      catchError(this.handleError)
    );
  }

  getSimpleEventsByArtistId(artistId: number, pageSize: number) {
    console.log('Load events by artist id');
    console.log('url: ' + this.eventBaseUri + '?artistId=' + artistId);
    return this.httpClient.get<SimpleEvent[]>(this.eventBaseUri + '?artistId=' + artistId + '&size=' + pageSize).pipe(
      catchError(this.handleError)
    );
  }

  getSimpleEventsByParameters(event: SimpleEvent, size: number): Observable<SimpleEvent[]> {
    if (!event.startsAt) {
      event.startsAt = new Date(2000, 1, 1);
    }
    if (!event.endsAt) {
      event.endsAt = new Date(3000, 1, 1);
    }
    const params = new HttpParams()
      .set('eventCode', event.eventCode)
      .set('name', event.name)
      .set('startRange', event.startsAt.toDateString())
      .set('endRange', event.endsAt.toDateString())
      .set('size', size.toString());
    return this.httpClient.get<SimpleEvent[]>(this.eventBaseUri + '/', {params});
  }

  save(event: DetailedEvent) {
    return this.httpClient.post<DetailedEvent>(this.eventBaseUri + '/', event);
  }

  getAllEventCategories(): Observable<EventCategories[]> {
    console.log('Load all event categories.');
    return this.httpClient.get<EventCategories[]>(this.eventBaseUri + '/eventCategories');
  }
  getAllEventTypes(): Observable<EventTypes[]> {
    console.log('Load all event categories.');
    return this.httpClient.get<EventTypes[]>(this.eventBaseUri + '/eventTypes');
  }

  getSimpleEventsBy(name: string, type: string, category: string, startsAt: string, endsAt: string, duration: string, pageSize: number): Observable<SimpleEvent[]> {
    console.log('Load events advanced');
    if (startsAt !== '') {
      startsAt += 'T00:00';
    }
    if (endsAt !== '') {
      endsAt += 'T23:59';
    }
    if (duration !== '') {
      duration = 'PT' + duration + 'H';
    }
    console.log('url: ' + this.eventBaseUri + '?eventName=' + name + '&type=' + type + '&category=' + category + '&startsAt=' + startsAt + '&endsAt=' + endsAt + '&showDuration=' + duration);
    return this.httpClient.get<SimpleEvent[]>(this.eventBaseUri + '?' + 'eventName=' + name + '&type=' + type + '&category=' + category + '&startsAt=' + startsAt + '&endsAt=' + endsAt + '&showDuration=' + duration + '&size=' + pageSize).pipe(
      catchError(this.handleError)
    );
  }
}
