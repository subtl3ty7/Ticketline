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
  /*@ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;*/

  constructor(private ticketService: TicketService,
              private route: ActivatedRoute,
              private showService: ShowService,
              private eventService: EventService) { }

  ngOnInit(): void {
    this.loadTickets();
  }
  public loadTickets(): void {
    const userCode = this.route.snapshot.paramMap.get('userCode');
    this.ticketService.getDetailedTicketsByUserCode(userCode).subscribe(
      (tickets: SimpleTicket[]) => {
        this.tickets = tickets;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }
  public getShowByShowId(showId: number) {
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
  }
}
