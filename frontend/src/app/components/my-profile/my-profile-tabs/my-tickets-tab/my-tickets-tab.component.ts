import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {EventService} from '../../../../services/event.service';
import {ActivatedRoute} from '@angular/router';
import {TicketService} from '../../../../services/ticket.service';
import {DetailedTicket} from '../../../../dtos/detailed-ticket';
import {SimpleTicket} from '../../../../dtos/simple-ticket';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {Show} from '../../../../dtos/show';
import {ShowService} from '../../../../services/show.service';
import {Observable} from 'rxjs';
import {forEachComment} from 'tslint';
import {DetailedEvent} from '../../../../dtos/detailed-event';
import {User} from '../../../../dtos/user';
import {MyProfileWrapper, State} from '../../my-profile-wrapper';
import {UserService} from '../../../../services/user.service';

@Component({
  selector: 'app-my-tickets-tab',
  templateUrl: './my-tickets-tab.component.html',
  styleUrls: ['./my-tickets-tab.component.css']
})
export class MyTicketsTabComponent implements OnInit {
  public tickets: Array<SimpleTicket>;
  private events: Array<DetailedEvent> = new Array<DetailedEvent>();
  private event: DetailedEvent;
  private show: Show;
  error = false;
  errorMessage = '';
  public dataSource: any;
  public wrapper = new MyProfileWrapper();
  private state = State;
  public firstName: string;
  public lastName: string;
  public currentUser: User = new User();
  /*@ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;*/

  constructor(private ticketService: TicketService,
              private route: ActivatedRoute,
              private showService: ShowService,
              private eventService: EventService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.loadUser();
  }
  private loadUser() {
    this.userService.getCurrentUser().subscribe(
      (user: User) => {
        Object.assign(this.currentUser, user);
        this.loadTickets();
      },
      (error) => {
        this.error = error.error;
      }
    );
  }
  public loadTickets(): void {
      this.ticketService.getDetailedTicketsByUserCode(this.currentUser.userCode).subscribe(
        (tickets: SimpleTicket[]) => {
          this.tickets = tickets;
        },
        (error) => {
          this.error = error.error;
        }
      );
  }
 /* public getShowByShowId(showId: number) {
   return this.showService.getShowByShowId(showId).subscribe(
      show => { this.show = show; },
      error => { this.defaultServiceErrorHandling(error); }
    );
  }
 /* private initTable() {
    if (this.tickets) {
      this.dataSource = new MatTableDataSource(this.tickets);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }
  }*/

 /*
  public getEventByEventCode(eventCode: string): DetailedEvent {
    let resultEvent;
    this.eventService.getDetailedEventByUserCode(eventCode).subscribe(
      event => {
         resultEvent = event;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
    return resultEvent;
  }*/
}