import { Component, OnInit } from '@angular/core';
import {EventService} from '../../../services/event.service';
import {Background} from '../../../utils/background';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchShared} from '../search-shared';
import {SimpleEvent} from '../../../dtos/simple-event';

@Component({
  selector: 'app-show',
  templateUrl: './show.component.html',
  styleUrls: ['./show.component.css']
})
export class ShowComponent implements OnInit {

  private shows: any[];
  private pageSize = 10;
  private currentPageIndex = 0;
  private previousPage;
  private currentPage;
  private nextPage;
  name: string = '';
  error;

  constructor(private background: Background,
              public authService: AuthService,
              private artistService: ArtistService,
              private activatedRoute: ActivatedRoute,
              private searchShared: SearchShared,
              private router: Router,
              private eventService: EventService
              ) {
    background.defineBackground();
  }

  ngOnInit(): void {
    console.log('show search');
    this.name = sessionStorage.getItem('searchTerm');
    if (sessionStorage.getItem('isAdvancedSearchActive') === String(true)) {
      const type = sessionStorage.getItem('eventType');
      const category = sessionStorage.getItem('eventCategory');
      const duration = sessionStorage.getItem('eventDuration');
      const startPrice = sessionStorage.getItem('eventStartPrice');
      const showStartsAt = sessionStorage.getItem('eventShowStartsAt');
      const showEndsAt = sessionStorage.getItem('eventShowEndsAt');
      console.log('show name: ' + this.name + ', show type: ' + type + ' show category: ' + category + 'starts at: ' + showStartsAt + ', event ends at: ' + showEndsAt + ', event duration: ' + duration + ', event start price: ' + startPrice);
      this.searchShared.getShowsBy(name, type, category, showStartsAt, showEndsAt, duration, startPrice);
    }
  }

  openPurchase(show) {
    if (show) {
      sessionStorage.setItem('show', JSON.stringify(show));
      this.router.navigate(['ticket-purchase']);
    }
  }

  /*private loadAllEvents() {
    this.loadAllEventsWithAdvancedFilters();
  }

  private loadAllEventsByName() {
    console.log('event name: ' + this.name);
    return this.eventService.getSimpleEventsByName(this.name, 0).subscribe(
      (firstPageEvents: SimpleEvent[]) => {
        this.shows = firstPageEvents.slice(0, this.pageSize);
        this.currentPage = firstPageEvents.slice(0, this.pageSize);
        this.eventService.getSimpleEventsByName(this.name, this.pageSize).subscribe(
          (secondPageEvents: SimpleEvent[]) => {
            this.nextPage = secondPageEvents.slice(0, this.pageSize);
            secondPageEvents.forEach(value => {
              this.shows.push(value);
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
    this.eventService.getSimpleEventsBy(this.name, type, category, startsAt, endsAt, duration, this.pageSize).subscribe(
      (firstPageEvents: SimpleEvent[]) => {
        this.shows = firstPageEvents.slice(0, this.pageSize);
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
          console.log('previousPage: ');
          console.log(this.previousPage);
          console.log('currentPage: ');
          console.log(this.currentPage);
          console.log('nextPage:');
          console.log(this.nextPage);
          console.log('events');
          console.log(this.events);
        },
        (error) => {
          this.error = error.error;
        }
      );
      this.eventService.getSimpleEventsByName(this.name, this.pageSize * (this.currentPageIndex + 1)).subscribe(
        (events: SimpleEvent[]) => {
          this.deleteFromEvents(this.previousPage);
          this.previousPage = this.currentPage;
          this.currentPage = this.nextPage;
          this.nextPage = events;
          this.events = this.events.concat(events);
          console.log('previousPage: ');
          console.log(this.previousPage);
          console.log('currentPage: ');
          console.log(this.currentPage);
          console.log('nextPage:');
          console.log(this.nextPage);
          console.log('events');
          console.log(this.events);
        },
        (error) => {
          this.error = error.error;
        }
      );
    this.scrollToTop();
  }

  private loadPreviousPage() {
    this.currentPageIndex -= 1;
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
          console.log('previousPage: ');
          console.log(this.previousPage);
          console.log('currentPage: ');
          console.log(this.currentPage);
          console.log('nextPage:');
          console.log(this.nextPage);
          console.log('events');
          console.log(this.events);
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
          console.log('previousPage: ');
          console.log(this.previousPage);
          console.log('currentPage: ');
          console.log(this.currentPage);
          console.log('nextPage:');
          console.log(this.nextPage);
          console.log('events');
          console.log(this.events);
        },
        (error) => {
          this.error = error.error;
        }
      );
    }
    this.scrollToTop();
  }

  private scrollToTop() {
    window.focus();
    window.scrollTo(0, 0);
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
  }*/
}
