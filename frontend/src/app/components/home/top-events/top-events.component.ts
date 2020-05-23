import { Component, OnInit } from '@angular/core';
import {EventService} from '../../../services/event.service';
import {SimpleEvent} from '../../../dtos/simple-event';
import {HttpErrorResponse} from '@angular/common/http';
import {CustomError} from '../../../dtos/customError';

@Component({
  selector: 'app-top-events',
  templateUrl: './top-events.component.html',
  styleUrls: ['./top-events.component.css']
})
export class TopEventsComponent implements OnInit {
  error;
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
        this.events = [];

        if (events.length <= 3) {
          this.events.push(events.slice(0, events.length));
        }
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
        this.error = error.error;
      }
    );
  }
}
