import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Router} from '@angular/router';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Invoice} from '../dtos/invoice';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {
  private invoiceBaseUri: string = this.globals.backendUri + '/invoices';

  constructor( private httpClient: HttpClient,
               private globals: Globals,
               private router: Router) { }

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
  getInvoicesByUserCode(userCode: string): Observable<Invoice[]> {
    console.log('Load all invoices by userCode');
    return this.httpClient.get<Invoice[]>(this.invoiceBaseUri + '/' + userCode).pipe(
      catchError(this.handleError)
    );
  }

  getInvoiceByTicketCode(ticketCode: string): Observable<Invoice> {
    console.log('Load invoice by ticketCode ' + ticketCode);
    return  this.httpClient.get<Invoice>(this.invoiceBaseUri + '?ticketCode=' + ticketCode);
  }
}
