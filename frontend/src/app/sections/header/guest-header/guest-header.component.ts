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
    localStorage.setItem('searchTerm', searchTerm);
    localStorage.setItem('searchEntity', s.toString());
    switch (s) {
      case SearchEntity.ARTIST:
        const term = localStorage.getItem('searchTerm').split(' ', 2);
        const firstName = term[0];
        const lastName = term[1];
        console.log('first name: ' + firstName + ', last name: ' + lastName);
        this.searchShared.getArtistsByFirstAndLastName(firstName, lastName);
        break;
      case SearchEntity.EVENT:
        break;
      case SearchEntity.LOCATION:
        break;
      case SearchEntity.SHOW:
        break;
    }
    this.isSearchActive = false;
    this.router.navigate(['/search']);
  }
}
