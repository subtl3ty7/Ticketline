import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchShared, SearchEntity} from '../../../components/search/search-shared';

@Component({
  selector: 'app-customer-header',
  templateUrl: './customer-header.component.html',
  styleUrls: ['./customer-header.component.css']
})
export class CustomerHeaderComponent implements OnInit {

  constructor(public authService: AuthService, public router: Router, private activatedRoute: ActivatedRoute, private searchShared: SearchShared) { }
  public isMobileLayout = false;
  isCollapsed: boolean;
  isSearchActive: boolean;
  searchTerm: string;
  searchedEntity = SearchEntity;

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
  }


  searchEntity(searchTerm: string) {
    console.log('navigating to search with searchTerm: ' + searchTerm);
    this.searchShared.searchTerm = searchTerm;
    this.router.navigate(['search']);
  }

}
