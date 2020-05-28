import {Component, Input, OnInit} from '@angular/core';
import {TicketPurchaseSharedServiceService, TicketState} from '../ticket-purchase-shared-service.service';
import {EventLocation} from '../../../dtos/event-location';
import {Show} from '../../../dtos/show';
import {FormBuilder, FormGroup} from '@angular/forms';
import {DetailedTicket} from '../../../dtos/detailed-ticket';
import {TicketService} from '../../../services/ticket.service';
import {MatHorizontalStepper} from '@angular/material/stepper';

@Component({
  selector: 'app-choose-ticket',
  templateUrl: './choose-ticket.component.html',
  styleUrls: ['./choose-ticket.component.css']
})
export class ChooseTicketComponent implements OnInit {
  private error;
  @Input() stepper: MatHorizontalStepper;
  @Input() ticketPurchaseSharingService: TicketPurchaseSharedServiceService;
  private show: Show;
  @Input() firstFormGroup: FormGroup;
  @Input() sharedVars: FormGroup;
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
      // all requirements fulfilled, go to next step
      this.setSuccess();
      this.setTicket();
      this.stepper.next();
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
      this.ticket.reserved = true;
      this.tickets = [this.ticket];
      this.ticketService.reserve(this.tickets).subscribe(
        (ret) => {
          // success from backend, go to next step
          console.log('Successfully reserved ticket');
          this.setSuccess();
          this.skipStepTwo();
          this.lockOtherSteps();
          this.stepper.next();
        },
        (error) => {
          this.error = error;
        }
      );
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

  skipStepTwo() {
    console.log('Skipping step two');
    this.sharedVars.get('stepTwo').setValue(false);
  }

  // prevents the user from going back to the other steps again when the purchase is done
  lockOtherSteps() {
    console.log('Lock other steps');
    this.sharedVars.get('editable').setValue(false);
  }

  setSuccess() {
    this.firstFormGroup.controls['success'].setErrors(null);
  }
}

