import { Component, OnInit } from '@angular/core';
import {TicketPurchaseSharedServiceService} from '../ticket-purchase-shared-service.service';

/* enum TicketState {
  PURCHASE,
  RESERVE
}
*/
@Component({
  selector: 'app-choose-ticket',
  templateUrl: './choose-ticket.component.html',
  styleUrls: ['./choose-ticket.component.css']
})
export class ChooseTicketComponent implements OnInit {


 /* isPurchased: boolean;
  ticketState = TicketState.RESERVE;
*/
  constructor(
    private ticketPurchaseSharingService: TicketPurchaseSharedServiceService

  ) {
  }

  ngOnInit(): void {
 //   this.isPurchased = this.methodIsPurchase(this.ticketState);

 //   console.log('Is the ticket purchased?' + this.isPurchased);
  }

  /* public methodIsPurchase(ticketState: TicketState) {
    // tslint:disable-next-line:triple-equals
    return ticketState == TicketState.PURCHASE;
  }
*/
}
