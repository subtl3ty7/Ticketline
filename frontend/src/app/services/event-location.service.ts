import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable, throwError} from 'rxjs';
import {DetailedEvent} from '../dtos/detailed-event';
import {SimpleEvent} from '../dtos/simple-event';
import {catchError, tap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {EventLocation} from '../dtos/event-location';

@Injectable({
  providedIn: 'root'
})

export class EventLocationService {
  error: boolean = false;
  errorMessage: string = '';
  private eventBaseUri: string = this.globals.backendUri + '/eventLocations';
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
  getAllEventLocations(): Observable<EventLocation[]> {
    console.log('Load all event locations.');
    return this.httpClient.get<EventLocation[]>(this.eventBaseUri + '/all').pipe(
      catchError(this.handleError)
    );
  }

  getLocationsByName(locationName: string, pageSize: number): Observable<EventLocation[]> {
    console.log('Load all locations with name: ' + locationName);
    return this.httpClient.get<EventLocation[]>(this.eventBaseUri + '?name=' + locationName + '&size=' + pageSize).pipe(
      catchError(this.handleError)
    );
  }

  getLocationsAdvanced(name: string, street: string, city: string, country: string, plz: string, pageSize: number) {
    console.log('Load all locations with advanced parameters');
    console.log(this.eventBaseUri + '?name=' + name + '&description=' + '&street=' + street + '&city=' + city + '&country=' + country + '&plz=' + plz + '&size=' + pageSize);
    return this.httpClient.get<EventLocation[]>(this.eventBaseUri + '?name=' + name + '&description=' + '&street=' + street + '&city=' + city + '&country=' + country + '&plz=' + plz + '&size=' + pageSize).pipe(
      catchError(this.handleError)
    );
  }

  getLocationById(locationId: number): Observable<EventLocation> {
    console.log('Load location with id: ' + locationId);
    return this.httpClient.get<EventLocation>(this.eventBaseUri + '/' + locationId).pipe(
      catchError(this.handleError)
    );
  }
}
