import { Component, OnInit } from '@angular/core';
import { NewsService} from '../../../services/news.service';
import {SimpleEvent} from '../../../dtos/simple-event';
import {News} from '../../../dtos/news';

@Component({
  selector: 'app-latest-news',
  templateUrl: './latest-news.component.html',
  styleUrls: ['./latest-news.component.css']
})
export class LatestNewsComponent implements OnInit {
  error;
  unseenNews: News[][];

  constructor(private newsService: NewsService) { }

  ngOnInit(): void {
    this.getLatestUnseen();
  }

  /**
   * Tries to load 6 of the latest news that the user has not seen yet
   */
  public getLatestUnseen() {
    this.newsService.getLatestUnseen().subscribe(
      (news) => {
        this.unseenNews = [];
        // split news into blocks of three
        const size = news.length / 3;
        for (let i = 0; i < size; i++) {
          this.unseenNews.push([
            news[3 * i],
            news[3 * i + 1],
            news[3 * i + 2]
          ]);
        }
      },
      (error) => {
        this.unseenNews = null;
        this.error = error.error;
      }
    );
  }

}
