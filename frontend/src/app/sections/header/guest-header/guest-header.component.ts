import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchEntity, SearchShared} from '../../../components/search/search-shared';

@Component({
  selector: 'app-guest-header',
  templateUrl: './guest-header.component.html',
  styleUrls: ['./guest-header.component.css']
})
export class GuestHeaderComponent implements OnInit {

  constructor(public authService: AuthService, public router: Router, private activatedRoute: ActivatedRoute, private searchShared: SearchShared) { }
  public isMobileLayout = false;
  isCollapsed: boolean;
  isSearchActive: boolean;
  searchTerm: string = '';
  searchedEntity = SearchEntity;

  ngOnInit(): void {
    this.isSearchActive = false;
    this.isCollapsed = true;
    this.isMobileLayout = window.innerWidth <= 992;
    window.onresize = () => this.isMobileLayout = window.innerWidth <= 992;
  }

  refreshSearchTerm(event): void {
    this.searchTerm = event.target.value;
    this.isSearchActive = this.searchTerm.length > 0;
    console.log('current search term: ' + this.searchTerm);
  }

  searchEntity(searchTerm: string, s: SearchEntity) {
    console.log('navigating to search with searchTerm: ' + searchTerm + ' and searchEntity: ' + s);
    sessionStorage.setItem('searchTerm', searchTerm);
    sessionStorage.setItem('searchEntity', s.toString());
    sessionStorage.setItem('isAdvancedSearchActive', String(false));
    switch (s) {
      case SearchEntity.ARTIST:
        this.searchShared.searchEntity = 'Artist';
        const term = sessionStorage.getItem('searchTerm').split(' ', 2);
        const firstName = term[0];
        const lastName = term[1];
        console.log('first name: ' + firstName + ', last name: ' + lastName);
        this.searchShared.getArtistsByFirstAndLastName(firstName, lastName);
        break;
      case SearchEntity.EVENT:
        this.searchShared.searchEntity = 'Event';
        const eventTerm = sessionStorage.getItem('searchTerm');
        console.log('event name: ' + eventTerm);
        break;
      case SearchEntity.LOCATION:
        this.searchShared.searchEntity = 'Location';
        break;
      case SearchEntity.SHOW:
        this.searchShared.searchEntity = 'Show';
        break;
    }
    this.isSearchActive = false;
    this.router.navigate(['/search']);
  }
}
