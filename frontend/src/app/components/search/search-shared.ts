import {Injectable} from '@angular/core';
import {Artist} from '../../dtos/artist';
import {AuthService} from '../../services/auth.service';
import {ArtistService} from '../../services/artist.service';
import {ActivatedRoute, Router} from '@angular/router';
import {EventLocationService} from '../../services/event-location.service';
import {EventLocation} from '../../dtos/event-location';
import {EventService} from '../../services/event.service';
import {SimpleEvent} from '../../dtos/simple-event';
import {DetailedEvent} from '../../dtos/detailed-event';
import {Background} from '../../utils/background';
import {ShowService} from '../../services/show.service';
import {Show} from '../../dtos/show';

export enum SearchEntity {
  ARTIST = 'Artist',
  EVENT = 'Event',
  LOCATION = 'Location',
  SHOW = 'Show'
}

@Injectable({
  providedIn: 'root',
})
export class SearchShared {
  public searchTerm: string = '';
  public searchEntity: string;
  public entities: object[];
  public error;

  constructor(public authService: AuthService,
              private artistService: ArtistService,
              private eventService: EventService,
              private eventLocationService: EventLocationService,
              private showService: ShowService,
              private background: Background) {
    this.background.defineBackground();
  }

  public getArtistsByFirstAndLastName(firstName: string, lastName: string) {
    console.log('loading all artists with first name containing "' + firstName + '" and last name containing "' + lastName + '"');
    this.artistService.getArtistsByFirstAndLastName(firstName, lastName, 0).subscribe(
      (artists: Artist[]) => {
        this.entities = artists;
        this.error = null;
      },
      error => {
        this.entities = null;
        this.error = error;
      }
    );
  }

  public getLocationByName(locationTerm: string) {
    console.log('loading all locations with name containing "' + locationTerm + '"');
    this.eventLocationService.getLocationsByName(locationTerm, 0).subscribe(
      (eventLocations: EventLocation[]) => {
        this.entities = eventLocations;
        this.error = null;
      },
      error => {
        this.entities = null;
        this.error = error;
      }
    );
  }

  getLocationsAdvanced(name: string, street: string, city: string, country: string, plz: string) {
    console.log('loading all locations with name containing "' + name + '", street containing "' + street + '", city containing "' + city + '", country containing "' + country + '" and plz containing "' + plz + '"');
    this.eventLocationService.getLocationsAdvanced(name, street, city, country, plz, 0).subscribe(
      (eventLocations: EventLocation[]) => {
        this.entities = eventLocations;
        this.error = null;
      },
      error => {
        this.entities = null;
        this.error = error;
      }
    );
  }

  /*getEventsBy(name: string, type: string, category: string, startsAt: string, endsAt: string, duration: string) {
    this.eventService.getSimpleEventsBy(name, type, category, startsAt, endsAt, duration).subscribe(
      (events: SimpleEvent[]) => {
        this.entities = events;
        this.error = null;
      }, error => {
        this.entities = null;
        this.error = error;
      }
    );
  }

  getEventsByName(name: string) {
    this.eventService.getDetailedEventsByName(name).subscribe(
      (events: DetailedEvent[]) => {
        this.entities = events;
        this.error = null;
      },
      error => {
        this.entities = null;
        this.error = error;
      }
    );
  }

  getShowsBy(name: never, type: string, category: string, showStartsAt: string, showEndsAt: string, duration: string, startPrice: string) {
    return this.showService.getDetailedShowsBy(name, type, category, showStartsAt, showEndsAt, duration, startPrice).subscribe(
      (shows: Show[]) => {
        this.entities = shows;
        this.error = null;
      }, error => {
        this.entities = null;
        this.error = error;
      }
    );
  }*/

  public scrollToTop() {
    window.focus();
    window.scrollTo(0, 0);
  }
}
