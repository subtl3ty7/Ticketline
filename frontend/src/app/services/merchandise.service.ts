import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Router} from '@angular/router';
import {Observable, throwError} from 'rxjs';
import {Merchandise} from '../dtos/merchandise';

@Injectable({
  providedIn: 'root'
})
export class MerchandiseService {

  error: boolean = false;
  errorMessage: string = '';
  private merchandiseBaseUri: string = this.globals.backendUri + '/merchandise';


  constructor(private httpClient: HttpClient,
              private globals: Globals,
              private router: Router) {

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

  getAllMerchandiseProducts(): Observable<Merchandise[]> {
    console.log('Load merchandise products');
    return this.httpClient.get<Merchandise[]>(this.merchandiseBaseUri + '/all');
  }

  getMerchandiseProductByProductCode(merchandiseProductCode: string): Observable<Merchandise> {
    console.log('Load merchandise product with the code ' + merchandiseProductCode);
    return this.httpClient.get<Merchandise>(this.merchandiseBaseUri + '/' + merchandiseProductCode);
  }


}


