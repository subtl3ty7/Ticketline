import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-guest-header',
  templateUrl: './guest-header.component.html',
  styleUrls: ['./guest-header.component.css']
})
export class GuestHeaderComponent implements OnInit {

  constructor(public authService: AuthService, public router: Router, private activatedRoute: ActivatedRoute) { }
  public isMobileLayout = false;
  isCollapsed: boolean;
  isSearchActive: boolean;
  searchTerm: string = '';

  ngOnInit(): void {
    this.isSearchActive = false;
    this.isCollapsed = true;
    this.isMobileLayout = window.innerWidth <= 992;
    window.onresize = () => this.isMobileLayout = window.innerWidth <= 992;
  }

  refreshSearchTerm(event): void {
    this.searchTerm = event.target.value;
    console.log('current search term: ' + this.searchTerm);
  }

  private navigateArtistSearch(searchQuery: string[]) {
    searchQuery[0] = searchQuery[0] === undefined ? '' : searchQuery[0];
    searchQuery[1] = searchQuery[1] === undefined ? '' : searchQuery[1];
    const searchQueryString = 'firstName=' + searchQuery[0] + '&lastName=' + searchQuery[1];
    console.log('navigating to ' + 'search-artist?' + searchQueryString);
    this.router.navigateByUrl('search-artist?' + searchQueryString);
  }
  private navigateEventSearch(searchQuery: string) {
    this.router.navigateByUrl('search-event?');
  }
  private navigateLocationSearch(searchQuery: string) {
    this.router.navigateByUrl('search-location?');
  }

  searchArtist(searchTerm: string) {
    console.log('searching for artist with string: ' + searchTerm);
    const searchQuery = searchTerm.split(' ', 2);
    this.navigateArtistSearch(searchQuery);
  }

  searchLocation(searchTerm: string) {
    console.log('searching for location with string: ' + searchTerm);
    this.navigateLocationSearch(searchTerm);
  }

  searchEvent(searchTerm: string) {
    console.log('searching for event with string: ' + searchTerm);
    this.navigateEventSearch(searchTerm);
  }
}
