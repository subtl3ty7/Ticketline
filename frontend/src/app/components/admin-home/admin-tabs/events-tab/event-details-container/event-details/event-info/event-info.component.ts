import {Component, Input, OnInit} from '@angular/core';
import {DetailedEvent} from '../../../../../../../dtos/detailed-event';
import {ShowsTableComponent} from './shows-table/shows-table.component';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-event-info',
  templateUrl: './event-info.component.html',
  styleUrls: ['./event-info.component.css']
})
export class EventInfoComponent implements OnInit {
  @Input() event: DetailedEvent;
  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
    console.log(this.event.description);
  }

  priceToString() {
    const prices = new Array<string>(this.event.prices.length);
    let i = 0;
    this.event.prices.forEach(function (value) {
      if (value) {
        prices[i] = ('€' + value);
        i = i + 1;
      }
    });
    return prices;
  }

  openShowDialog() {
    this.dialog.open(ShowsTableComponent,
      {width: '100%', data: this.event.shows});
  }
}
