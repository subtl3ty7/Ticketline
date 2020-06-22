import { Component, OnInit } from '@angular/core';
import {SimpleEvent} from '../../../../dtos/simple-event';
import {ShowService} from '../../../../services/show.service';
import {Show} from '../../../../dtos/show';
import {EventService} from '../../../../services/event.service';
import {Observable} from 'rxjs';
import {SearchShared} from '../../search-shared';
import {Background} from '../../../../utils/background';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-location-show',
  templateUrl: './location-show.component.html',
  styleUrls: ['./location-show.component.css']
})
export class LocationShowComponent implements OnInit {

  private pageSize = 10;
  private currentPageIndex = 0;
  private previousPage;
  private currentPage;
  private nextPage;
  private advancedSearching;
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
              private searchShared: SearchShared,
              private background: Background) {
    background.defineBackground();
  }

  ngOnInit(): void {
    this.previousPage = [];
    this.nextPage = [];
    this.currentPageIndex = 0;
    this.eventLocationId = parseInt(sessionStorage.getItem('eventLocationId'), 10);
    this.eventLocationCity = sessionStorage.getItem('eventLocationCity');
    this.eventLocationName = sessionStorage.getItem('eventLocationName');
    this.eventLocationPLZ = sessionStorage.getItem('eventLocationPLZ');
    this.showService.getShowsByEventLocationId(this.eventLocationId, 0).subscribe(
      (firstPageShows: Show[]) => {
        console.log(firstPageShows);
        this.shows = firstPageShows.slice(0, this.pageSize);
        this.currentPage = firstPageShows.slice(0, this.pageSize);
        this.showService.getShowsByEventLocationId(this.eventLocationId, this.pageSize).subscribe(
          (secondPageShows: Show[]) => {
            this.nextPage = secondPageShows.slice(0, this.pageSize);
            secondPageShows.forEach(value => {
              this.shows.push(value);
            });
          }, (error) => {
            this.error = error.error;
          }
        );
      },
      error => {
        this.error = error;
      }
    );
    this.printPageStatus();
  }

  private loadNextPage() {
    this.currentPageIndex += 1;
    this.showService.getShowsByEventLocationId(this.eventLocationId, this.pageSize * (this.currentPageIndex + 1)).subscribe(
      (shows: Show[]) => {
        this.deleteFromShows(this.previousPage);
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
      this.deleteFromShows(this.nextPage);
      this.nextPage = this.currentPage;
      this.currentPage = this.previousPage;
      this.previousPage = [];
      this.printPageStatus();
    } else {
      this.showService.getShowsByEventLocationId(this.eventLocationId, this.pageSize * (this.currentPageIndex - 1)).subscribe(
        (shows: Show[]) => {
          this.deleteFromShows(this.nextPage);
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
      this.searchShared.scrollToTop();
    }
  }

  openPurchase(show: Show) {
    if (show) {
      sessionStorage.setItem('show', JSON.stringify(show));
      this.router.navigate(['ticket-purchase/' + show.id]);
    }
  }

  private deleteFromShows(shows: Show[]) {
    if (shows !== undefined) {
      if (shows.length !== 0) {
        shows.forEach(value => {
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
