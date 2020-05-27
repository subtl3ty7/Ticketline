import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TicketPurchaseSharedServiceService, TicketState} from './ticket-purchase-shared-service.service';
import {DetailedTicket} from '../../dtos/detailed-ticket';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import {Background} from '../../utils/background';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import {MatHorizontalStepper} from '@angular/material/stepper';

@Component({
  selector: 'app-ticket-purchase',
  templateUrl: './ticket-purchase.component.html',
  styleUrls: ['./ticket-purchase.component.css'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: {showError: true}
  }]
})

export class TicketPurchaseComponent implements OnInit, AfterViewInit {
  @ViewChild(MatHorizontalStepper) stepper: MatHorizontalStepper;

  private isLinear: boolean;
  private ticket: DetailedTicket;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

  constructor(
    private _formBuilder: FormBuilder,
    private ticketPurchaseSharingService: TicketPurchaseSharedServiceService,
    private userService: UserService, private background: Background) {
    this.background.defineBackground();
  }

  ngOnInit(): void {
    this.setTicket();
    this.initForms();
  }

  // fixes the "create-instead-of-number-bug", replaces all icons (that are not working here) with numbers
  ngAfterViewInit() {
    this.stepper._getIndicatorType = () => 'number';
  }

  setTicket() {
    this.ticketPurchaseSharingService.ticketState = TicketState.BEGIN;
    this.isLinear = false;
    this.ticket = new DetailedTicket();
    this.userService.getCurrentUser().subscribe((user) => {
      this.ticket.userCode = user.userCode;
    });
  }


  initForms() {
    this.firstFormGroup = this._formBuilder.group({
      ticketCountCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }
}


