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

  public error;
  @Input() show: Show;
  @Input() event = new DetailedEvent();
  @Input() firstFormGroup: FormGroup;
  @Input() secondFormGroup: FormGroup;
  @Input() sharedVars: FormGroup;
  @Input() stepper: MatHorizontalStepper;
  @Input() tickets: DetailedTicket[];

  constructor( private eventService: EventService,
               private ticketService: TicketService,
               private datePipe: DatePipe) { }

  ngOnInit(): void {
    // this.show = JSON.parse(sessionStorage.getItem('show'));
  }

  completePurchase() {
    if (this.secondFormGroup.controls['paymentmethod'].valid) {
      this.setTickets();
      this.ticketService.purchase(this.tickets).subscribe(
        (tickets) => {
          // success from backend, go to next step
          console.log('Successfully bought ticket');
          this.setSuccess();
          this.lockOtherSteps();
          this.stepper.next();
          this.setFailure();
        },
        (error) => {
          this.error = error;
        }
      );
    } else {
      this.error = {
        'messages' : ['Bitte wähle eine Bezahlungsmethode aus']
      };
    }
  }

  setTickets() {
    for (const ticket of this.tickets) {
      ticket.purchased = true;
    }
  }

  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'dd.MM.yyyy, hh:mm') + ' Uhr';
  }

  // prevents the user from going back to the other steps again when the purchase is done
  lockOtherSteps() {
    console.log('Lock other steps');
    this.sharedVars.get('editable').setValue(false);
  }

  setSuccess() {
    this.firstFormGroup.controls['success'].setErrors(null);
    this.secondFormGroup.controls['purchased'].setErrors(null);
  }

  setFailure() {
    this.firstFormGroup.controls['success'].setErrors({'incorrect': true});
    this.secondFormGroup.controls['purchased'].setErrors({'incorrect': true});
  }

  getSectionNameById(sectionId) {
    let name;
    this.show.eventLocation.sections.forEach((next) => {
      if (next.id === sectionId) {
        name = next.name;
      }
    });
    return name;
  }

  test() {
    console.log('test');
  }
}
