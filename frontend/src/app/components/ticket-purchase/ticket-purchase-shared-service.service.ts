import { Injectable } from '@angular/core';

export enum TicketState {
  PURCHASE,
  RESERVE
}

@Injectable({
  providedIn: 'root'
})
export class TicketPurchaseSharedServiceService {

  ticketState: TicketState;
  isPurchased = this.methodIsPurchase(this.ticketState);

  constructor() { }

  public methodIsPurchase(ticketState: TicketState) {
    // tslint:disable-next-line:triple-equals
    return ticketState == TicketState.PURCHASE;
  }

  public isPurchase(ticketState: TicketState) {
    return this.ticketState = TicketState.PURCHASE;
  }

  public isReserve(ticketState: TicketState) {
    return this.ticketState = TicketState.RESERVE;
  }

}
