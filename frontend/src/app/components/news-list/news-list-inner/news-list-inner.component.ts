import {Component, Input, OnInit} from '@angular/core';
import {News} from '../../../dtos/news';
import {DatePipe} from '@angular/common';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-news-list-inner',
  templateUrl: './news-list-inner.component.html',
  styleUrls: ['./news-list-inner.component.css'],
  providers: [DatePipe]
})
export class NewsListInnerComponent implements OnInit {
  @Input() news: News[][];

  constructor(private datePipe: DatePipe) { }

  ngOnInit(): void {
  }

  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'EEEE, dd. MMMM y');
  }

}
