import { Component, OnInit } from '@angular/core';
import {Artist} from '../../../dtos/artist';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchShared} from '../search-shared';
import {EventLocation} from '../../../dtos/event-location';
import {Background} from '../../../utils/background';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit {

  private pageSize = 10;
  private currentPageIndex = 0;
  private previousPage;
  private currentPage;
  private nextPage;
  name: string = '';

  constructor(public authService: AuthService,
              private artistService: ArtistService,
              private activatedRoute: ActivatedRoute,
              private searchShared: SearchShared,
              private router: Router,
              private background: Background) {
    background.defineBackground();
  }

  ngOnInit(): void {
    console.log('location search');
    this.name = sessionStorage.getItem('searchTerm');
    console.log('location name: ' + this.name);

    if (sessionStorage.getItem('isAdvancedSearchActive') === String(true)) {
      const street = sessionStorage.getItem('locationStreet');
      const city = sessionStorage.getItem('locationCity');
      const country = sessionStorage.getItem('locationCountry');
      const plz = sessionStorage.getItem('locationPLZ');
      console.log('location street: ' + street + '; location city: ' + city + '; location country: ' + country + '; location plz: ' + plz);
      this.searchShared.getLocationsAdvanced(this.name, street, city, country, plz);
    } else if (sessionStorage.getItem('isAdvancedSearchActive') === String(false)) {
      this.searchShared.getLocationByName(this.name);
    } else {
      // throw error
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
}
