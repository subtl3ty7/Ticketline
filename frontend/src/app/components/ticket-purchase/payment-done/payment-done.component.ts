import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {Show} from '../../../dtos/show';
import {DetailedEvent} from '../../../dtos/detailed-event';
import {DatePipe} from '@angular/common';
import {DetailedTicket} from '../../../dtos/detailed-ticket';

@Component({
  selector: 'app-payment-done',
  templateUrl: './payment-done.component.html',
  styleUrls: ['./payment-done.component.css'],
  providers: [DatePipe]
})
export class PaymentDoneComponent implements OnInit {
  @Input() show: Show;
  @Input() event: DetailedEvent;
  @Input() tickets: DetailedTicket[];

  constructor(private datePipe: DatePipe) { }

  ngOnInit(): void {
  }

  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'EE dd.MM.yyyy - hh:mm');
  }

  getSectionNameById(sectionId) {
    let name;
    this.show.eventLocation.sections.forEach((next) => {
      if (next.id === sectionId) {
        name = next.name;
      }
    });
    return name;
  }
}
