import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {ActivatedRoute, Router} from '@angular/router';
import {MatTableDataSource} from '@angular/material/table';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';
import {News} from '../../../../dtos/news';
import {NewsService} from '../../../../services/news.service';
import {SimpleNews} from '../../../../dtos/simple-news';
import {SearchNews} from '../../../../dtos/search-news';

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
  constructor(private newsService: NewsService,
              public  router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadAllNews();
    this.searchNews = new SearchNews();
    this.searchNews.newsCode = '';
    this.searchNews.title = '';
    this.searchNews.publishedAtStartRange = new Date(2000, 1, 1);
    this.searchNews.publishedAtEndRange = new Date(3000, 1, 1);
    this.searchNews.author = '';
  }

  private loadAllNews() {
    this.newsService.getAllSimpleNews().subscribe(
      (news: SimpleNews[]) => {
        this.news = news;
        this.initTable();
      },
      (error) => {
        this.error = error.error;
      }
    );
  }
  private loadAllNewsByParameters() {
    this.newsService.getSimpleNewsByParameters(this.searchNews).subscribe(
      (news: SimpleNews[]) => {
        this.news = news;
        this.initTable();
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
