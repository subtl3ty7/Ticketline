import {Component, Input, OnInit} from '@angular/core';

import {MerchandiseService} from '../../services/merchandise.service';
import {UserService} from '../../services/user.service';

import {Merchandise} from '../../dtos/merchandise';
import {User} from '../../dtos/user';

import {ActivatedRoute} from '@angular/router';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-merchandise',
  templateUrl: './merchandise.component.html',
  styleUrls: ['./merchandise.component.css']
})
export class MerchandiseComponent implements OnInit {

  error;
  merchandise: Merchandise[];
  @Input() user: User;

  constructor(private merchandiseService: MerchandiseService,
              private userService: UserService,
              private route: ActivatedRoute,
              private authService: AuthService) { }

  ngOnInit(): void {
    this.getAllMerchandiseProducts();

    if (this.authService.isLoggedIn()) {
      this.loadUserByUserCode();
    }

  }

  public getAllMerchandiseProducts(): void {
    this.merchandiseService.getAllMerchandiseProducts().subscribe(
      (merchandise: Merchandise[]) => {
        this.merchandise = merchandise;
      },
      (error1) => {
        this.error = error1;
      }
    );
  }

  public loadUserByUserCode(): void {
    this.userService.getCurrentUser().subscribe(
      (user: User) => {
        this.user = user;
      },
      (error) => {
        this.error = error;
      }
    );
  }


}
