import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {SearchEntity, SearchShared} from './search-shared';

@Component({
  selector: 'app-search',
  providers: [SearchShared],
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor(private authService: AuthService, private searchShared: SearchShared) { }
  public isMobileLayout = false;
  isCollapsed: boolean;
  currentTerm: string;
  currentEntity: string;

  ngOnInit(): void {
    this.currentTerm = localStorage.getItem('searchTerm');
    this.currentEntity = localStorage.getItem('searchEntity');
    this.searchShared.searchEntity = this.currentEntity;
    console.log('entity: ' + this.currentEntity + '; term: ' + this.currentTerm);
    this.isCollapsed = true;
    this.isMobileLayout = window.innerWidth <= 992;
    window.onresize = () => this.isMobileLayout = window.innerWidth <= 992;
  }

}
