import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TicketPurchaseSharedServiceService, TicketState} from '../ticket-purchase-shared-service.service';
import {EventLocation} from '../../../dtos/event-location';
import {Show} from '../../../dtos/show';
import {FormGroup} from '@angular/forms';
import {Section} from '../../../dtos/section';
import {Seat} from '../../../dtos/seat';
import {DetailedTicket} from '../../../dtos/detailed-ticket';
import {TicketService} from '../../../services/ticket.service';

@Component({
  selector: 'app-choose-ticket',
  templateUrl: './choose-ticket.component.html',
  styleUrls: ['./choose-ticket.component.css']
})
export class ChooseTicketComponent implements OnInit {

  @Input() ticketPurchaseSharingService: TicketPurchaseSharedServiceService;
  private show: Show;
  @Input() ticket: DetailedTicket;
  @Input() firstFormGroup: FormGroup;
  private ticketState = TicketState;
  public tickets: DetailedTicket[];
  private eventLocation: EventLocation;
  private chosenSection: Section;
  private chosenSeat: Seat;
  constructor(private ticketService: TicketService) {
  }

  ngOnInit(): void {
    this.show = JSON.parse(sessionStorage.getItem('show'));
    this.eventLocation = this.show.eventLocationCopy;
    console.log(this.eventLocation);
  }

  nextStep(ticketState: TicketState) {
    if (this.chosenSection && this.show && this.chosenSeat) {
      this.ticket.show = this.show;
      this.ticket.seat = this.chosenSeat;
      console.log(this.ticket.seat);
      this.ticket.seat.sectionId = this.chosenSection.id;
      this.ticketPurchaseSharingService.ticketState = ticketState;
      if (ticketState === TicketState.RESERVED) {
        this.ticket.reserved = true;
        this.tickets = [this.ticket];
        this.ticketService.reserve(this.tickets).subscribe();
      }
    }
  }
}

