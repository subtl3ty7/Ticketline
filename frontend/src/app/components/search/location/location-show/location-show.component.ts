import { Component, OnInit } from '@angular/core';
import {SimpleEvent} from '../../../../dtos/simple-event';
import {ShowService} from '../../../../services/show.service';
import {Show} from '../../../../dtos/show';

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

  constructor(private showService: ShowService) { }

  ngOnInit(): void {
    this.eventLocationId = parseInt(sessionStorage.getItem('eventLocationId'), 10);
    this.eventLocationCity = sessionStorage.getItem('eventLocationCity');
    this.eventLocationName = sessionStorage.getItem('eventLocationName');
    this.eventLocationPLZ = sessionStorage.getItem('eventLocationPLZ');
    this.showService.getShowsByEventLocationId(this.eventLocationId).subscribe(
      (shows: Show[]) => {
        this.shows = shows;
      },
      error => {
        // throw error
      }
    );
  }

}
