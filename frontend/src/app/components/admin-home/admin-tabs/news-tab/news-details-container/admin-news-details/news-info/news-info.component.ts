import {Component, Input, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {NewsDetailsDialogComponent} from './news-details-dialog/news-details-dialog.component';
import {News} from '../../../../../../../dtos/news';

@Component({
  selector: 'app-news-info',
  templateUrl: './news-info.component.html',
  styleUrls: ['./news-info.component.css']
})
export class NewsInfoComponent implements OnInit {
  @Input() news: News;
  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openNewsDetailsDialog() {
    this.dialog.open(NewsDetailsDialogComponent,
      {width: '100%', data: this.news});
  }
}
