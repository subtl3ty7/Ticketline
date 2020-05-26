import {Component, Input, OnInit} from '@angular/core';
import {SimpleTicket} from '../../../../../dtos/simple-ticket';

@Component({
  selector: 'app-my-invoice',
  templateUrl: './my-invoice.component.html',
  styleUrls: ['./my-invoice.component.css']
})
export class MyInvoiceComponent implements OnInit {
  @Input() ticket: SimpleTicket;

  constructor() { }

  ngOnInit(): void {
  }

}
