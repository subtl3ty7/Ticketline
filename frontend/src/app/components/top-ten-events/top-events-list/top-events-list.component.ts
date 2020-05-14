import { Component, OnInit } from '@angular/core';
import {SimpleEvent} from '../../../dtos/simple-event';
import {EventService} from '../../../services/event.service';

@Component({
  selector: 'app-top-ten-events-list',
  templateUrl: './top-events-list.component.html',
  styleUrls: ['./top-events-list.component.css']
})
export class TopEventsListComponent implements OnInit {
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
      },
      error => {
        this.events = null;
      }
    );
  }
}
