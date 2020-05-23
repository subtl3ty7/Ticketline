import { Component, OnInit } from '@angular/core';
import {News} from '../../../dtos/news';
import {NewsService} from '../../../services/news.service';
import {ActivatedRoute} from '@angular/router';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-news-details-inner',
  templateUrl: './news-details-inner.component.html',
  styleUrls: ['./news-details-inner.component.css'],
  providers: [DatePipe]
})
export class NewsDetailsInnerComponent implements OnInit {
  error;
  newsEntry: News;

  constructor(private newsService: NewsService, private route: ActivatedRoute, private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.getNewsEntry();
  }
  getNewsEntry() {
    const newsCode = this.route.snapshot.paramMap.get('nc');
    console.log('Newscode: ' + newsCode);
    this.newsService.getNewsEntry(newsCode).subscribe(
      (news) => {
        this.newsEntry = news;
        console.log(this.newsEntry.text);
      },
      (error) => {
        this.newsEntry = null;
        this.error = error.error;
      }
    );
  }

  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'dd.MM.yyyy');
  }
}
