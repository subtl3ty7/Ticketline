import { Component, OnInit } from '@angular/core';
import {NewsDetailsWrapper, State} from './news-details-wrapper';
import {ActivatedRoute, Router} from '@angular/router';
import {NewsService} from '../../../../../../services/news.service';
import {News} from '../../../../../../dtos/news';

@Component({
  selector: 'app-admin-news-details',
  templateUrl: './admin-news-details.component.html',
  styleUrls: ['./admin-news-details.component.css']
})
export class AdminNewsDetailsComponent implements OnInit {
  private nc: string;
  private sub: any;
  public wrapper = new NewsDetailsWrapper();
  private state = State;
  public title: string;
  constructor(private router: Router,
              private route: ActivatedRoute,
              private newsService: NewsService) { }

  ngOnInit(): void {
    this.wrapper.state = this.state.READY;
    this.loadNews();
  }
  private loadNews() {
    this.sub = this.route.params.subscribe(params => {
      this.nc = params['nc'];
    });
    if (this.nc) {
      this.newsService.getNewsEntry(this.nc).subscribe(
        (news: News) => {
          Object.assign(this.wrapper.model, news);
          this.title = this.wrapper.model.title;
        },
        error => {
          this.router.navigate(['administration', 'news']);
        }
      );
    }
  }
  public newsNavigate() {
    if (this.wrapper.state !== this.state.PROCESSING) {
      this.router.navigate(['administration', 'news']);
    }
  }

}
