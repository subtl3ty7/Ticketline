import { Component, OnInit } from '@angular/core';
import {TicketPurchaseSharedServiceService} from '../ticket-purchase-shared-service.service';

import {Seat} from '../../../dtos/seat';
import {SeatService} from '../../../services/seat.service';

import {Section} from '../../../dtos/section';


import {Show} from '../../../dtos/show';


import {EventLocation} from '../../../dtos/event-location';



@Component({
  selector: 'app-choose-ticket',
  templateUrl: './choose-ticket.component.html',
  styleUrls: ['./choose-ticket.component.css']
})
export class ChooseTicketComponent implements OnInit {

  constructor(
    private ticketPurchaseSharingService: TicketPurchaseSharedServiceService  ) {
  }

  ngOnInit(): void {
  }
}
