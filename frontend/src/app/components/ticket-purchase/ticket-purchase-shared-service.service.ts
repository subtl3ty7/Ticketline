import { Injectable } from '@angular/core';

export enum TicketState {
  PURCHASED,
  RESERVED,
  PROCESSING,
  BEGIN
}

@Injectable({
  providedIn: 'root'
})
export class TicketPurchaseSharedServiceService {

  ticketState: TicketState;

  constructor() {
    this.ticketState = TicketState.BEGIN;
  }

  public isProcessing() {
    return this.ticketState === TicketState.PROCESSING;
  }

  public isPurchased() {
    return this.ticketState === TicketState.PURCHASED;
  }

  public isReserved() {
    return this.ticketState === TicketState.RESERVED;
  }

  public isBegin() {
    return this.ticketState === TicketState.BEGIN;
  }

}
