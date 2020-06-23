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
  private pageSize = 10;
  private currentPageIndex = 0;
  private previousPage;
  private currentPage;
  private nextPage;
  private searching: boolean;
  constructor(private eventService: EventService,
              public  router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.searching = false;
    this.loadAllEvents();
    this.searchEvent = new SimpleEvent();
    this.searchEvent.eventCode = '';
    this.searchEvent.name = '';
    this.searchEvent.startsAt = new Date(2000, 1, 1);
    this.searchEvent.endsAt = new Date(3000, 1, 1);
  }

  private loadAllEvents() {
    this.currentPageIndex = 0;
    this.previousPage = [];
    this.paginator.pageIndex = 0;
    this.eventService.getAllEvents(0).subscribe(
      (firstPageEvents: SimpleEvent[]) => {
        this.events = firstPageEvents.slice(0, 10);
        this.currentPage = firstPageEvents.slice(0, 10);
        this.eventService.getAllEvents(this.pageSize).subscribe(
          (secondPageEvents: SimpleEvent[]) => {
            this.nextPage = secondPageEvents;
            secondPageEvents.forEach(value => {
              this.events.push(value);
            });
            this.initTable();
          },
          (error) => {
            this.error = error.error;
          }
        );
      },
      (error) => {
        this.error = error.error;
      }
    );
  }

  private pageEvent(event) {
    if (this.currentPageIndex === 1 && event.pageIndex === 0) {
      if(this.searching){
        this.loadAllEventsByParameters();
      } else {
      this.loadAllEvents();
      }
      return;
    }
    if (this.currentPageIndex === 0 && event.pageIndex === 1) {
      this.currentPageIndex++;
      this.paginator.pageIndex = 1;
      this.loadNextPage();
      return;
    }
    if (event.pageIndex > 1) {
        this.currentPageIndex++;
        this.paginator.pageIndex = 1;
        this.loadNextPage();
    } else if (event.pageIndex < 1) {
      this.currentPageIndex--;
      this.paginator.pageIndex = 1;
      this.loadPreviousPage();
    }
  }

  private loadNextPage() {
    if (this.searching) {
      this.eventService.getSimpleEventsByParameters(this.searchEvent, this.pageSize * (this.currentPageIndex + 1)).subscribe (
        (events: SimpleEvent[]) => {
          this.deleteFromEvents(this.previousPage);
          this.previousPage = this.currentPage;
          this.currentPage = this.nextPage;
          this.nextPage = events;
          this.events = this.events.concat(events);
          console.log(this.events);
          this.initTable();
        },
        (error) => {
          this.error = error.error;
        }
      );
    } else {
     this.eventService.getAllEvents(this.pageSize * (this.currentPageIndex + 1)).subscribe(
      (events: SimpleEvent[]) => {
        this.deleteFromEvents(this.previousPage);
        this.previousPage = this.currentPage;
        this.currentPage = this.nextPage;
        this.nextPage = events;
        this.events = this.events.concat(events);
        console.log(this.events);
        this.initTable();
        },
      (error) => {
        this.error = error.error;
      }
    );
    }
  }
  private loadPreviousPage() {
    if (this.searching) {
      this.eventService.getSimpleEventsByParameters(this.searchEvent, this.pageSize * (this.currentPageIndex - 1)).subscribe(
        (events: SimpleEvent[]) => {
          this.deleteFromEvents(this.nextPage);
          this.nextPage = this.currentPage;
          this.currentPage = this.previousPage;
          this.previousPage = events;
          this.events = events.concat(this.events);
          console.log(this.events);

          this.initTable();
        },
        (error) => {
          this.error = error.error;
        }
      );
    } else {
      this.eventService.getAllEvents(this.pageSize * (this.currentPageIndex - 1)).subscribe(
        (events: SimpleEvent[]) => {
          this.deleteFromEvents(this.nextPage);
          this.nextPage = this.currentPage;
          this.currentPage = this.previousPage;
          this.previousPage = events;
          this.events = events.concat(this.events);
          console.log(this.events);

          this.initTable();
        },
        (error) => {
          this.error = error.error;
        }
      );
    }

  }
  private deleteFromEvents(events: SimpleEvent[]) {
    if (events.length !== 0) {
      events.forEach(value => {
        const index = this.events.indexOf(value, 0);
        this.events.splice(index, 1);
      });
    }
  }

  private loadAllEventsByParameters() {

    this.currentPageIndex = 0;
    this.previousPage = [];
    this.paginator.pageIndex = 0;
    this.eventService.getSimpleEventsByParameters(this.searchEvent, 0).subscribe(
      (firstPageEvents: SimpleEvent[]) => {
        this.events = firstPageEvents.slice(0, 10);
        this.currentPage = firstPageEvents.slice(0, 10);
        this.eventService.getSimpleEventsByParameters(this.searchEvent, this.pageSize).subscribe(
          (secondPageEvents: SimpleEvent[]) => {
            this.nextPage = secondPageEvents;
            secondPageEvents.forEach(value => {
              this.events.push(value);
            });
            this.initTable();
          },
          (error) => {
            this.error = error.error;
          }
        );
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
