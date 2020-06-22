import { Component, OnInit } from '@angular/core';
import {News} from '../../dtos/news';
import {NewsService} from '../../services/news.service';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {Background} from '../../utils/background';

@Component({
  selector: 'app-news',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.css']
})
export class NewsListComponent implements OnInit {
  errorLatest;
  errorSeen;
  latestNews: News[][];
  seenNews: News[][];

  constructor(private newsService: NewsService, private authService: AuthService, private background: Background) {
    this.background.defineBackground();
  }

  ngOnInit(): void {
    this.getLatest();
    if (this.authService.isLoggedIn()) {
      this.getSeen();
    }
  }

  /**
   * Tries to load 6 of the latest news that the user has not seen yet
   */
  public getSeen() {
    this.newsService.getSeen(null).subscribe(
      (news) => {
        this.seenNews = this.constructBlocksOfTwo(news);
      },
      (error) => {
        this.errorSeen = error.error;
      }
    );
  }

  /**
   * Tries to load 6 of the latest news that the user has not seen yet
   */
  public getLatest() {
    this.newsService.getLatest(null).subscribe(
      (news) => {
        this.latestNews = this.constructBlocksOfTwo(news);
      },
      (error) => {
        this.errorLatest = error;
      }
    );
  }

  constructBlocksOfTwo(news: News[]): News[][] {
    const blocks: News[][] = [];
    // split news into blocks of 2
    const size = Math.floor(news.length / 2);
    for (let i = 0; i < size; i++) {
      blocks.push([
        news[2 * i],
        news[2 * i + 1]
      ]);
    }
    // last block might contain less than 2 elements, so construct it separately
    const lastBlock = [];
    const remainder = news.length % 2;
    for (let i = 0; i < remainder; i++) {
      lastBlock.push(news[2 * size + i]);
    }
    if (lastBlock.length > 0) {
      blocks.push(lastBlock);
    }
    return blocks;
  }
}
