/* tslint:disable:max-line-length */
import {Component, DoCheck, OnInit} from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {ArtistService} from '../../../services/artist.service';
import {Artist} from '../../../dtos/artist';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchEntity, SearchShared} from '../search-shared';
import {string} from '@amcharts/amcharts4/core';
import {EventService} from '../../../services/event.service';
import {Background} from '../../../utils/background';
import {SimpleEvent} from '../../../dtos/simple-event';

@Component({
  selector: 'app-artist',
  templateUrl: './artist.component.html',
  styleUrls: ['./artist.component.css']
})
export class ArtistComponent implements OnInit, DoCheck {

  private pageSize = 10;
  private currentPageIndex = 0;
  private previousPage;
  private currentPage;
  private nextPage;
  private searchTerm;
  private searchTermArr: string[];
  firstName: string = '';
  lastName: string = '';
  artists: Artist[];
  error;

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
    this.previousPage = [];
    this.nextPage = [];
    this.searchTerm = sessionStorage.getItem('searchTerm');
    if (this.searchTerm.includes(' ')) {
      this.searchTermArr = this.searchTerm.split(' ', 2);
      this.firstName = this.searchTermArr[0];
      this.lastName = this.searchTermArr[1];
    } else {
      this.firstName = this.searchTerm;
      this.lastName = '';
    }
    console.log('first name: ' + this.firstName + ', last name: ' + this.lastName);
    console.log('searchTerm: ' + this.searchTerm);
    console.log('arr: ' + this.searchTermArr);
    this.artistService.getArtistsByFirstAndLastName(this.firstName, this.lastName, 0).subscribe(
      (firstPageArtists: Artist[]) => {
        this.artists = firstPageArtists.slice(0, this.pageSize);
        this.currentPage = firstPageArtists.slice(0, this.pageSize);
        this.artistService.getArtistsByFirstAndLastName(this.firstName, this.lastName, this.pageSize).subscribe(
          (secondPageArtists: Artist[]) => {
            this.nextPage = secondPageArtists.slice(0, this.pageSize);
            secondPageArtists.forEach(value => {
              this.artists.push(value);
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

  ngDoCheck(): void {
    console.log('searchTerm in sessionStorage: ' + sessionStorage.getItem('searchTerm'));
    console.log('searchTerm in component: ' + this.searchTerm);
    if (sessionStorage.getItem('searchTerm') !== this.searchTerm) {
      console.log(sessionStorage.getItem('searchTerm'));
      window.location.reload();
    }
  }

  searchEvents(artist: Artist) {
      console.log('searching for artists events with id ' + artist.id);
      sessionStorage.setItem('artistId', artist.id.toString());
      sessionStorage.setItem('artistName', artist.firstName + ' ' + artist.lastName);
      this.router.navigate(['/search/artist-events']);
  }

  private loadNextPage() {
    this.currentPageIndex += 1;
    this.artistService.getArtistsByFirstAndLastName(this.firstName, this.lastName, this.pageSize * (this.currentPageIndex + 1)).subscribe(
        (artists: Artist[]) => {
          this.deleteFromEvents(this.previousPage);
          this.previousPage = this.currentPage;
          this.currentPage = this.nextPage;
          this.nextPage = artists;
          this.artists = this.artists.concat(artists);
          this.printPageStatus();
        },
        (error) => {
          this.error = error.error;
        }
      );
    this.scrollToTop();
  }

  private loadPreviousPage() {
    this.currentPageIndex -= 1;
    if (this.currentPageIndex === 0) {
      this.deleteFromEvents(this.nextPage);
      this.nextPage = this.currentPage;
      this.currentPage = this.previousPage;
      this.previousPage = [];
      this.printPageStatus();
    } else {
      console.log('current page index: ' + this.currentPageIndex);
      this.artistService.getArtistsByFirstAndLastName(this.firstName, this.lastName, this.pageSize * (this.currentPageIndex - 1)).subscribe(
        (artists: Artist[]) => {
          this.deleteFromEvents(this.nextPage);
          this.nextPage = this.currentPage;
          this.currentPage = this.previousPage;
          this.previousPage = artists;
          this.artists = this.artists.concat(artists);
          this.printPageStatus();
        },
        (error) => {
          this.error = error.error;
        }
      );
    }
    this.scrollToTop();
    console.log('new page index: ' + this.currentPageIndex);
  }

  private scrollToTop() {
    window.focus();
    window.scrollTo(0, 0);
  }

  private deleteFromEvents(artists: Artist[]) {
    if (artists !== undefined) {
      if (artists.length !== 0) {
        artists.forEach(value => {
          const index = this.artists.indexOf(value, 0);
          this.artists.splice(index, 1);
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
    console.log('artists');
    console.log(this.artists);
  }
}
