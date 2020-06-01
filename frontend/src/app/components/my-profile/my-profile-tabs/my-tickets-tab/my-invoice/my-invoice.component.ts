import {Component, Input, OnInit} from '@angular/core';
import {SimpleTicket} from '../../../../../dtos/simple-ticket';
import {TicketService} from '../../../../../services/ticket.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-my-invoice',
  templateUrl: './my-invoice.component.html',
  styleUrls: ['./my-invoice.component.css']
})
export class MyInvoiceComponent implements OnInit {
  @Input() ticket: SimpleTicket;

  constructor(private ticketService: TicketService,
              private router: Router) { }

  ngOnInit(): void {
  }

  public cancelTicket(ticketCode: String): void {
    if (this.ticket.reserved) {
      this.cancelReservedTicket(ticketCode);
    } else if (this.ticket.purchased) {
      this.cancelPurchasedTicket(ticketCode);
    }
  }
  public cancelReservedTicket(ticketCode: String): void {
    this.ticketService.cancelReservedTicket(ticketCode).subscribe(() => {
      window.location.reload();
    });

  }
  public cancelPurchasedTicket(ticketCode: String): void {
    this.ticketService.cancelPurchasedTicket(ticketCode).subscribe(() => {
      window.location.reload();
    });
  }

}
