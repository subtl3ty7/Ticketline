import { Component, OnInit } from '@angular/core';
import {User} from '../../dtos/user';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {
  error = false;
  errorMessage = '';
  user: User;
  users: User[];
  updatedUser: boolean;
  selectedUser: User;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.updatedUser = false;
    this.selectedUser = new User(null, '', '', '', '', '', '', '', '', false, false, 0, '', false);
    this.loadAllUsers();
  }
  private loadUser(userCode: string) {
    this.userService.getUserByUserCode(userCode).subscribe(
      (user: User) => {
        this.user = user;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }
  private loadAllUsers() {
    this.userService.getAllUsers().subscribe(
      (user: User[]) => {
        this.users = user;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }
  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }
  private setPassword(userCode: string) { // to do
  }

}
