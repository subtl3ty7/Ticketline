import { Component, OnInit } from '@angular/core';
import { NewsService} from '../../../services/news.service';
import {SimpleEvent} from '../../../dtos/simple-event';
import {News} from '../../../dtos/news';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-latest-news',
  templateUrl: './latest-news.component.html',
  styleUrls: ['./latest-news.component.css']
})
export class LatestNewsComponent implements OnInit {
  error;
  latestNews: News[][];

  constructor(private newsService: NewsService, private authService: AuthService) { }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.getUnseen();
    } else {
      this.getLatest();
    }
  }

  /**
   * Tries to load 6 of the latest news that the user has not seen yet
   */
  public getUnseen() {
    this.newsService.getUnseen(6).subscribe(
      (news) => {
        this.constructBlocksOfThree(news);
      },
      (error) => {
        this.latestNews = null;
        this.error = error;
      }
    );
  }

  /**
   * Tries to load 6 of the latest news that the user has not seen yet
   */
  public getLatest() {
    this.newsService.getLatest(0, 6).subscribe(
      (news) => {
        this.constructBlocksOfThree(news);
      },
      (error) => {
        this.latestNews = null;
        this.error = error;
      }
    );
  }

  private constructBlocksOfThree(news: News[]) {
    this.latestNews = null;
    this.latestNews = [];
    // split news into blocks of three
    const size = Math.floor(news.length / 3);
    console.log(size);
    for (let i = 0; i < size; i++) {
      this.latestNews.push([
        news[3 * i],
        news[3 * i + 1],
        news[3 * i + 2]
      ]);
    }
    // last block might contain less than 3 elements, so construct it separately
    const lastBlock = [];
    const remainder = news.length % 3;
    for (let i = 0; i < remainder; i++) {
      lastBlock.push(news[3 * size + i]);
    }
    if (lastBlock.length > 0) {
      this.latestNews.push(lastBlock);
    }
    if (this.latestNews.length === 0) {
      this.latestNews = null;
      this.error = {
        'messages': ['Du hast bereits alle aktuellen News gelesen!']
      };
    }
    console.log(this.error);
  }

}
