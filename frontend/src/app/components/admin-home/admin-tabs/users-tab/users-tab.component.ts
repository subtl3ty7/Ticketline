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

  error;
  private users: Array<User>;
  private searchBlocked: Array<User>;
  private showBlocked = false;
  private searchUser;

  constructor(private userService: UserService,
              public  router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.loadAllUsers();
    this.searchUser = new User();
    this.searchUser.userCode = '';
    this.searchUser.firstName = '';
    this.searchUser.lastName = '';
    this.searchUser.email = '';
  }

  private loadAllUsers() {
    this.userService.getAllUsers().subscribe(
      (users: User[]) => {
        this.users = users;
        this.initTable();
      },
      error => {
        this.error = error.error;
      }
    );
  }

  private loadAllUsersByParameters() {
    console.log(this.searchUser);
    this.userService.getUsersByParameters(this.searchUser).subscribe(
      (users: User[]) => {
        this.users = users;
        this.initTable();
      },
      (error) => {
        this.error = error.error;
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
    this.userService.blockUser(element.userCode).subscribe( () => {},
      (error) => {
        this.error = error.error;
      }
    );
  }

  private unblockUser(element) {

    element.blocked = false;
    this.userService.unblockUser(element.userCode).subscribe( () => {},
      (error) => {
        this.error = error.error;
      }
    );
  }

  private createNewUser() {
    this.router.navigate(['create-user']);
  }
  private applyBlocked(users: Array<User>) {
    if (this.showBlocked) {
      this.searchBlocked = users.filter(param => param.blocked === true);
      this.initSearchTable();
    } else {
      this.initTable();
    }
  }
private initSearchTable() {
  if (this.searchBlocked) {
    this.dataSource = new MatTableDataSource(this.searchBlocked);
    this.dataSource.sort = this.sort;
  }
}

}
