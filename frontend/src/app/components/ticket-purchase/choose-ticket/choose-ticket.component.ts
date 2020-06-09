import {Component, DoCheck, Input, OnInit} from '@angular/core';
import {TicketPurchaseSharedServiceService, TicketState} from '../ticket-purchase-shared-service.service';
import {EventLocation} from '../../../dtos/event-location';
import {Show} from '../../../dtos/show';
import {FormBuilder, FormGroup} from '@angular/forms';
import {DetailedTicket} from '../../../dtos/detailed-ticket';
import {TicketService} from '../../../services/ticket.service';
import {MatHorizontalStepper} from '@angular/material/stepper';
import {Seat} from '../../../dtos/seat';

@Component({
  selector: 'app-choose-ticket',
  templateUrl: './choose-ticket.component.html',
  styleUrls: ['./choose-ticket.component.css']
})
export class ChooseTicketComponent implements OnInit, DoCheck {
  private error;
  @Input() stepper: MatHorizontalStepper;
  @Input() ticketPurchaseSharingService: TicketPurchaseSharedServiceService;
  private show: Show;
  @Input() firstFormGroup: FormGroup;
  @Input() sharedVars: FormGroup;
  selectedSeats: Seat[] = [];
  currentSeats = 0;
  price = 0;
  @Input() ticket: DetailedTicket;
  tickets: DetailedTicket[] = [];
  private ticketState = TicketState;
  private eventLocation: EventLocation;
  constructor(private ticketService: TicketService, private _formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.show = JSON.parse(sessionStorage.getItem('show'));
    this.eventLocation = this.show.eventLocationCopy;
    this.firstFormGroup.statusChanges.subscribe(
      status => {
        if (status === 'VALID') { this.nextStep(); }
      }
    );
  }
  ngDoCheck() {
    if (this.selectedSeats.length !== this.currentSeats) {
      this.currentSeats = this.selectedSeats.length;
      this.price = this.selectedSeats.reduce((sum, current) => sum + current.price, 0);
      this.price = +this.price.toFixed(2);
    }
  }

  nextStep() {
    if (this.selectedSeats.length > 0 && this.show) {
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
    if (this.selectedSeats.length > 0 && this.show) {
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
      this.ticket.seat = this.selectedSeats[0];
      this.ticket.seat.sectionId = this.selectedSeats[0].sectionId;
      this.ticket.price = this.price;

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

  getSectionNameById(sectionId) {
    let name;
    this.eventLocation.sections.forEach((next) => {
      if (next.id === sectionId) {
        name = next.name;
      }
    });
    return name;
  }
}

