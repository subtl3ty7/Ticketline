import {Component, DoCheck, OnInit} from '@angular/core';
import {Artist} from '../../../dtos/artist';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchShared} from '../search-shared';
import {number} from '@amcharts/amcharts4/core';
import {Background} from '../../../utils/background';
import {SimpleEvent} from '../../../dtos/simple-event';
import {MatTableDataSource} from '@angular/material/table';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';
import {EventService} from '../../../services/event.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit, DoCheck {

  private events: any[];
  private pageSize = 10;
  private currentPageIndex = 0;
  private previousPage;
  private currentPage;
  private nextPage;
  private advancedSearching: boolean;
  name: string = '';
  artistId: string;
  error;

  constructor(public authService: AuthService,
              private artistService: ArtistService,
              private activatedRoute: ActivatedRoute,
              private eventService: EventService,
              private searchShared: SearchShared,
              private background: Background,
              private router: Router) {
    background.defineBackground();
  }

  ngOnInit(): void {
    console.log('event search');
    this.name = sessionStorage.getItem('searchTerm');
    this.currentPageIndex = 0;
    this.previousPage = [];
    this.nextPage = [];
    this.advancedSearching = sessionStorage.getItem('isAdvancedSearchActive') === String(true);
    this.loadAllEvents();
  }

  ngDoCheck(): void {
    if (sessionStorage.getItem('searchTerm') !== this.name) {
      console.log(sessionStorage.getItem('searchTerm'));
      window.location.reload();
    }
  }

  private loadAllEvents() {
    if (this.advancedSearching) {
      this.loadAllEventsWithAdvancedFilters();
    } else {
      this.loadAllEventsByName();
    }
  }

  private loadAllEventsByName() {
    console.log('event name: ' + this.name);
    return this.eventService.getSimpleEventsByName(this.name, 0).subscribe(
      (firstPageEvents: SimpleEvent[]) => {
        this.events = firstPageEvents.slice(0, this.pageSize);
        this.currentPage = firstPageEvents.slice(0, this.pageSize);
        this.eventService.getSimpleEventsByName(this.name, this.pageSize).subscribe(
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

  private loadAllEventsWithAdvancedFilters() {
    const type = sessionStorage.getItem('eventType');
    const category = sessionStorage.getItem('eventCategory');
    const startsAt = sessionStorage.getItem('eventStartsAt');
    const endsAt = sessionStorage.getItem('eventEndsAt');
    const duration = sessionStorage.getItem('eventDuration');
    console.log('event name: ' + this.name + ', event type: ' + type + ' event category: ' + category + 'starts at: ' + startsAt + ', event ends at: ' + endsAt + ', event duration: ' + duration);
    this.eventService.getSimpleEventsBy(this.name, type, category, startsAt, endsAt, duration, 0).subscribe(
      (firstPageEvents: SimpleEvent[]) => {
        this.events = firstPageEvents.slice(0, this.pageSize);
        this.currentPage = firstPageEvents.slice(0, this.pageSize);
        this.eventService.getSimpleEventsBy(this.name, type, category, startsAt, endsAt, duration, this.pageSize).subscribe(
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
    if (this.advancedSearching) {
      const type = sessionStorage.getItem('eventType');
      const category = sessionStorage.getItem('eventCategory');
      const startsAt = sessionStorage.getItem('eventStartsAt');
      const endsAt = sessionStorage.getItem('eventEndsAt');
      const duration = sessionStorage.getItem('eventDuration');
      console.log('event name: ' + this.name + ', event type: ' + type + ' event category: ' + category + 'starts at: ' + startsAt + ', event ends at: ' + endsAt + ', event duration: ' + duration);
      this.eventService.getSimpleEventsBy(this.name, type, category, startsAt, endsAt, duration, this.pageSize * (this.currentPageIndex + 1)).subscribe(
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
    } else {
      this.eventService.getSimpleEventsByName(this.name, this.pageSize * (this.currentPageIndex + 1)).subscribe(
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
    }
    this.searchShared.scrollToTop();
  }

  private loadPreviousPage() {
    this.currentPageIndex -= 1;
    if (this.currentPageIndex === 0) {
      this.deleteFromEvents(this.nextPage);
      this.nextPage = this.currentPage;
      this.currentPage = this.previousPage;
      this.previousPage = [];
      this.printPageStatus();
    } else {
      if (this.advancedSearching) {
        const type = sessionStorage.getItem('eventType');
        const category = sessionStorage.getItem('eventCategory');
        const startsAt = sessionStorage.getItem('eventStartsAt');
        const endsAt = sessionStorage.getItem('eventEndsAt');
        const duration = sessionStorage.getItem('eventDuration');
        console.log('event name: ' + this.name + ', event type: ' + type + ' event category: ' + category + 'starts at: ' + startsAt + ', event ends at: ' + endsAt + ', event duration: ' + duration);
        this.eventService.getSimpleEventsBy(this.name, type, category, startsAt, endsAt, duration, this.pageSize * (this.currentPageIndex - 1)).subscribe(
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
      } else {
        this.eventService.getSimpleEventsByName(this.name, this.pageSize * (this.currentPageIndex - 1)).subscribe(
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
      }
    }

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
