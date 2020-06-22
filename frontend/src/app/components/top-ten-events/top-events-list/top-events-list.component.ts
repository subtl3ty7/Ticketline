import {AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {SimpleEvent} from '../../../dtos/simple-event';
import {EventService} from '../../../services/event.service';

@Component({
  selector: 'app-top-ten-events-list',
  templateUrl: './top-events-list.component.html',
  styleUrls: ['./top-events-list.component.css']
})
export class TopEventsListComponent implements OnChanges {
  @Input() events: SimpleEvent[];
  topThreeIndices: number[];
  topTenIndices: number[];

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    this.setIndices();
  }

  private setIndices() {
    this.topThreeIndices = [];
    for (let i = 0; i < 3 && i < this.events.length; i++) {
      this.topThreeIndices.push(i);
    }
    this.topTenIndices = [];
    for (let i = 3; i < 10 && i < this.events.length; i++) {
      this.topTenIndices.push(i);
    }
  }
}
