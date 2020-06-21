import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Router} from '@angular/router';
import {Observable, throwError} from 'rxjs';
import {Merchandise} from '../dtos/merchandise';
import {User} from '../dtos/user';

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

  public purchaseWithMoney(merchandise: Merchandise, userCode: String): Observable<Merchandise> {
    console.log('Purchasing merchandise product(s)');
    return this.httpClient.post<Merchandise>(this.merchandiseBaseUri + '/purchasingWithMoney/' + userCode, merchandise);
  }
  public purchaseWithPremium(merchandise: Merchandise, userCode: String): Observable<Merchandise> {
    console.log('Purchasing merchandise product ' + merchandise.merchandiseProductCode + ' with premium points');
    return this.httpClient.post<Merchandise>(this.merchandiseBaseUri + '/purchasingWithPremiumPoints/' + userCode, merchandise);
  }



}


