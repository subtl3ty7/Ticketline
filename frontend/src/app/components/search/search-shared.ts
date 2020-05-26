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

  constructor(public authService: AuthService,
              private artistService: ArtistService,
              private eventService: EventService,
              private eventLocationService: EventLocationService, private background: Background) {
    this.background.defineBackground();
  }

  public getArtistsByFirstAndLastName(firstName: string, lastName: string) {
    console.log('loading all artists with first name containing "' + firstName + '" and last name containing "' + lastName + '"');
    this.artistService.getArtistsByFirstAndLastName(firstName, lastName).subscribe(
      (artists: Artist[]) => {
        this.entities = artists;
      },
      error => {
        this.entities = null;
      }
    );
  }

  public getLocationByName(locationTerm: string) {
    console.log('loading all locations with name containing "' + locationTerm + '"');
    this.eventLocationService.getLocationsByName(locationTerm).subscribe(
      (eventLocations: EventLocation[]) => {
        this.entities = eventLocations;
      },
      error => {
        this.entities = null;
      }
    );
  }

  getLocationsAdvanced(name: string, street: string, city: string, country: string, plz: string) {
    console.log('loading all locations with name containing "' + name + '", street containing "' + street + '", city containing "' + city + '", country containing "' + country + '" and plz containing "' + plz + '"');
    this.eventLocationService.getLocationsAdvanced(name, street, city, country, plz).subscribe(
      (eventLocations: EventLocation[]) => {
        this.entities = eventLocations;
      },
      error => {
        this.entities = null;
      }
    );
  }

  getEventsBy(name: string, startsAt: string, endsAt: string, startPrice: string, endPrice: string, type: string, category: string, prices: string) {
    this.eventService.getDetailedEventsBy(name, startsAt, endsAt, startPrice, endPrice, type, category, prices);
  }

  getEventsByName(name: string) {
    this.eventService.getDetailedEventsByName(name).subscribe(
      (events: DetailedEvent[]) => {
        this.entities = events;
      },
      error => {
        this.entities = null;
      }
    );
  }
}
