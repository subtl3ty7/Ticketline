import { Injectable } from '@angular/core';
import {SimpleTicket} from '../dtos/simple-ticket';
import {DetailedTicket} from '../dtos/detailed-ticket';
import {Globals} from '../global/globals';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {User} from '../dtos/user';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private ticketBaseUri: string = this.globals.backendUri + '/tickets';

  constructor( private httpClient: HttpClient,
               private globals: Globals,
               private router: Router) { }


  public purchase(tickets: DetailedTicket[]): Observable<DetailedTicket[]> {
    console.log('saving user in the database');
    sessionStorage.removeItem('show');
    return this.httpClient.post<DetailedTicket[]>(this.ticketBaseUri + '/purchase', tickets).pipe(
      catchError(this.handleError)
    );
  }

  public reserve(tickets: DetailedTicket[]): Observable<DetailedTicket[]> {
    console.log('saving user in the database');
    sessionStorage.removeItem('show');
    return this.httpClient.post<DetailedTicket[]>(this.ticketBaseUri + '/reserve', tickets).pipe(
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
  getDetailedTicketsByUserCode(userCode: string): Observable<SimpleTicket[]> {
    console.log('Load all tickets by userCode');
    return this.httpClient.get<SimpleTicket[]>(this.ticketBaseUri + '/' + userCode).pipe(
      catchError(this.handleError)
    );
  }
  cancelReservedTicket(ticketCode: String) {
    console.log('Cancel ticket with ticketCode ' + ticketCode);
    return this.httpClient.delete(this.ticketBaseUri + '/cancelReserved/' + ticketCode).pipe(
      catchError(this.handleError)
    );
  }
  cancelPurchasedTicket(ticketCode: String) {
    console.log('Cancel ticket with ticketCode ' + ticketCode);
    return this.httpClient.delete(this.ticketBaseUri + '/cancelPurchased/' + ticketCode).pipe(
      catchError(this.handleError)
    );
  }
}
