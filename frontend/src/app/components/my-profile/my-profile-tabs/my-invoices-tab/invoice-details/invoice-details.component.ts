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

    let lineOffset = 70 + 25;
    let textOffset = 70 + 30;

    if (invoice.invoice_category.startsWith('TICKET')) {
      const maxLineLength = 65.0;
      const eventName0: string[] = doc.splitTextToSize(invoice.tickets[0].eventName, maxLineLength);
      const numberOfLinesPerTicket = eventName0.length;
      const pixelsPerTicket = numberOfLinesPerTicket * 5 + 2;
      const pageHeight = 260;
      console.log('EventNameLength: ' + doc.getTextWidth(invoice.tickets[0].eventName));
      console.log('NumberOfLinesPerTicket: '  + numberOfLinesPerTicket);
      console.log('pixelsPerTicket: '  + pixelsPerTicket);

      let i, sum = 0, j = 0, seat_num, pages = 0;

      this.drawTableHeaderTickets(textOffset, lineOffset, doc);
      this.drawTableCell(lineOffset, 10, doc);

      lineOffset = lineOffset + 10;
      textOffset = textOffset + 10;
      for (i = 0; i < invoice.tickets.length; i++) {
        if (textOffset + j * pixelsPerTicket > pageHeight) {
          doc.addPage();
          j = 0;
          pages++;
          lineOffset = 10;
          textOffset = 15;
          this.drawTableHeaderTickets(textOffset, lineOffset, doc);
          this.drawTableCell(lineOffset, 10, doc);
          lineOffset = lineOffset + 10;
          textOffset = textOffset + 10;
        }
        seat_num = invoice.tickets[i].seatId.toString();
        this.drawTableCell(lineOffset + j * pixelsPerTicket, pixelsPerTicket, doc);
        doc.text('' + invoice.tickets[i].ticketCode, 20, textOffset + j * pixelsPerTicket, null, null);
        // break eventName down in multiple lines in case it is too long
        // tslint:disable-next-line:max-line-length
        const eventName: string[] = doc.splitTextToSize(invoice.tickets[i].eventName, maxLineLength);
        const showId =  + ' / ' + invoice.tickets[i].showId;
        doc.text(eventName, 50,  textOffset + j * pixelsPerTicket, null, null);
        doc.text('' + seat_num, 125, textOffset + j * pixelsPerTicket, null, null);
        const price = invoice.tickets[i].price;
        doc.text('' + price.toFixed(2), 155, textOffset + j * pixelsPerTicket, null, null);
        sum += price;
        console.log('eventName-Length: ' + eventName.length);
        j++;
      }
      const vat = sum * 0.1;
      const netto = sum - vat;
      textOffset = textOffset + 20;
      lineOffset = textOffset + 1;
      doc.text('Total vor VAT                ' + netto.toFixed(2) + '€', 135, textOffset + j * pixelsPerTicket, null, null);
      textOffset = textOffset + 5;
      lineOffset = textOffset + 1;
      doc.text('VAT (10%)                    ' + vat.toFixed(2) + '€', 135, textOffset + j * pixelsPerTicket, null, null);
      doc.line(100, lineOffset + j * pixelsPerTicket, 200, lineOffset + j * pixelsPerTicket);
      doc.setFontType('bold');
      textOffset = textOffset + 5;
      lineOffset = textOffset + 1;
      if (invoice.invoice_type === 'Kaufrechnung') {
        doc.text('Total                              ' + sum.toFixed(2) + '€', 135, textOffset + j * pixelsPerTicket, null, null);
      } else {
        doc.text('Stornierung Betrag                ' + sum.toFixed(2) + '€', 120, textOffset + j * pixelsPerTicket, null, null);
      }
    } else {
      const maxLineLength = 65.0;
      const merchName: string[] = doc.splitTextToSize(this.merchandise.merchandiseProductName, maxLineLength);
      const numberOfLinesPerMerch = merchName.length;
      const pixelsPerMerch = numberOfLinesPerMerch * 5 + 2;

      this.drawTableHeaderMerch(textOffset, lineOffset, doc);
      this.drawTableCell(lineOffset, 10, doc);
      lineOffset = lineOffset + 10;
      textOffset = textOffset + 10;

      this.drawTableCell(lineOffset, pixelsPerMerch, doc);
      doc.text('' + invoice.merchandise_code, 20, textOffset, null, null);
      doc.text(merchName, 50,  textOffset, null, null);
      doc.text('' + this.merchandise.premiumPrice, 130, textOffset, null, null);
      const price = this.merchandise.price;
      doc.text('' + price.toFixed(2), 155, textOffset, null, null);
      const vat = price * 0.1;
      const netto = price - vat;
      if (invoice.payment_method === 'premium points') {
        doc.text('Mit ' + this.merchandise.premiumPrice + ' Punkten bezahlt', 135, 130, null, null);
      } else {
        doc.text('Total vor VAT                ' + netto.toFixed(2) + '€', 135, 125, null, null);
        doc.text('VAT (10%)                    ' + vat.toFixed(2) + '€', 135, 130, null, null);
        doc.line(110, 131, 200, 131);
        doc.setFontType('bold');
        doc.text('Total                              ' + price.toFixed(2) + '€', 135, 135, null, null);
      }
    }
  }

  private drawTableHeaderTickets(textOffset: number, lineOffset: number, doc: jsPDF) {
    doc.line(15, lineOffset, 180, lineOffset);
    doc.setFontType('bold');
    // tslint:disable-next-line:max-line-length
    doc.text('Ticket Code                          Event / Show id                                   Seat id                   Price(€)', 20, textOffset, null, null, 'left');
    doc.setFontType('normal');
  }

  private drawTableHeaderMerch(textOffset: number, lineOffset: number, doc: jsPDF) {
    doc.line(15, lineOffset, 180, lineOffset);
    doc.setFontType('bold');
    // tslint:disable-next-line:max-line-length
    doc.text('Merch Code                          Product name                                   Premium                   Price(€)', 20, textOffset, null, null, 'left');
    doc.setFontType('normal');
  }

  private drawTableCell(lineOffset: number, cellHeight: number, doc: jsPDF) {
    doc.line(15, lineOffset + cellHeight, 180, lineOffset + cellHeight);
    doc.line(15, lineOffset, 15, lineOffset + cellHeight);
    doc.line(45, lineOffset, 45, lineOffset + cellHeight);
    doc.line(115, lineOffset, 115, lineOffset + cellHeight);
    doc.line(145, lineOffset, 145, lineOffset + cellHeight);
    doc.line(180, lineOffset, 180, lineOffset + cellHeight);
  }

  public getShow() {

  }
}
