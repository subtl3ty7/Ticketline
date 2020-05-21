import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ChooseTicketComponent} from './choose-ticket/choose-ticket.component';
import {TicketPurchaseSharedServiceService} from './ticket-purchase-shared-service.service';

/* enum TicketState {
  PURCHASE,
  RESERVE
}
*/
@Component({
  selector: 'app-ticket-purchase',
  templateUrl: './ticket-purchase.component.html',
  styleUrls: ['./ticket-purchase.component.css']
})

export class TicketPurchaseComponent implements OnInit {

  private isLinear: boolean;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

/*  isPurchased: boolean;
  ticketState = TicketState.PURCHASE;
*/
  constructor(
    private _formBuilder: FormBuilder,
    private ticketPurchaseSharingService: TicketPurchaseSharedServiceService
  ) { }

  ngOnInit(): void {

    this.ticketPurchaseSharingService.isPurchase(this.ticketPurchaseSharingService.ticketState);

    console.log(this.ticketPurchaseSharingService.methodIsPurchase(this.ticketPurchaseSharingService.ticketState));

    this.isLinear = false;

    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }


  /* public methodIsPurchase(ticketState: TicketState) {
    // tslint:disable-next-line:triple-equals
    return ticketState == TicketState.PURCHASE;
  } */
}


