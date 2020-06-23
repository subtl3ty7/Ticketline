import {Component, Input, OnInit} from '@angular/core';
import {SimpleTicket} from '../../../../../dtos/simple-ticket';
import {TicketService} from '../../../../../services/ticket.service';
import {InvoiceService} from '../../../../../services/invoice.service';
import {Router} from '@angular/router';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-my-invoice',
  templateUrl: './my-invoice.component.html',
  styleUrls: ['./my-invoice.component.css']
})
export class MyInvoiceComponent implements OnInit {
  @Input() ticket: SimpleTicket;
  error;

  constructor(private ticketService: TicketService,
              private invoiceService: InvoiceService,
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

  public goToInvoice() {
    this.invoiceService.getInvoiceByTicketCode(this.ticket.ticketCode).subscribe(
      (invoice) => {
        this.router.navigate(['/my-profile', 'my-invoices'], { queryParams: { invoiceId: invoice.id } });
      },
      (error) => {
        this.error = error;
      }
    );
  }

  public purchaseTicket(ticketCode: String, ticket: SimpleTicket) {
    const ticketsToReserve: Array<SimpleTicket> = new Array<SimpleTicket>();
    ticketsToReserve.push(ticket);
    this.ticketService.purchaseReservedTickets(ticketCode, ticketsToReserve).subscribe(
      o => {  window.location.reload();}
    );
  }

}
