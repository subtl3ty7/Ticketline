import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TicketPurchaseSharedServiceService, TicketState} from './ticket-purchase-shared-service.service';
import {DetailedTicket} from '../../dtos/detailed-ticket';
import {UserService} from '../../services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Background} from '../../utils/background';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import {MatHorizontalStepper} from '@angular/material/stepper';
import {Show} from '../../dtos/show';
import {SimpleEvent} from '../../dtos/simple-event';
import {forkJoin} from 'rxjs';
import {EventService} from '../../services/event.service';
import {DetailedEvent} from '../../dtos/detailed-event';
import {DatePipe} from '@angular/common';
import {delay} from 'rxjs/operators';
import {ChooseTicketComponent} from './choose-ticket/choose-ticket.component';
import {ShowService} from '../../services/show.service';

@Component({
  selector: 'app-ticket-purchase',
  templateUrl: './ticket-purchase.component.html',
  styleUrls: ['./ticket-purchase.component.css'],
  providers: [
    DatePipe,
    {provide: STEPPER_GLOBAL_OPTIONS, useValue: {showError: true}}
    ]
})

export class TicketPurchaseComponent implements OnInit, AfterViewInit {
  @ViewChild(MatHorizontalStepper) stepper: MatHorizontalStepper;
  @ViewChild(ChooseTicketComponent) chooseTicketComponent: ChooseTicketComponent;
  private error;
  private isLinear: boolean;
  private tickets: DetailedTicket[];
  private show: Show;
  private event: DetailedEvent;
  private userCode: string;
  sharedVars: FormGroup;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

  constructor(
    private _formBuilder: FormBuilder,
    private ticketPurchaseSharingService: TicketPurchaseSharedServiceService,
    private userService: UserService,
    private eventService: EventService,
    private showService: ShowService,
    private router: Router,
    private route: ActivatedRoute,
    private background: Background,
    private datePipe: DatePipe) {
    this.background.defineBackground();
  }

  ngOnInit(): void {
    // this.show = JSON.parse(sessionStorage.getItem('show'));
    this.initData();
    this.initForms();
  }

  // fixes the "create-instead-of-number-bug", replaces all icons (that are not working here) with numbers
  ngAfterViewInit() {
    this.stepper._getIndicatorType = () => 'number';
  }

  // initialize usercode, tickets, event and show
  initData() {
    const id = this.route.snapshot.paramMap.get('showId');
    this.ticketPurchaseSharingService.ticketState = TicketState.BEGIN;
    this.isLinear = false;
    this.tickets = [];
    forkJoin(
      this.userService.getCurrentUser(),
      this.showService.getShowById(Number(id))
    ).subscribe(([user, show]) => {
        this.userCode = user.userCode;
        this.show = show;
        this.initEvent();
      }
    );
  }

  // initialize event
  initEvent() {
    this.eventService.getDetailedEventByUserCode(this.show.eventCode).subscribe(
      (event) => {
        this.event = event;
      }
    );
  }

  // initialize forms used for validating whether the User is allowed to proceed to the next step
  initForms() {
    this.firstFormGroup = this._formBuilder.group({
      // selectedSeats: [[], ],
      success: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      paymentmethod: ['', Validators.required],
      purchased: ['', Validators.required],
    });
    this.sharedVars = this._formBuilder.group( {
      editable: [true],
      stepTwo: [true]
    });
  }


  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'EE dd.MM.yyyy - hh:mm');
  }

  // check if show was really successfully retrieved from session storage
  sanitycheck(): boolean {
    if (this.show) {
      return true;
    } else {
      this.error = {
        messages: ['Die Sitzung scheint abgelaufen zu sein. Bitte versuche es erneut.']
      };
      return false;
    }
  }

  chooseTicketComponentNextStep() {
    this.chooseTicketComponent.nextStep();
  }
}



