import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {MatTableDataSource} from '@angular/material/table';
import {Show} from '../../../../../../../../dtos/show';
import {EventLocation} from '../../../../../../../../dtos/event-location';
import {EventLocationService} from '../../../../../../../../services/event-location.service';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';

@Component({
  selector: 'app-create-shows',
  templateUrl: './create-shows.component.html',
  styleUrls: ['./create-shows.component.css']
})
export class CreateShowsComponent implements OnInit {
  public displayedColumns = [
    'startsAt',
    'endsAt',
    'eventLocation',
    'delete'
  ];

  public dataSource: any;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('scrollBlock') scrollBlock: ElementRef;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  show = new Show();
  eventLocations: EventLocation[];
  error;
  startTime: string;
  endTime: string;

  constructor(@Inject(MAT_DIALOG_DATA) public data: Show[],
              public dialogRef: MatDialogRef<CreateShowsComponent>,
              public eventLocationService: EventLocationService,
              private router: Router) {}

  ngOnInit(): void {
    this.initTable();
    this.getAllEventLocations();
  }

  private getAllEventLocations() {
    this.eventLocationService.getAllEventLocations().subscribe((next) => {
      this.eventLocations = next;
    }, (error) => {
      this.error = error.error;
    });
  }

  private initTable() {
    if (this.data) {
      this.dataSource = new MatTableDataSource(this.data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }
  }
  public closeModal() {
    this.dialogRef.close();
  }

  createShow() {
    if (this.show && this.show.startsAt  && this.show.endsAt && this.show.eventLocationCopy && this.startTime && this.endTime) {
      const show = new Show();
      show.startsAt = this.show.startsAt;
      const startHourAndMinute = this.startTime.split(':');
      show.startsAt.setHours(+startHourAndMinute[0], +startHourAndMinute[1], 0, 0);
      show.endsAt = this.show.endsAt;
      const endHourAndMinute = this.endTime.split(':');
      show.endsAt.setHours(+endHourAndMinute[0], +endHourAndMinute[1], 0, 0);
      show.eventLocationCopy = this.show.eventLocationCopy;
      show.ticketsAvailable = show.eventLocationCopy.capacity;
      show.eventLocationOriginalId = show.eventLocationCopy.id;
      this.data.push(show);
      this.show = new Show();
      this.initTable();
    }
  }

  addStartsAt(type: string, event: MatDatepickerInputEvent<Date>) {
    this.show.startsAt = event.value;
  }
  addEndsAt(type: string, event: MatDatepickerInputEvent<Date>) {
    this.show.endsAt = event.value;
  }

  delete(event) {
    const index = this.data.indexOf(event, 0);
    if (index > -1) {
      this.data.splice(index, 1);
    }
    this.initTable();
  }
}
