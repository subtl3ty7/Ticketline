import { Component, OnInit } from '@angular/core';
import {EventService} from '../../../services/event.service';
import {Background} from '../../../utils/background';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchShared} from '../search-shared';
import {SimpleEvent} from '../../../dtos/simple-event';
import {Show} from '../../../dtos/show';
import {ShowService} from '../../../services/show.service';

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
              private showService: ShowService
              ) {
    background.defineBackground();
  }

  ngOnInit(): void {
    console.log('show search');
    this.previousPage = [];
    this.nextPage = [];
    this.name = sessionStorage.getItem('searchTerm');
    if (sessionStorage.getItem('isAdvancedSearchActive') === String(true)) {
      const type = sessionStorage.getItem('eventType');
      const category = sessionStorage.getItem('eventCategory');
      const duration = sessionStorage.getItem('eventDuration');
      const startPrice = sessionStorage.getItem('eventStartPrice');
      const showStartsAt = sessionStorage.getItem('eventShowStartsAt');
      const showEndsAt = sessionStorage.getItem('eventShowEndsAt');
      console.log('show name: ' + this.name + ', show type: ' + type + ' show category: ' + category + 'starts at: ' + showStartsAt + ', event ends at: ' + showEndsAt + ', event duration: ' + duration + ', event start price: ' + startPrice);
      this.showService.getDetailedShowsBy(name, type, category, showStartsAt, showEndsAt, duration, startPrice, 0).subscribe(
        (firstPageShows: Show[]) => {
          this.shows = firstPageShows.slice(0, this.pageSize);
          this.currentPage = firstPageShows.slice(0, this.pageSize);
          this.showService.getDetailedShowsBy(name, type, category, showStartsAt, showEndsAt, duration, startPrice, this.pageSize).subscribe(
            (secondPageShows: Show[]) => {
              this.nextPage = secondPageShows.slice(0, this.pageSize);
              secondPageShows.forEach(value => {
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
  }

  openPurchase(show) {
    if (show) {
      sessionStorage.setItem('show', JSON.stringify(show));
      this.router.navigate(['ticket-purchase']);
    }
  }

  private loadNextPage() {
    this.currentPageIndex += 1;
    const type = sessionStorage.getItem('eventType');
    const category = sessionStorage.getItem('eventCategory');
    const duration = sessionStorage.getItem('eventDuration');
    const startPrice = sessionStorage.getItem('eventStartPrice');
    const showStartsAt = sessionStorage.getItem('eventShowStartsAt');
    const showEndsAt = sessionStorage.getItem('eventShowEndsAt');
    console.log('show name: ' + this.name + ', show type: ' + type + ' show category: ' + category + 'starts at: ' + showStartsAt + ', event ends at: ' + showEndsAt + ', event duration: ' + duration + ', event start price: ' + startPrice);
    this.showService.getDetailedShowsBy(name, type, category, showStartsAt, showEndsAt, duration, startPrice, this.pageSize * (this.currentPageIndex + 1)).subscribe(
      (shows: Show[]) => {
        this.deleteFromEvents(this.previousPage);
        this.previousPage = this.currentPage;
        this.currentPage = this.nextPage;
        this.nextPage = shows;
        this.shows = this.shows.concat(shows);
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
    if (this.currentPageIndex === 0) {
      this.deleteFromEvents(this.nextPage);
      this.nextPage = this.currentPage;
      this.currentPage = this.previousPage;
      this.previousPage = [];
      this.printPageStatus();
    } else {
      const type = sessionStorage.getItem('eventType');
      const category = sessionStorage.getItem('eventCategory');
      const duration = sessionStorage.getItem('eventDuration');
      const startPrice = sessionStorage.getItem('eventStartPrice');
      const showStartsAt = sessionStorage.getItem('eventShowStartsAt');
      const showEndsAt = sessionStorage.getItem('eventShowEndsAt');
      console.log('show name: ' + this.name + ', show type: ' + type + ' show category: ' + category + 'starts at: ' + showStartsAt + ', event ends at: ' + showEndsAt + ', event duration: ' + duration + ', event start price: ' + startPrice);
      this.showService.getDetailedShowsBy(name, type, category, showStartsAt, showEndsAt, duration, startPrice, this.pageSize * (this.currentPageIndex - 1)).subscribe(
        (shows: Show[]) => {
          this.deleteFromEvents(this.nextPage);
          this.nextPage = this.currentPage;
          this.currentPage = this.previousPage;
          this.previousPage = shows;
          this.shows = shows.concat(this.shows);
          this.printPageStatus();
        },
        (error) => {
          this.error = error.error;
        }
      );
    }

    this.searchShared.scrollToTop();
  }

  private deleteFromEvents(events: SimpleEvent[]) {
    if (events !== undefined) {
      if (events.length !== 0) {
        events.forEach(value => {
          const index = this.shows.indexOf(value, 0);
          this.shows.splice(index, 1);
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
    console.log('shows');
    console.log(this.shows);
  }
}
