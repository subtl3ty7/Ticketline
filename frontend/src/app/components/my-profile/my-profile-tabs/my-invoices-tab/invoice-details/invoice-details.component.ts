import {Component, Input, OnInit} from '@angular/core';
import {Invoice} from '../../../../../dtos/invoice';
import * as jsPDF from 'jspdf';
import {Merchandise} from '../../../../../dtos/merchandise';
import {MerchandiseService} from '../../../../../services/merchandise.service';
import {User} from '../../../../../dtos/user';
import {InvoiceService} from '../../../../../services/invoice.service';
import {UserService} from '../../../../../services/user.service';

@Component({
  selector: 'app-invoice-details',
  templateUrl: './invoice-details.component.html',
  styleUrls: ['./invoice-details.component.css']
})
export class InvoiceDetailsComponent implements OnInit {
  @Input() invoice: Invoice;
  @Input() merchandise: Merchandise;
  public currentUser: User = new User();
  error = false;
  constructor( private merchandiseService: MerchandiseService,
               private userService: UserService) {
  }
  printInvoice(invoice: Invoice) {
    const doc = new jsPDF('p', 'mm', 'a4');
    this.drawForm(doc, invoice);
    doc.autoPrint();
    window.open(doc.output('bloburl'), '_blank');
  }
  private drawForm (doc: jsPDF, invoice: Invoice) {
    doc.setFont('Times');
    doc.setFontType('bold');
    doc.setFontSize(30);
    let date;
    if (invoice.invoice_category.startsWith('TICKET')) {
      date = invoice.tickets[0].purchaseDate.replace('T', ' ');
      if (invoice.invoice_type === 'Kaufrechnung') {
        doc.text('Ticket Kaufrechnung ', 15, 20, null, null, 'left');
      } else {
        doc.text('Ticket Stornorechnung ', 15, 20, null, null, 'left');
      }
    } else {
      date = invoice.generatedAt.replace('T', ' ');
      doc.text('Merchandise Kaufrechnung ', 15, 20, null, null, 'left');
    }
    doc.setFontSize(15);
    doc.text('Ticketline™', 15, 40, null, null, 'left');


    date = date.substring(0, date.indexOf('.'));
    let date1 = invoice.generatedAt.replace('T', ' ');
    date1 = date1.substring(0, date1.indexOf('.'));
    doc.setFontType('normal');
    doc.setFontSize(11);
    doc.text('Kundenname: ' + this.currentUser.firstName + ' ' + this.currentUser.lastName, 15, 50, null, null, 'left');
    doc.text('Kundennummer: ' + this.currentUser.userCode, 15, 60, null, null, 'left');
    doc.text('Rechnungsnummer: ' + invoice.invoiceNumber, 15, 70, null, null, 'left');

    if (invoice.invoice_type === 'Kaufrechnung') {
      doc.text('Rechnungsdatum: ' + date1, 130, 70, null, null);
    } else {
      doc.text('Kaufdatum: ' + date, 130, 60, null, null, 'left');
      doc.text('Stornierungsdatum: ' + date1, 130, 70, null, null);
    }

    if (invoice.invoice_category.startsWith('TICKET')) {
      doc.setFontType('bold');
      // tslint:disable-next-line:max-line-length
      doc.text('Ticket Code                          Event / Show id                                   Seat id                   Price(€)', 20, 100, null, null, 'left');
      let i, sum = 0, j = 0, seat_num, pages = 0;
      doc.setFontType('normal');

      this.drawTable(invoice.tickets.length + 1, doc);

      for (i = 0; i < invoice.tickets.length; i++) {
        if (j === 17) {
          doc.addPage();
          j = 0;
          pages++;
          this.drawTable(invoice.tickets.length - 17 * pages, doc);
        }
        seat_num = invoice.tickets[i].seatId.toString();
        doc.line(15, 95 + 10 * (j + 2), 180, 95 + 10 * (j + 2));
        doc.text('' + invoice.tickets[i].ticketCode, 20, 110 + j * 10, null, null);
        doc.text('' + invoice.tickets[i].eventName + ' / ' + invoice.tickets[i].showId, 65, 110 + j * 10, null, null);
        doc.text('' + seat_num, 125, 110 + j * 10, null, null);
        const price = invoice.tickets[i].price;
        doc.text('' + price.toFixed(2), 155, 110 + j * 10, null, null);
        sum += price;
        j++;
      }
      const vat = sum * 0.1;
      const netto = sum - vat;
      doc.text('Total vor VAT                ' + netto.toFixed(2) + '€', 135, 115 + j * 10, null, null);
      doc.text('VAT (10%)                    ' + vat.toFixed(2) + '€', 135, 120 + j * 10, null, null);
      doc.line(110, 121 + j * 10, 200, 121 + j * 10);
      doc.setFontType('bold');
      if (invoice.invoice_type === 'Kaufrechnung') {
        doc.text('Total                              ' + sum.toFixed(2) + '€', 135, 125 + j * 10, null, null);
      } else {
        doc.text('Stornierung Betrag                ' + sum.toFixed(2) + '€', 120, 125 + j * 10, null, null);
      }
    } else {
      doc.setFontType('bold');
      // tslint:disable-next-line:max-line-length
      doc.text('Merch Code                          Product name                                   Premium                   Price(€)', 20, 100, null, null, 'left');
      doc.setFontType('normal');

      this.drawTable(2, doc);
      doc.line(15, 95 + 20, 180, 95 + 20);
      doc.text('' + invoice.merchandise_code, 20, 110, null, null);
      doc.text('' + this.merchandise.merchandiseProductName, 70, 110, null, null);
      doc.text('' + this.merchandise.premiumPrice, 130, 110, null, null);
      const price = this.merchandise.price;
      doc.text('' + price.toFixed(2), 155, 110, null, null);
      const vat = price * 0.1;
      const netto = price - vat;
      doc.text('Total vor VAT                ' + netto.toFixed(2) + '€', 135, 125, null, null);
      doc.text('VAT (10%)                    ' + vat.toFixed(2) + '€', 135, 130, null, null);
      doc.line(110, 131, 200, 131);
      doc.setFontType('bold');
      if (invoice.invoice_type === 'Kaufrechnung') {
        doc.text('Total                              ' + price.toFixed(2) + '€', 135, 135, null, null);
      } else {
        doc.text('Stornierung Betrag                ' + price.toFixed(2) + '€', 125, 135, null, null);
      }
    }
  }
  private drawTable(x: number, doc: jsPDF) {
    doc.line(15, 95, 180, 95);
    doc.line(15, 95 + 10, 180, 95 + 10);
    doc.line(15, 95, 15, 95 + 10 * x);
    doc.line(45, 95, 45, 95 + 10 * x);
    doc.line(115, 95, 115, 95 + 10 * x);
    doc.line(145, 95, 145, 95 + 10 * x);
    doc.line(180, 95, 180, 95 + 10 * x);
  }
  ngOnInit(): void {
    this.loadUser();
  }
  private loadUser() {
    this.userService.getCurrentUser().subscribe(
      (user: User) => {
        Object.assign(this.currentUser, user);
      },
      (error) => {
        this.error = error.error;
      }
    );
  }
}
