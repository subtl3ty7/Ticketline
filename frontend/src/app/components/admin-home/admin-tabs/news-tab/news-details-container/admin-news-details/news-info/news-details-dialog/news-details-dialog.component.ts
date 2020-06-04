import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {News} from '../../../../../../../../dtos/news';

@Component({
  selector: 'app-news-details-dialog',
  templateUrl: './news-details-dialog.component.html',
  styleUrls: ['./news-details-dialog.component.css']
})
export class NewsDetailsDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: News,
              public dialogRef: MatDialogRef<NewsDetailsDialogComponent>) { }

  ngOnInit(): void {
  }

}
