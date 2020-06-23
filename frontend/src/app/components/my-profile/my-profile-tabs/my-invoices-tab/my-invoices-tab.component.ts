import {Component, Input, OnInit} from '@angular/core';
import {Invoice} from '../../../../dtos/invoice';
import {UserService} from '../../../../services/user.service';
import {InvoiceService} from '../../../../services/invoice.service';
import {User} from '../../../../dtos/user';
import {ActivatedRoute} from '@angular/router';
import * as jsPDF from 'jspdf';
import {Merchandise} from '../../../../dtos/merchandise';
import {MerchandiseService} from '../../../../services/merchandise.service';

@Component({
  selector: 'app-my-invoices-tab',
  templateUrl: './my-invoices-tab.component.html',
  styleUrls: ['./my-invoices-tab.component.css']
})
export class MyInvoicesTabComponent implements OnInit {
  @Input() invoiceId: number;
  public invoices: Array<Invoice>;
  private merchandise: Merchandise;
  public currentUser: User = new User();
  error = false;
  errorMessage = '';
  details = false;
  public selectedInvoice: Invoice;

  constructor( private userService: UserService,
               private invoiceService: InvoiceService,
               private merchandiseService: MerchandiseService,
               private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadUser();
  }

  private loadUser() {
    this.userService.getCurrentUser().subscribe(
      (user: User) => {
        Object.assign(this.currentUser, user);
        this.loadInvoices();
      },
      (error) => {
        this.error = error;
      }
    );
  }

  public loadInvoices(): void {
    this.invoiceService.getInvoicesByUserCode(this.currentUser.userCode).subscribe(
      (invoices: Invoice[]) => {
        this.invoices = invoices;
        this.loadUrlInvoice();
      },
        (error) => {
        this.error = error.error;
        }
    );
  }

  private loadUrlInvoice() {
    if (this.invoiceId) {
      console.log('Invoice-Id: ' + this.invoiceId);
      for (const inv of this.invoices.entries()) {
        console.log(inv[1].id);
        if (inv[1].id === this.invoiceId) {
          console.log('test');
          this.showInvoiceDetails(inv[1]);
        }
      }
      console.log(this.selectedInvoice);
    }
  }

  public showInvoiceDetails(invoice: Invoice) {
    this.details = true;
    this.selectedInvoice = invoice;
    if (invoice.invoice_category.startsWith('MERCH')) {
      this.loadMerch(invoice.merchandise_code);
    }
  }

  public loadMerch(code: string): void {
    this.merchandiseService.getMerchandiseProductByProductCode(code).subscribe(
      (merch: Merchandise) => {
        this.merchandise = merch;
      },
      (error) => {
        this.error = error.error;
      }
    );
  }

}
