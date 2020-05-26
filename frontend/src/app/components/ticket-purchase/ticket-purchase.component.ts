import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TicketPurchaseSharedServiceService, TicketState} from './ticket-purchase-shared-service.service';
import {DetailedTicket} from '../../dtos/detailed-ticket';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import {Background} from '../../utils/background';

@Component({
  selector: 'app-ticket-purchase',
  templateUrl: './ticket-purchase.component.html',
  styleUrls: ['./ticket-purchase.component.css']
})

export class TicketPurchaseComponent implements OnInit {


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
    this.ticketPurchaseSharingService.ticketState = TicketState.BEGIN;
    this.isLinear = false;
    this.ticket = new DetailedTicket();
    this.userService.getCurrentUser().subscribe((user) => {
      this.ticket.userCode = user.userCode;
    });
    this.firstFormGroup = this._formBuilder.group({
      ticketCountCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }
}


