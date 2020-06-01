import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {ActivatedRoute, Router} from '@angular/router';
import {EventService} from '../../../../services/event.service';
import {User} from '../../../../dtos/user';
import {MatTableDataSource} from '@angular/material/table';
import {SimpleEvent} from '../../../../dtos/simple-event';
import {DetailedEvent} from '../../../../dtos/detailed-event';
import {MatPaginator} from '@angular/material/paginator';


@Component({
  selector: 'app-events-tab',
  templateUrl: './events-tab.component.html',
  styleUrls: ['./events-tab.component.css']
})
export class EventsTabComponent implements OnInit {
  public displayedColumns = [
    'eventCode',
    'name',
    'startsAt',
    'endsAt'
  ];
  public dataSource: any;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('scrollBlock') scrollBlock: ElementRef;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  error = false;
  errorMessage = '';
  private event: SimpleEvent;
  private events: Array<SimpleEvent>;
  constructor(private eventService: EventService,
              public  router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadAllEvents();
  }

  private loadAllEvents() {
    this.eventService.getAllEvents().subscribe(
      (event: SimpleEvent[]) => {
        this.events = event;
        this.initTable();
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }
  private initTable() {
    if (this.events) {
      this.dataSource = new MatTableDataSource(this.events);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }
  }

  private createNewEvent() {
    this.router.navigate(['create-event']);
  }

  public eventDetails(event) {
    this.router.navigate(['event-details/' + event.eventCode]);
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
  public getName(): string {
    return this.event.name;
  }
}
