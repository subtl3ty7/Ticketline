import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../services/user.service';
import {Globals} from '../../../global/globals';

@Component({
  selector: 'app-admin-tabs',
  templateUrl: './admin-tabs.component.html',
  styleUrls: ['./admin-tabs.component.css']
})
export class AdminTabsComponent implements OnInit {

  constructor(public  router: Router,
              private route: ActivatedRoute,
              private userService: UserService,
              private globals: Globals) {
  }

  ngOnInit(): void {
    console.log('debug');
    console.log(this.route.toString());
    this.route.params.subscribe(param => {
      if (!param['tabId']) {
        console.log('debug');
        this.router.navigate(['/administration', 'users']);
      }
      this.globals.setChoosenTab = param['tabId'];
    });
  }

}
