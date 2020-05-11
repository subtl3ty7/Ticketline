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
  ngOnInit(): void {
    this.isMobileLayout = window.innerWidth <= 1000;
    window.onresize = () => this.isMobileLayout = window.innerWidth <= 1000;
  }

}
