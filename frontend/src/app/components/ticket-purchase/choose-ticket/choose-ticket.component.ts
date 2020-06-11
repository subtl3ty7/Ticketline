import {Component, DoCheck, Input, OnInit} from '@angular/core';
import {TicketPurchaseSharedServiceService, TicketState} from '../ticket-purchase-shared-service.service';
import {EventLocation} from '../../../dtos/event-location';
import {Show} from '../../../dtos/show';
import {FormBuilder, FormGroup} from '@angular/forms';
import {DetailedTicket} from '../../../dtos/detailed-ticket';
import {TicketService} from '../../../services/ticket.service';
import {MatHorizontalStepper} from '@angular/material/stepper';
import {Seat} from '../../../dtos/seat';
import {tick} from '@angular/core/testing';
import {EventLocationService} from '../../../services/event-location.service';
import {DetailedEvent} from '../../../dtos/detailed-event';

@Component({
  selector: 'app-choose-ticket',
  templateUrl: './choose-ticket.component.html',
  styleUrls: ['./choose-ticket.component.css']
})
export class ChooseTicketComponent implements OnInit, DoCheck {
  private error;
  @Input() stepper: MatHorizontalStepper;
  @Input() event: DetailedEvent;
  @Input() show: Show;
  @Input() firstFormGroup: FormGroup;
  @Input() sharedVars: FormGroup;
  selectedSeats: Seat[] = [];
  currentSeats = 0;
  price = 0;
  @Input() tickets: DetailedTicket[];
  @Input() userCode: string;
  private eventLocation: EventLocation;
  constructor(private ticketService: TicketService, private eventLocationService: EventLocationService, private _formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.eventLocation = this.show.eventLocation;
  }
  ngDoCheck() {
    if (this.selectedSeats.length !== this.currentSeats) {
      console.log('Changing price');
      this.currentSeats = this.selectedSeats.length;
      this.price = 0.00;
      for (const seat of this.selectedSeats) {
        this.price += this.show.price + seat.price;
      }
      // this.price = this.selectedSeats.reduce((sum, current) => sum + current.price, 0);
      this.price = +this.price.toFixed(2);
    }
  }

  nextStep() {
    if (this.selectedSeats.length > 0) {
      console.log('Next step');
      // all requirements fulfilled, go to next step
      this.setSuccess();
      this.setTickets(false);
      this.stepper.next();
      this.setFailure();
    } else {
      this.setFailure();
      this.error = {
        messages: ['Bitte wähle mindestens einen Platz aus.']
      };
    }
  }

  reserve() {
    if (this.selectedSeats.length > 0) {
      this.setTickets(true);
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
          this.setFailure();
          this.error = error;
        }
      );
    } else {
      this.setFailure();
      this.error = {
        messages: ['Bitte wähle mindestens einen Platz aus.']
      };
    }
  }

  setTickets(reserve: boolean) {
    // empty the previous ticketlist
    while (this.tickets.pop()) {}
    // fill it with new tickets
    for (const seat of this.selectedSeats) {
      const ticket = new DetailedTicket();
      ticket.userCode = this.userCode;
      ticket.show = this.show;
      ticket.seat = seat;
      ticket.price = this.price;
      ticket.reserved = reserve;
      this.tickets.push(ticket);
    }

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

  setFailure() {
    this.firstFormGroup.controls['success'].setErrors({'incorrect': true});
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

