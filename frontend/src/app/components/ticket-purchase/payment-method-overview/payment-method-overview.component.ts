import {Component, Input, OnInit} from '@angular/core';
import {DetailedTicket} from '../../../dtos/detailed-ticket';
import {Show} from '../../../dtos/show';
import {TicketPurchaseSharedServiceService, TicketState} from '../ticket-purchase-shared-service.service';
import {DetailedEvent} from '../../../dtos/detailed-event';
import {EventService} from '../../../services/event.service';
import {TicketService} from '../../../services/ticket.service';
import {DatePipe} from '@angular/common';
import {FormGroup} from '@angular/forms';
import {MatHorizontalStepper} from '@angular/material/stepper';

@Component({
  selector: 'app-payment-method-overview',
  templateUrl: './payment-method-overview.component.html',
  styleUrls: ['./payment-method-overview.component.css'],
  providers: [DatePipe]
})
export class PaymentMethodOverviewComponent implements OnInit {

  private error;
  private show: Show;
  @Input() event = new DetailedEvent();
  private ticketState = TicketState;
  @Input() ticket: DetailedTicket;
  @Input() secondFormGroup: FormGroup;
  @Input() stepper: MatHorizontalStepper;
  public tickets: DetailedTicket[];

  constructor( private ticketPurchaseSharingService: TicketPurchaseSharedServiceService,
               private eventService: EventService,
               private ticketService: TicketService,
               private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.show = JSON.parse(sessionStorage.getItem('show'));
  }

  completePurchase() {
    this.ticketPurchaseSharingService.ticketState = TicketState.PURCHASED;
    this.ticket.purchased = true;
    this.tickets = [this.ticket];
    this.ticketService.purchase(this.tickets).subscribe(
      (ret) => {
        // success from backend, go to next step
        console.log('Successfully bought ticket');
        this.secondFormGroup.controls['purchased'].setErrors(null);
        this.lockOtherSteps();
        this.stepper.next();
      },
      (error) => {
        this.error = error;
      }
    );
  }

  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'dd.MM.yyyy, hh:mm') + ' Uhr';
  }

  // prevents the user from going back to the other steps again when the purchase is done
  lockOtherSteps() {
    console.log('Lock other steps');
    this.secondFormGroup.get('editable').setValue(false);
  }

}
