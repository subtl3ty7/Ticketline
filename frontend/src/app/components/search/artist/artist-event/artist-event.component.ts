import { Component, OnInit } from '@angular/core';
import {Artist} from '../../../../dtos/artist';
import {EventService} from '../../../../services/event.service';
import {SimpleEvent} from '../../../../dtos/simple-event';
import {Background} from '../../../../utils/background';
import {SearchShared} from '../../search-shared';

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
  private advancedSearching;
  public artistId: string;
  public artistName: string;
  public events: SimpleEvent[];
  public error;

  constructor(private eventService: EventService, private searchShared: SearchShared, private background: Background) {
    background.defineBackground();
  }

  ngOnInit(): void {
    this.artistId = sessionStorage.getItem('artistId');
    this.artistName = sessionStorage.getItem('artistName');
    this.previousPage = [];
    this.nextPage = [];
    this.eventService.getSimpleEventsByArtistId(parseInt(this.artistId, 10), 0).subscribe(
      (firstPageEvents: SimpleEvent[]) => {
        this.events = firstPageEvents.slice(0, this.pageSize);
        this.currentPage = firstPageEvents.slice(0, this.pageSize);
        this.eventService.getSimpleEventsByArtistId(parseInt(this.artistId, 10), this.pageSize).subscribe(
          (secondPageEvents: SimpleEvent[]) => {
            this.nextPage = secondPageEvents.slice(0, this.pageSize);
            secondPageEvents.forEach(value => {
              this.events.push(value);
            });
          },
          (error) => {
            this.error = error.error;
          }
        );
      },
      (error) => {
        this.error = error.error;
      }
    );
  }

  private loadNextPage() {
    this.currentPageIndex += 1;
    this.eventService.getSimpleEventsByArtistId(parseInt(this.artistId, 10), this.pageSize * (this.currentPageIndex + 1)).subscribe(
      (events: SimpleEvent[]) => {
        this.deleteFromEvents(this.previousPage);
        this.previousPage = this.currentPage;
        this.currentPage = this.nextPage;
        this.nextPage = events;
        this.events = this.events.concat(events);
        this.printPageStatus();
      },
      (error) => {
        this.error = error.error;
      }
    );
    this.searchShared.scrollToTop();
  }

  private loadPreviousPage() {
    this.currentPageIndex -= 1;
    this.eventService.getSimpleEventsByArtistId(parseInt(this.artistId, 10), this.pageSize * (this.currentPageIndex - 1)).subscribe(
      (events: SimpleEvent[]) => {
        this.deleteFromEvents(this.nextPage);
        this.nextPage = this.currentPage;
        this.currentPage = this.previousPage;
        this.previousPage = events;
        this.events = events.concat(this.events);
        this.printPageStatus();
      },
      (error) => {
        this.error = error.error;
      }
    );
    this.searchShared.scrollToTop();
  }

  private deleteFromEvents(events: SimpleEvent[]) {
    if (events !== undefined) {
      if (events.length !== 0) {
        events.forEach(value => {
          const index = this.events.indexOf(value, 0);
          this.events.splice(index, 1);
        });
      }
    }
  }

  printPageStatus() {
    console.log('previousPage: ');
    console.log(this.previousPage);
    console.log('currentPage: ');
    console.log(this.currentPage);
    console.log('nextPage:');
    console.log(this.nextPage);
    console.log('events');
    console.log(this.events);
  }
}
