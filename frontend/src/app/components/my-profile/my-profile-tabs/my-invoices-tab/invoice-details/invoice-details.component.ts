import {Component, Input, OnInit} from '@angular/core';
import {Invoice} from '../../../../../dtos/invoice';

@Component({
  selector: 'app-invoice-details',
  templateUrl: './invoice-details.component.html',
  styleUrls: ['./invoice-details.component.css']
})
export class InvoiceDetailsComponent implements OnInit {
  @Input() invoice: Invoice;
  constructor() { }

  ngOnInit(): void {
  }

}
