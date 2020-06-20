/* tslint:disable:max-line-length */
import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {Artist} from '../../../dtos/artist';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchEntity, SearchShared} from '../search-shared';
import {string} from '@amcharts/amcharts4/core';
import {EventService} from '../../../services/event.service';
import {Background} from '../../../utils/background';

@Component({
  selector: 'app-artist',
  templateUrl: './artist.component.html',
  styleUrls: ['./artist.component.css']
})
export class ArtistComponent implements OnInit {

  private pageSize = 10;
  private currentPageIndex = 0;
  private previousPage;
  private currentPage;
  private nextPage;
  firstName: string = '';
  lastName: string = '';
  artists: Artist[];

  constructor(public authService: AuthService,
              private artistService: ArtistService,
              private activatedRoute: ActivatedRoute,
              private searchShared: SearchShared,
              private router: Router,
              private background: Background) {
    background.defineBackground();
  }

  ngOnInit(): void {
    console.log('artist search');
    const searchTerm = sessionStorage.getItem('searchTerm').split(' ', 2);
    this.firstName = searchTerm[0];
    this.lastName = searchTerm[1];
    console.log('first name: ' + this.firstName + ', last name: ' + this.lastName);
    this.searchShared.getArtistsByFirstAndLastName(this.firstName, this.lastName);
  }

  searchEvents(artist: Artist) {
      console.log('searching for artists events with id ' + artist.id);
      sessionStorage.setItem('artistId', artist.id.toString());
      sessionStorage.setItem('artistName', artist.firstName + ' ' + artist.lastName);
      this.router.navigate(['/search/artist-events']);
  }
}
