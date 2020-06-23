import {Component, DoCheck, OnInit} from '@angular/core';
import {Artist} from '../../../dtos/artist';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchShared} from '../search-shared';
import {EventLocation} from '../../../dtos/event-location';
import {Background} from '../../../utils/background';
import {SimpleEvent} from '../../../dtos/simple-event';
import {EventLocationService} from '../../../services/event-location.service';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit, DoCheck {

  private eventLocations: EventLocation[];
  private pageSize = 10;
  private currentPageIndex = 0;
  private previousPage;
  private currentPage;
  private nextPage;
  private advancedSearching: boolean;
  name: string = '';
  error;

  constructor(public authService: AuthService,
              private artistService: ArtistService,
              private activatedRoute: ActivatedRoute,
              private searchShared: SearchShared,
              private router: Router,
              private eventLocationService: EventLocationService,
              private background: Background) {
    background.defineBackground();
  }

  ngOnInit(): void {
    console.log('location search');
    this.currentPageIndex = 0;
    this.previousPage = [];
    this.nextPage = [];
    this.name = sessionStorage.getItem('searchTerm');
    this.advancedSearching = sessionStorage.getItem('isAdvancedSearchActive') === String(true);
    console.log('location name: ' + this.name);
    this.loadAllLocations();
  }

  ngDoCheck(): void {
    if (sessionStorage.getItem('searchTerm') !== this.name) {
      console.log(sessionStorage.getItem('searchTerm'));
      window.location.reload();
    }
  }

  private loadAllLocations() {
    if (this.advancedSearching) {
      this.loadAllLocationsWithAdvancedFilters();
    } else {
      this.loadAllLocationsbyName();
    }
  }

  searchShows(eventLocation: EventLocation) {
    console.log('searching for shows with event location id ' + eventLocation.id);
    sessionStorage.setItem('eventLocationId', eventLocation.id.toString());
    sessionStorage.setItem('eventLocationName', eventLocation.name.toString());
    sessionStorage.setItem('eventLocationPLZ', eventLocation.plz.toString());
    sessionStorage.setItem('eventLocationCity', eventLocation.city.toString());
    this.router.navigate(['/search/location-shows']);
  }

  private loadAllLocationsWithAdvancedFilters() {
    const street = sessionStorage.getItem('locationStreet');
    const city = sessionStorage.getItem('locationCity');
    const country = sessionStorage.getItem('locationCountry');
    const plz = sessionStorage.getItem('locationPLZ');
    console.log('location street: ' + street + '; location city: ' + city + '; location country: ' + country + '; location plz: ' + plz);
    this.eventLocationService.getLocationsAdvanced(this.name, street, city, country, plz, 0).subscribe(
      (firstPageLocations: EventLocation[]) => {
        this.eventLocations = firstPageLocations.slice(0, this.pageSize);
        this.currentPage = firstPageLocations.slice(0, this.pageSize);
        this.eventLocationService.getLocationsAdvanced(this.name, street, city, country, plz, this.pageSize).subscribe(
          (secondPageLocations: EventLocation[]) => {
            this.nextPage = secondPageLocations.slice(0, this.pageSize);
            secondPageLocations.forEach(value => {
              this.eventLocations.push(value);
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

  private loadAllLocationsbyName() {
    console.log('event name: ' + this.name);
    return this.eventLocationService.getLocationsByName(this.name, 0).subscribe(
      (firstPageLocations: EventLocation[]) => {
        this.eventLocations = firstPageLocations.slice(0, this.pageSize);
        this.currentPage = firstPageLocations.slice(0, this.pageSize);
        this.eventLocationService.getLocationsByName(this.name, this.pageSize).subscribe(
          (secondPageLocations: EventLocation[]) => {
            this.nextPage = secondPageLocations.slice(0, this.pageSize);
            secondPageLocations.forEach(value => {
              this.eventLocations.push(value);
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
      const street = sessionStorage.getItem('locationStreet');
      const city = sessionStorage.getItem('locationCity');
      const country = sessionStorage.getItem('locationCountry');
      const plz = sessionStorage.getItem('locationPLZ');
      console.log('location name: ' + this.name + ', city: ' + city + ' country: ' + country + 'plz: ' + plz);
      this.eventLocationService.getLocationsAdvanced(this.name, street, city, country, plz, this.pageSize * (this.currentPageIndex + 1)).subscribe(
        (eventLocations: EventLocation[]) => {
          this.deleteFromEventLocations(this.previousPage);
          this.previousPage = this.currentPage;
          this.currentPage = this.nextPage;
          this.nextPage = eventLocations;
          this.eventLocations = this.eventLocations.concat(eventLocations);
          this.printPageStatus();
        },
        (error) => {
          this.error = error.error;
        }
      );
    } else {
      this.eventLocationService.getLocationsByName(this.name, this.pageSize * (this.currentPageIndex + 1)).subscribe(
        (eventLocations: EventLocation[]) => {
          this.deleteFromEventLocations(this.previousPage);
          this.previousPage = this.currentPage;
          this.currentPage = this.nextPage;
          this.nextPage = eventLocations;
          this.eventLocations = this.eventLocations.concat(eventLocations);
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
      this.deleteFromEventLocations(this.nextPage);
      this.nextPage = this.currentPage;
      this.currentPage = this.previousPage;
      this.previousPage = [];
      this.printPageStatus();
    } else {
      if (this.advancedSearching) {
        const street = sessionStorage.getItem('locationStreet');
        const city = sessionStorage.getItem('locationCity');
        const country = sessionStorage.getItem('locationCountry');
        const plz = sessionStorage.getItem('locationPLZ');
        console.log('location name: ' + this.name + ', city: ' + city + ' country: ' + country + 'plz: ' + plz);
        this.eventLocationService.getLocationsAdvanced(this.name, street, city, country, plz, this.pageSize * (this.currentPageIndex - 1)).subscribe(
          (eventLocations: EventLocation[]) => {
            this.deleteFromEventLocations(this.nextPage);
            this.nextPage = this.currentPage;
            this.currentPage = this.previousPage;
            this.previousPage = eventLocations;
            this.eventLocations = eventLocations.concat(this.eventLocations);
            this.printPageStatus();
          },
          (error) => {
            this.error = error.error;
          }
        );
      } else {
        this.eventLocationService.getLocationsByName(this.name, this.pageSize * (this.currentPageIndex - 1)).subscribe(
          (eventLocations: EventLocation[]) => {
            this.deleteFromEventLocations(this.nextPage);
            this.nextPage = this.currentPage;
            this.currentPage = this.previousPage;
            this.previousPage = eventLocations;
            this.eventLocations = eventLocations.concat(this.eventLocations);
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

  private deleteFromEventLocations(eventLocations: EventLocation[]) {
    if (eventLocations !== undefined) {
      if (eventLocations.length !== 0) {
        eventLocations.forEach(value => {
          const index = this.eventLocations.indexOf(value, 0);
          this.eventLocations.splice(index, 1);
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
    console.log('event locations');
    console.log(this.eventLocations);
  }
}
