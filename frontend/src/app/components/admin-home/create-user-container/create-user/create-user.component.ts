import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../../services/user.service';
import {UserDetailsWrapper} from '../../user-details-container/user-details/user-details-wrapper';
import {CreateUserWrapper} from './create-user-wrapper';
import {User} from '../../../../dtos/user';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  public wrapper = new CreateUserWrapper();
  constructor(private router: Router,
              private route: ActivatedRoute,
              private userService: UserService) { }

  ngOnInit(): void {
  }
  public usersNavigate() {
      this.router.navigate(['administration']);
  }

  cancelCreateUserMode() {
    this.usersNavigate();
  }
  saveUser() {
    if (this.wrapper.valid()) {
    this.userService.save(this.wrapper.model).subscribe(
      error => {
        this.router.navigate(['administration']);
      }
    );
  }
  }
}
