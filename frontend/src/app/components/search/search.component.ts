import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor(private authService: AuthService) { }
  public isMobileLayout = false;
  isCollapsed: boolean;
  @Input() searchEntity: string;
  @Input() searchTerm: string;

  ngOnInit(): void {
    this.isCollapsed = true;
    this.isMobileLayout = window.innerWidth <= 992;
    window.onresize = () => this.isMobileLayout = window.innerWidth <= 992;
    console.log('Entity: ' + this.searchEntity);
  }

}
