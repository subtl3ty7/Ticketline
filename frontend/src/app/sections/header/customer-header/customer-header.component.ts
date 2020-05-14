import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-customer-header',
  templateUrl: './customer-header.component.html',
  styleUrls: ['./customer-header.component.css']
})
export class CustomerHeaderComponent implements OnInit {

  constructor(public authService: AuthService) { }
  public isMobileLayout = false;
  isCollapsed: boolean;

  ngOnInit(): void {
    this.isCollapsed = true;
    this.isMobileLayout = window.innerWidth <= 992;
    window.onresize = () => this.isMobileLayout = window.innerWidth <= 992;
  }

}
