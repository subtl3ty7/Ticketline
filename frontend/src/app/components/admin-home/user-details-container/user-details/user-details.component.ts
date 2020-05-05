import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../../services/user.service';
import {State, UserDetailsWrapper} from './user-details-wrapper';
import {User} from '../../../../dtos/user';
import {ResetPasswordComponent} from './reset-password/reset-password.component';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {
  private uc: string;
  private sub: any;
  public wrapper = new UserDetailsWrapper();
  private state = State;
  public firstName: string;
  public lastName: string;
  @ViewChild(ResetPasswordComponent) resetPassword: ResetPasswordComponent;
  constructor(private router: Router,
              private route: ActivatedRoute,
              private userService: UserService) {}

  ngOnInit(): void {
    if(this.route.toString().includes('reset-password')){
      this.wrapper.state = this.state.PASSWORD;
    } else { this.wrapper.state = this.state.READY;}
    this.loadUser();
  }
  private loadUser() {
    this.sub = this.route.params.subscribe(params => {
      this.uc = params['uc'];
    });
    if (this.uc) {
      this.userService.getUserByUserCode(this.uc).subscribe(
        (user: User) => {
          Object.assign(this.wrapper.model, user);
          this.firstName = this.wrapper.model.firstName;
          this.lastName = this.wrapper.model.lastName;
          this.wrapper.model.birthday = new Date(this.wrapper.model.birthday);
        },
        error => {
          this.router.navigate(['administration']);
        }
      );
    }
  }
  public usersNavigate() {
    if (this.wrapper.state !== this.state.PROCESSING) {
      this.router.navigate(['administration']);
    }
  }
  public cancelResetPasswordMode() {
    this.wrapper.state = this.state.READY;
    this.usersNavigate();
  }
  public savePassword() {
    if (this.wrapper.model && this.resetPassword.password) {
      //this.userService.resetPassword(this.wrapper.model.userCode, this.wrapper.model.password, this.resetPassword.password);
      this.wrapper.state = this.state.READY;
      this.usersNavigate();
    }
  }
}
