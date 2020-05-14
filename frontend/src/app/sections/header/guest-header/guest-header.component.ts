import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import { NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-guest-header',
  templateUrl: './guest-header.component.html',
  styleUrls: ['./guest-header.component.css']
})
export class GuestHeaderComponent implements OnInit {

  constructor(public authService: AuthService) { }
  public isMobileLayout = false;
  isCollapsed: boolean;

  ngOnInit(): void {
    this.isCollapsed = true;
    this.isMobileLayout = window.innerWidth <= 992;
    window.onresize = () => this.isMobileLayout = window.innerWidth <= 992;
  }

}
