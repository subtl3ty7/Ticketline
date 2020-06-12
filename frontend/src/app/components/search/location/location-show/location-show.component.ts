import { Component, OnInit } from '@angular/core';
import {SimpleEvent} from '../../../../dtos/simple-event';
import {ShowService} from '../../../../services/show.service';
import {Show} from '../../../../dtos/show';
import {EventService} from '../../../../services/event.service';
import {Observable} from 'rxjs';
import {SearchShared} from '../../search-shared';
import {Background} from '../../../../utils/background';
import {ActivatedRoute, Router} from '@angular/router';

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
  public error;

  constructor(private showService: ShowService,
              private router: Router,
              private route: ActivatedRoute,
              private eventService: EventService,
              private background: Background) {
    background.defineBackground();
  }

  ngOnInit(): void {
    this.eventLocationId = parseInt(sessionStorage.getItem('eventLocationId'), 10);
    this.eventLocationCity = sessionStorage.getItem('eventLocationCity');
    this.eventLocationName = sessionStorage.getItem('eventLocationName');
    this.eventLocationPLZ = sessionStorage.getItem('eventLocationPLZ');
    this.showService.getShowsByEventLocationId(this.eventLocationId).subscribe(
      (shows: Show[]) => {
        this.shows = shows;
        console.log(this.shows);
      },
      error => {
        this.error = error;
      }
    );
  }

  openPurchase(show: Show) {
    if (show) {
      sessionStorage.setItem('show', JSON.stringify(show));
      this.router.navigate(['ticket-purchase/' + show.id]);
    }
  }
}
