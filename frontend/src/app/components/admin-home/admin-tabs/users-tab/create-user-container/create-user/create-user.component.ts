import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../../../../services/user.service';
import {CreateUserWrapper} from './create-user-wrapper';
import * as bcrypt from 'bcryptjs';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  public wrapper = new CreateUserWrapper();
  error;
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
    if (this.wrapper.valid() && this.wrapper.model.password.length >= 8) {
      const salt = bcrypt.genSaltSync(10);
      const pwd = bcrypt.hashSync(this.wrapper.model.password, salt);
      this.wrapper.model.password = pwd;
    this.userService.save(this.wrapper.model).subscribe(
      (next) => {
        this.router.navigate(['administration']);
      },
      (error) => {
        this.error = error;
    }
    );
  }
  }
}
