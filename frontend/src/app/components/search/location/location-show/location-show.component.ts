import { Component, OnInit } from '@angular/core';
import {SimpleEvent} from '../../../../dtos/simple-event';
import {ShowService} from '../../../../services/show.service';
import {Show} from '../../../../dtos/show';
import {EventService} from '../../../../services/event.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-location-show',
  templateUrl: './location-show.component.html',
  styleUrls: ['./location-show.component.css']
})
export class LocationShowComponent implements OnInit {

  public eventLocationId: number;
  public eventLocationCity: string;
  public eventLocationName: string;
  public eventLocationPLZ: string;
  public shows: Show[];
  public showsEvents: SimpleEvent[];

  constructor(private showService: ShowService, private eventService: EventService) { }

  ngOnInit(): void {
    this.eventLocationId = parseInt(sessionStorage.getItem('eventLocationId'), 10);
    this.eventLocationCity = sessionStorage.getItem('eventLocationCity');
    this.eventLocationName = sessionStorage.getItem('eventLocationName');
    this.eventLocationPLZ = sessionStorage.getItem('eventLocationPLZ');
    this.showService.getShowsByEventLocationId(this.eventLocationId).subscribe(
      (shows: Show[]) => {
        for (let i = 0; i < shows.length; i++) {
          this.getEventByEventCode(shows[i].eventCode, i);
        }
        this.shows = shows;
      },
      error => {
        // throw error
      }
    );
  }

  getEventByEventCode(eventCode: string, i: number): void {
    this.eventService.getSimpleEventByUserCode(eventCode).subscribe(
      (event: SimpleEvent) => {
        this.showsEvents[i] = event;
      }
    );
  }
}
