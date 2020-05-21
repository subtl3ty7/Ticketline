import {AfterContentChecked, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {User} from '../../../../dtos/user';
import {UserService} from '../../../../services/user.service';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {ActivatedRoute, Router} from '@angular/router';
import {MatPaginator} from '@angular/material/paginator';

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
    'block',
    'password'
  ];
  public dataSource: any;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('scrollBlock') scrollBlock: ElementRef;

  error = false;
  errorMessage = '';
  private user: User;
  private users: Array<User>;
  private searchUsers: Array<User>;
  private searchParam = '';
  private showBlocked = false;
  private searchIn;

  constructor(private userService: UserService,
              public  router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.loadAllUsers();
  }

  private loadAllUsers() {
    this.userService.getAllUsers().subscribe(
      (user: User[]) => {
        this.users = user;
        this.initTable();
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  private initTable() {
    if (this.users) {
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }
  }

  public userDetails(user) {
    this.router.navigate(['user-details/' + user.userCode]);
  }

  private changePassword(user) {
    this.router.navigate(['user-details/' + user.userCode + '/reset-password']);
  }

  private blockUser(element) {
    element.blocked = true;
    this.userService.blockUser(element.userCode).subscribe(
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  private unblockUser(element) {

    element.blocked = false;
    this.userService.unblockUser(element.userCode).subscribe(
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  private createNewUser() {
    this.router.navigate(['create-user']);
  }

  public searchForAllUsers(searchData?, searchIn?) {
    this.searchParam = searchData.target.value;
    this.applySearch(this.users, searchIn);
  }

  private applySearch(users: Array<User>, searchIn) {
    if (!this.searchParam) {
      if (this.showBlocked) {
        this.searchUsers = users.filter(param => param.blocked === true);
        this.initSearchTable();
      } else {
        this.initTable();
      }
    } else {
      this.searchUsers = users.filter((param) => (
        (param.userCode.toLowerCase() + ' ' + param.firstName.toLowerCase() + ' ' + param.lastName.toLowerCase() + ' ' + param.email)
          .indexOf(this.searchParam.toLowerCase()) > -1));
      if (this.showBlocked) {
        this.searchUsers = this.searchUsers.filter((param) => (param.blocked === true));
      }
      this.initSearchTable();
    }
  }

  private initSearchTable() {
    if (this.searchUsers) {
      this.dataSource = new MatTableDataSource(this.searchUsers);
      this.dataSource.sort = this.sort;
    }
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
}
