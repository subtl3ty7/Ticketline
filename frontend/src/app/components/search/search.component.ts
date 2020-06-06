import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {SearchEntity, SearchShared} from './search-shared';
import {Router} from '@angular/router';
import {Background} from '../../utils/background';

@Component({
  selector: 'app-search',
  providers: [SearchShared],
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  constructor(private authService: AuthService,
              private searchShared: SearchShared,
              private router: Router,
              private background: Background) {
    this.background.defineBackground();
  }
  public isMobileLayout = false;
  isCollapsed: boolean;
  currentTerm: string;
  currentEntity: string;

  ngOnInit(): void {
    this.isCollapsed = true;
    this.isMobileLayout = window.innerWidth <= 992;
    window.onresize = () => this.isMobileLayout = window.innerWidth <= 992;
    this.currentTerm = sessionStorage.getItem('searchTerm');
    this.currentEntity = sessionStorage.getItem('searchEntity');
    if (this.currentTerm !== null && this.currentEntity !== null /*&& no error from backend detected*/) {
      this.searchShared.searchEntity = this.currentEntity;
      console.log('entity: ' + this.currentEntity + '; term: ' + this.currentTerm);
    } else {
      console.log('No search entered, redirecting to home page');
      this.router.navigate(['/home']);
    }
  }
}
