import { Component, OnInit } from '@angular/core';
import {Invoice} from '../../../../dtos/invoice';
import {UserService} from '../../../../services/user.service';
import {InvoiceService} from '../../../../services/invoice.service';
import {User} from '../../../../dtos/user';

@Component({
  selector: 'app-my-invoices-tab',
  templateUrl: './my-invoices-tab.component.html',
  styleUrls: ['./my-invoices-tab.component.css']
})
export class MyInvoicesTabComponent implements OnInit {
  public invoices: Array<Invoice>;
  public currentUser: User = new User();
  error = false;
  errorMessage = '';

  constructor( private userService: UserService,
               private invoiceService: InvoiceService) { }

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
        this.error = error.error;
      }
    );
  }
  public loadInvoices(): void {
    this.invoiceService.getInvoicesByUserCode(this.currentUser.userCode).subscribe(
      (invoices: Invoice[]) => {
        this.invoices = invoices;
      },
        (error) => {
        this.error = error.error;
        }
    );
  }

}
