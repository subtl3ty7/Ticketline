import {AfterContentChecked, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {User} from '../../../dtos/user';
import {UserService} from '../../../services/user.service';
import {MatSort} from '@angular/material/sort';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-users-tab',
  templateUrl: './users-tab.component.html',
  styleUrls: ['./users-tab.component.css']
})
export class UsersTabComponent implements OnInit {
  public displayedColumns = [
    'userCode',
    'firstName',
    'lastName',
    'email',
    'password',
    'block'
  ];
  public dataSource: any;
  public showLoadingIndicator = true;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('scrollBlock') scrollBlock: ElementRef;
  error = false;
  errorMessage = '';
  private user: User;
  private users: Array<User>;
  private updatedUser: boolean;
  private selectedUser: User;
  constructor(private userService: UserService) {
  this.updatedUser = false;
  //this.selectedUser = new User(null, '', '', '', '', '', '', '', '', false, false, 0, '', false);
}
ngOnInit(): void {
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
      this.initTable();
      this.showLoadingIndicator = false;
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

  private initTable() {
    if (this.users) {
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.sort = this.sort;
    }
  }

  private getUserCode(user?: User): string {
    return user.userCode ? user.userCode.toLowerCase() : '';
  }


  searchForAllUsers($event: KeyboardEvent) {
  }

  getDateRangeState($event: any) {}

}
