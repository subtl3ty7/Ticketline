/* tslint:disable:max-line-length */
import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {Artist} from '../../../dtos/artist';
import {ActivatedRoute} from '@angular/router';
import {SearchShared} from '../search-shared';
import {string} from '@amcharts/amcharts4/core';

@Component({
  selector: 'app-artist',
  templateUrl: './artist.component.html',
  styleUrls: ['./artist.component.css']
})
export class ArtistComponent implements OnInit {

  firstName: string = '';
  lastName: string = '';
  artists: Artist[];

  constructor(public authService: AuthService, private artistService: ArtistService, private activatedRoute: ActivatedRoute, private searchShared: SearchShared) {

  }

  ngOnInit(): void {
    console.log('artist search');
    const searchTerm = sessionStorage.getItem('searchTerm').split(' ', 2);
    this.firstName = searchTerm[0];
    this.lastName = searchTerm[1];
    document.body.style.backgroundImage = null;
    document.body.style.backgroundColor = '#DEDEDE';
    console.log('first name: ' + this.firstName + ', last name: ' + this.lastName);
    this.searchShared.getArtistsByFirstAndLastName(this.firstName, this.lastName);
  }
}
