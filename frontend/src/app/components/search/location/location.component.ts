import { Component, OnInit } from '@angular/core';
import {Artist} from '../../../dtos/artist';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {ActivatedRoute} from '@angular/router';
import {SearchShared} from '../search-shared';
import {EventLocation} from '../../../dtos/event-location';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit {

  name: string = '';

  constructor(public authService: AuthService, private artistService: ArtistService, private activatedRoute: ActivatedRoute, private searchShared: SearchShared) {

  }

  ngOnInit(): void {
    console.log('location search');
    document.body.style.backgroundImage = null;
    document.body.style.backgroundColor = '#DEDEDE';
    this.name = sessionStorage.getItem('searchTerm');
    console.log('location name: ' + this.name);

    if (sessionStorage.getItem('isAdvancedSearchActive') === String(true)) {
      const street = sessionStorage.getItem('locationStreet');
      const city = sessionStorage.getItem('locationCity');
      const country = sessionStorage.getItem('locationCountry');
      const plz = sessionStorage.getItem('locationPLZ');
      console.log('location street: ' + street + '; location city: ' + city + '; location country: ' + country + '; location plz: ' + plz);
      this.searchShared.getLocationsAdvanced(name, street, city, country, plz);
    } else if (sessionStorage.getItem('isAdvancedSearchActive') === String(false)) {
      this.searchShared.getLocationByName(this.name);
    } else {
      // throw error
    }


  }

}
