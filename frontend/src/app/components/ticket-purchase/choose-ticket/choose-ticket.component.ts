import {Component, Input, OnInit} from '@angular/core';
import {TicketPurchaseSharedServiceService, TicketState} from '../ticket-purchase-shared-service.service';
import {EventLocation} from '../../../dtos/event-location';
import {Show} from '../../../dtos/show';
import {FormBuilder, FormGroup} from '@angular/forms';
import {DetailedTicket} from '../../../dtos/detailed-ticket';
import {TicketService} from '../../../services/ticket.service';

@Component({
  selector: 'app-choose-ticket',
  templateUrl: './choose-ticket.component.html',
  styleUrls: ['./choose-ticket.component.css']
})
export class ChooseTicketComponent implements OnInit {
  private error;
  @Input() ticketPurchaseSharingService: TicketPurchaseSharedServiceService;
  private show: Show;
  @Input() firstFormGroup: FormGroup;
  @Input() ticket: DetailedTicket;
  private ticketState = TicketState;
  public tickets: DetailedTicket[];
  private eventLocation: EventLocation;
  constructor(private ticketService: TicketService, private _formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.show = JSON.parse(sessionStorage.getItem('show'));
    this.eventLocation = this.show.eventLocationCopy;
    this.firstFormGroup.statusChanges.subscribe(
      status => {
        if (status === 'VALID') { this.nextStep(); }
        console.log(status);
      }
    );
  }

  nextStep() {
    if (this.firstFormGroup.value.count && this.firstFormGroup.value.section && this.firstFormGroup.value.seat && this.show) {
      this.setTicket();
      this.ticketPurchaseSharingService.ticketState = TicketState.PROCESSING;
    } else {
      this.error = {
        messages: ['Bitte wähle mindestens einen Platz aus.']
      };
    }
  }

  reserve() {
    if (this.firstFormGroup.value.count && this.firstFormGroup.value.section && this.firstFormGroup.value.seat && this.show) {
      this.setTicket();
      this.ticketPurchaseSharingService.ticketState = TicketState.RESERVED;
      this.ticket.isReserved = true;
      this.tickets = [this.ticket];
      this.ticketService.reserve(this.tickets).subscribe();
    } else {
      this.error = {
        messages: ['Bitte wähle mindestens einen Platz aus.']
      };
    }
  }

  setTicket() {
    this.ticket.show = this.show;
    this.ticket.seat = this.firstFormGroup.value.seat;
    this.ticket.seat.sectionId = this.firstFormGroup.value.section.id;
    this.ticket.price = this.ticket.seat.price;
  }
}

