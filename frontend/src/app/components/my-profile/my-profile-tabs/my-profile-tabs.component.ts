import { Component, OnInit } from '@angular/core';
import {Globals} from '../../../global/globals';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-my-profile-tabs',
  templateUrl: './my-profile-tabs.component.html',
  styleUrls: ['./my-profile-tabs.component.css']
})
export class MyProfileTabsComponent implements OnInit {
  invoiceId: number;

  constructor(public  router: Router,
              private route: ActivatedRoute,
              private userService: UserService,
              private globals: Globals) { }

  ngOnInit(): void {
    this.route.params.subscribe((param) => {
      if (!param['tabId']) {
        this.router.navigate(['/my-info', 1]);
      }
      this.globals.setProfileChoosenTab = param['tabId'];
    });
    this.route.queryParams.subscribe(
      ( params) => {
        this.invoiceId = Number(params['invoiceId']);
        console.log('Invoice-Id: ' + this.invoiceId);
      }
    );
  }

}
