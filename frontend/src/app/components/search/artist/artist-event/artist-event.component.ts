import { Component, OnInit } from '@angular/core';
import {Artist} from '../../../../dtos/artist';
import {EventService} from '../../../../services/event.service';
import {SimpleEvent} from '../../../../dtos/simple-event';
import {Background} from '../../../../utils/background';

@Component({
  selector: 'app-artist-event',
  templateUrl: './artist-event.component.html',
  styleUrls: ['./artist-event.component.css']
})
export class ArtistEventComponent implements OnInit {

  private pageSize = 10;
  private currentPageIndex = 0;
  private previousPage;
  private currentPage;
  private nextPage;
  public artistId: string;
  public artistName: string;
  public events: SimpleEvent[];
  public error;

  constructor(private eventService: EventService, private background: Background) {
    background.defineBackground();
  }

  ngOnInit(): void {
    this.artistId = sessionStorage.getItem('artistId');
    this.artistName = sessionStorage.getItem('artistName');
    this.eventService.getSimpleEventsByArtistId(parseInt(this.artistId, 10)).subscribe(
        (events: SimpleEvent[]) => {
          this.events = events;
        },
        error => {
          this.error = error;
        }
      );
  }

}
