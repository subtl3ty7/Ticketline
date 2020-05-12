import { Component, OnInit } from '@angular/core';
import {EventService} from '../../../services/event.service';
import {SimpleEvent} from '../../../dtos/simple-event';

@Component({
  selector: 'app-top-events',
  templateUrl: './top-events.component.html',
  styleUrls: ['./top-events.component.css']
})
export class TopEventsComponent implements OnInit {
  events: SimpleEvent[];

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
        this.events = events;
        for (const e of this.events) {
          console.log(e.name);
        }
      },
      error => {
        this.events = null;
      }
    );
  }

}
