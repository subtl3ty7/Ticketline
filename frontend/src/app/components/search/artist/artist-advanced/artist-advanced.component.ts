import { Component, OnInit } from '@angular/core';
import {Artist} from '../../../../dtos/artist';
import {AuthService} from '../../../../services/auth.service';
import {ArtistService} from '../../../../services/artist.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchShared} from '../../search-shared';
import {FormControl, FormGroup} from '@angular/forms';
import {Background} from '../../../../utils/background';

@Component({
  selector: 'app-artist-advanced',
  templateUrl: './artist-advanced.component.html',
  styleUrls: ['./artist-advanced.component.css']
})
export class ArtistAdvancedComponent implements OnInit {

  advancedArtistSearchForm: FormGroup;
  firstName: string = '';
  lastName: string = '';

  constructor(public authService: AuthService, public router:
    Router, private artistService: ArtistService,
              private activatedRoute: ActivatedRoute,
              private searchShared: SearchShared,
              private background: Background) {
      background.defineBackground();
      this.advancedArtistSearchForm = new FormGroup({
      firstName: new FormControl(),
      lastName: new FormControl()
    });
  }

  ngOnInit(): void {
    console.log('advanced artist search');
  }

  advancedArtistSearch() {
    this.firstName = this.advancedArtistSearchForm.controls.firstName.value === null ? '' : this.advancedArtistSearchForm.controls.firstName.value;
    this.lastName = this.advancedArtistSearchForm.controls.lastName.value  === null ? '' : this.advancedArtistSearchForm.controls.lastName.value;
    console.log('advanced searching with first name: ' + this.firstName + ' and last name: ' + this.lastName);
    if (this.lastName === '') {
      sessionStorage.setItem('searchTerm', this.firstName);
    } else {
      sessionStorage.setItem('searchTerm', this.firstName + ' ' + this.lastName);
      sessionStorage.setItem('searchEntity', 'Artist');
    }
    this.router.navigate(['/search']);
  }

  resetForm() {
    this.advancedArtistSearchForm.controls.firstName.setValue(null);
    this.advancedArtistSearchForm.controls.lastName.setValue(null);
  }
}
