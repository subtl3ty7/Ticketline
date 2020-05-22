import {Component, Input, OnInit} from '@angular/core';
import {DetailedTicket} from '../../../dtos/detailed-ticket';
import {Show} from '../../../dtos/show';
import {TicketPurchaseSharedServiceService, TicketState} from '../ticket-purchase-shared-service.service';
import {DetailedEvent} from '../../../dtos/detailed-event';
import {EventService} from '../../../services/event.service';
import {TicketService} from '../../../services/ticket.service';

@Component({
  selector: 'app-payment-method-overview',
  templateUrl: './payment-method-overview.component.html',
  styleUrls: ['./payment-method-overview.component.css']
})
export class PaymentMethodOverviewComponent implements OnInit {

  private show: Show;
  private event = new DetailedEvent();
  private ticketState = TicketState;
  @Input() ticket: DetailedTicket;
  public tickets: DetailedTicket[];

  constructor( private ticketPurchaseSharingService: TicketPurchaseSharedServiceService,
               private eventService: EventService,
               private ticketService: TicketService) { }

  ngOnInit(): void {
    this.show = JSON.parse(sessionStorage.getItem('show'));
    this.eventService.getDetailedEventByUserCode(this.show.eventCode).subscribe((event) => {
      this.event = event;
    });
  }

  completePurchase() {
    this.ticketPurchaseSharingService.ticketState = TicketState.PURCHASED;
    this.ticket.isPurchased = true;
    this.tickets = [this.ticket];
    this.ticketService.purchase(this.tickets).subscribe();
  }
}
