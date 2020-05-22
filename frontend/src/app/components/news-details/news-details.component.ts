import { Component, OnInit } from '@angular/core';
import {News} from '../../dtos/news';
import {NewsService} from '../../services/news.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-news-details',
  templateUrl: './news-details.component.html',
  styleUrls: ['./news-details.component.css']
})
export class NewsDetailsComponent implements OnInit {
  error;
  newsEntry: News;

  constructor(private newsService: NewsService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getNewsEntry();
  }
  getNewsEntry() {
    const newsCode = this.route.snapshot.paramMap.get('newsCode');
    this.newsService.getNewsEntry(newsCode).subscribe(
      (news) => {
        this.newsEntry = news;
      },
      (error) => {
        this.newsEntry = null;
        this.error = error.error;
      }
    );
  }

}
