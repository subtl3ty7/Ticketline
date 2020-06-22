import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {ActivatedRoute, Router} from '@angular/router';
import {MatTableDataSource} from '@angular/material/table';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';
import {NewsService} from '../../../../services/news.service';
import {SimpleNews} from '../../../../dtos/simple-news';
import {SearchNews} from '../../../../dtos/search-news';
import {SimpleEvent} from '../../../../dtos/simple-event';

@Component({
  selector: 'app-news-tab',
  templateUrl: './news-tab.component.html',
  styleUrls: ['./news-tab.component.css']
})
export class NewsTabComponent implements OnInit {

  public displayedColumns = [
    'newsCode',
    'title',
    'author',
    'publishedAt'
  ];
  public dataSource: any;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('scrollBlock') scrollBlock: ElementRef;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  error;
  private news: Array<SimpleNews>;
  private searchNews;
  private pageSize = 10;
  private currentPageIndex = 0;
  private previousPage;
  private currentPage;
  private nextPage;
  private searching: boolean;
  constructor(private newsService: NewsService,
              public  router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.searching = false;
    this.loadAllNews();
    this.searchNews = new SearchNews();
    this.searchNews.newsCode = '';
    this.searchNews.title = '';
    this.searchNews.publishedAtStartRange = new Date(2000, 1, 1);
    this.searchNews.publishedAtEndRange = new Date(3000, 1, 1);
    this.searchNews.author = '';
  }

  private loadAllNews() {
    this.currentPageIndex = 0;
    this.previousPage = [];
    this.paginator.pageIndex = 0;
    this.newsService.getAllSimpleNews(0).subscribe(
      (firstPageNews: SimpleNews[]) => {
        this.news = firstPageNews.slice(0, 10);
        this.currentPage = firstPageNews.slice(0, 10);
        this.newsService.getAllSimpleNews(this.pageSize).subscribe(
          (secondPageNews: SimpleNews[]) => {
            this.nextPage = secondPageNews;
            secondPageNews.forEach(value => {
              this.news.push(value);
            });
            this.initTable();
          },
          (error) => {
            this.error = error.error;
          }
        );
      },
      (error) => {
        this.error = error.error;
      }
    );
  }

  private pageEvent(event) {
    if (this.currentPageIndex === 1 && event.pageIndex === 0) {
      if(this.searching) {
        this.loadAllNewsByParameters();
      } else {
        this.loadAllNews();
      }
      return;
    }
    if (this.currentPageIndex === 0 && event.pageIndex === 1) {
      this.currentPageIndex++;
      this.paginator.pageIndex = 1;
      this.loadNextPage();
      return;
    }
    if (event.pageIndex > 1) {
      this.currentPageIndex++;
      this.paginator.pageIndex = 1;
      this.loadNextPage();
    } else if (event.pageIndex < 1) {
      this.currentPageIndex--;
      this.paginator.pageIndex = 1;
      this.loadPreviousPage();
    }
  }

  private loadNextPage() {
    if (this.searching) {
      this.newsService.getSimpleNewsByParameters(this.searchNews, this.pageSize * (this.currentPageIndex + 1)).subscribe (
        (news: SimpleNews[]) => {
          this.deleteFromNews(this.previousPage);
          this.previousPage = this.currentPage;
          this.currentPage = this.nextPage;
          this.nextPage = news;
          this.news = this.news.concat(news);
          console.log(this.news);
          this.initTable();
        },
        (error) => {
          this.error = error.error;
        }
      );
    } else {
      this.newsService.getAllSimpleNews(this.pageSize * (this.currentPageIndex + 1)).subscribe(
        (news: SimpleNews[]) => {
          this.deleteFromNews(this.previousPage);
          this.previousPage = this.currentPage;
          this.currentPage = this.nextPage;
          this.nextPage = news;
          this.news = this.news.concat(news);
          console.log(this.news);
          this.initTable();
        },
        (error) => {
          this.error = error.error;
        }
      );
    }
  }
  private loadPreviousPage() {
    if (this.searching) {
      this.newsService.getSimpleNewsByParameters(this.searchNews, this.pageSize * (this.currentPageIndex - 1)).subscribe(
        (news: SimpleNews[]) => {
          this.deleteFromNews(this.nextPage);
          this.nextPage = this.currentPage;
          this.currentPage = this.previousPage;
          this.previousPage = news;
          this.news = news.concat(this.news);
          console.log(this.news);

          this.initTable();
        },
        (error) => {
          this.error = error.error;
        }
      );
    } else {
      this.newsService.getAllSimpleNews(this.pageSize * (this.currentPageIndex - 1)).subscribe(
        (news: SimpleNews[]) => {
          this.deleteFromNews(this.nextPage);
          this.nextPage = this.currentPage;
          this.currentPage = this.previousPage;
          this.previousPage = news;
          this.news = news.concat(this.news);
          console.log(this.news);

          this.initTable();
        },
        (error) => {
          this.error = error.error;
        }
      );
    }
  }

  private deleteFromNews(news: SimpleNews[]) {
    if (news.length !== 0) {
      news.forEach(value => {
        const index = this.news.indexOf(value, 0);
        this.news.splice(index, 1);
      });
    }
  }

  private loadAllNewsByParameters() {

    this.currentPageIndex = 0;
    this.previousPage = [];
    this.paginator.pageIndex = 0;
    this.newsService.getSimpleNewsByParameters(this.searchNews, 0).subscribe(
      (firstPageNews: SimpleNews[]) => {
        this.news = firstPageNews.slice(0, 10);
        this.currentPage = firstPageNews.slice(0, 10);
        this.newsService.getSimpleNewsByParameters(this.searchNews, this.pageSize).subscribe(
          (secondPageNews: SimpleNews[]) => {
            this.nextPage = secondPageNews;
            secondPageNews.forEach(value => {
              this.news.push(value);
            });
            this.initTable();
          },
          (error) => {
            this.error = error.error;
          }
        );
      },
      (error) => {
        this.error = error.error;
      }
    );
  }

  private initTable() {
    if (this.news) {
      this.dataSource = new MatTableDataSource(this.news);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }
  }

  addStart(type: string, event: MatDatepickerInputEvent<Date>) {
    this.searchNews.publishedAtStartRange = event.value;
  }


  addEnd(type: string, event: MatDatepickerInputEvent<Date>) {
    this.searchNews.publishedAtEndRange = event.value;
  }

  private createNewNews() {
    this.router.navigate(['create-news']);
  }

  public newsDetails(news) {
    this.router.navigate(['news-details/' + news.newsCode]);
  }

}
