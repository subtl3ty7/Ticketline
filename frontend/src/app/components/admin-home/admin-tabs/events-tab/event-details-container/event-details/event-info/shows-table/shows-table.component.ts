import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {EventService} from '../../../../../../../../services/event.service';
import {Show} from '../../../../../../../../dtos/show';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-shows-table',
  templateUrl: './shows-table.component.html',
  styleUrls: ['./shows-table.component.css']
})
export class ShowsTableComponent implements OnInit {
  public displayedColumns = [
    'startsAt',
    'endsAt',
    'eventLocation',
    'tsold',
    'tavailable'
  ];

  public dataSource: any;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('scrollBlock') scrollBlock: ElementRef;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(@Inject(MAT_DIALOG_DATA) public data: Show[],
              public dialogRef: MatDialogRef<ShowsTableComponent>,
              private eventService: EventService,
              private router: Router) { }

  ngOnInit(): void {
    this.initTable();
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

}
