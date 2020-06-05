import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {ActivatedRoute, Router} from '@angular/router';
import {EventService} from '../../../../services/event.service';
import {User} from '../../../../dtos/user';
import {MatTableDataSource} from '@angular/material/table';
import {SimpleEvent} from '../../../../dtos/simple-event';
import {DetailedEvent} from '../../../../dtos/detailed-event';
import {MatPaginator} from '@angular/material/paginator';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';


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
  error;
  private events: Array<SimpleEvent>;
  private searchEvent;
  constructor(private eventService: EventService,
              public  router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadAllEvents();
    this.searchEvent = new SimpleEvent();
    this.searchEvent.eventCode = '';
    this.searchEvent.name = '';
    this.searchEvent.startsAt = new Date(2000, 1, 1);
    this.searchEvent.endsAt = new Date(3000, 1, 1);
  }

  private loadAllEvents() {
    this.eventService.getAllEvents().subscribe(
      (event: SimpleEvent[]) => {
        this.events = event;
        this.initTable();
      },
      (error) => {
        this.error = error.error;
      }
    );
  }
  private loadAllEventsByParameters() {
    console.log(this.searchEvent);
    this.eventService.getSimpleEventsByParameters(this.searchEvent).subscribe(
      (events: SimpleEvent[]) => {
        this.events = events;
        this.initTable();
      },
      (error) => {
        this.error = error.error;
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

  addStart(type: string, event: MatDatepickerInputEvent<Date>) {
    this.searchEvent.startsAt = event.value;
  }


  addEnd(type: string, event: MatDatepickerInputEvent<Date>) {
    this.searchEvent.endsAt = event.value;
  }

  private createNewEvent() {
    this.router.navigate(['create-event']);
  }

  public eventDetails(event) {
    this.router.navigate(['event-details/' + event.eventCode]);
  }
}
