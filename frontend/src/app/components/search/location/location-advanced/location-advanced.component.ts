import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../../services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ArtistService} from '../../../../services/artist.service';
import {SearchShared} from '../../search-shared';
import {EventService} from '../../../../services/event.service';
import {Background} from '../../../../utils/background';

@Component({
  selector: 'app-location-advanced',
  templateUrl: './location-advanced.component.html',
  styleUrls: ['./location-advanced.component.css']
})
export class LocationAdvancedComponent implements OnInit {

  advancedLocationSearchForm: FormGroup;
  submitted: boolean = false;
  name: string = '';
  street: string = '';
  city: string = '';
  country: string = '';
  plz: string = '';

  constructor(private formBuilder: FormBuilder,
              public authService: AuthService,
              public router: Router,
              private artistService: ArtistService,
              private activatedRoute: ActivatedRoute,
              private searchShared: SearchShared,
              private background: Background) {
    background.defineBackground();
    this.advancedLocationSearchForm = this.formBuilder.group({
      name: [''],
      street: [''],
      city: [''],
      country: [''],
      plz: ['']
    });
  }

  ngOnInit(): void {
    console.log('advanced artist search');
  }

  advancedLocationSearch() {
    this.submitted = true;
    if (this.advancedLocationSearchForm.valid) {
      this.name = this.advancedLocationSearchForm.controls.name.value === null ? '' : this.advancedLocationSearchForm.controls.name.value;
      this.street = this.advancedLocationSearchForm.controls.street.value  === null ? '' : this.advancedLocationSearchForm.controls.street.value;
      this.city = this.advancedLocationSearchForm.controls.city.value === null ? '' : this.advancedLocationSearchForm.controls.city.value;
      this.country = this.advancedLocationSearchForm.controls.country.value === null ? '' : this.advancedLocationSearchForm.controls.country.value;
      this.plz = this.advancedLocationSearchForm.controls.plz.value === null ? '' : this.advancedLocationSearchForm.controls.plz.value;
      console.log('advanced searching with name: ' + this.name + ', street: ' + this.street + ', city: ' + this.city + ', country: ' + this.country + ', plz: ' + this.plz);
      sessionStorage.setItem('isAdvancedSearchActive', String(true));
      sessionStorage.setItem('searchTerm', this.name);
      sessionStorage.setItem('searchEntity', 'Location');
      sessionStorage.setItem('locationStreet', this.street);
      sessionStorage.setItem('locationCity', this.city);
      sessionStorage.setItem('locationCountry', this.country);
      sessionStorage.setItem('locationPLZ', this.plz);
      this.router.navigate(['/search']);
    } else {
      console.log('Invalid input');
    }

  }

  resetForm() {
    this.advancedLocationSearchForm.controls.name.setValue('');
    this.advancedLocationSearchForm.controls.street.setValue('');
    this.advancedLocationSearchForm.controls.city.setValue('');
    this.advancedLocationSearchForm.controls.country.setValue('');
    this.advancedLocationSearchForm.controls.plz.setValue('');
  }

}
