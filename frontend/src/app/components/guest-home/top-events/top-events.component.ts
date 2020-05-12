import { Component, OnInit } from '@angular/core';
import {EventService} from '../../../services/event.service';
import {SimpleEvent} from '../../../dtos/simple-event';

@Component({
  selector: 'app-top-events',
  templateUrl: './top-events.component.html',
  styleUrls: ['./top-events.component.css']
})
export class TopEventsComponent implements OnInit {
  events: SimpleEvent[][];

  constructor(private eventService: EventService) { }

  ngOnInit(): void {
    this.getTop10Events();
  }

  /**
   * Tries to load the top 10 events from database
   */
  public getTop10Events() {
    this.eventService.getTop10Events().subscribe(
      (events: SimpleEvent[]) => {
        let events2 = [];
        events2 = events.slice(0, 3);
        console.log(events2);
        this.events = [];
        if (events.length >= 3) {
          this.events.push(events.slice(0, 3));
        }
        if (events.length >= 6) {
          this.events.push(events.slice(3, 6));
        }
        if (events.length >= 9) {
          this.events.push(events.slice(6, 9));
        }
      },
      error => {
        this.events = null;
      }
    );
  }

}
