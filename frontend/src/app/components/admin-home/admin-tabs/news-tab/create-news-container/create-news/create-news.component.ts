import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CreateNewsWrapper} from './create-news-wrapper';
import {NewsService} from '../../../../../../services/news.service';

@Component({
  selector: 'app-create-news',
  templateUrl: './create-news.component.html',
  styleUrls: ['./create-news.component.css']
})
export class CreateNewsComponent implements OnInit {

  public wrapper = new CreateNewsWrapper();
  error;
  constructor(private router: Router,
              private route: ActivatedRoute,
              private newsService: NewsService) { }

  ngOnInit(): void {
  }
  public newsNavigate() {
    this.router.navigate(['administration', 'news']);
  }

  cancelCreateNewsMode() {
    this.newsNavigate();
  }
  saveNews() {
    if (this.wrapper.valid()) {
      this.newsService.save(this.wrapper.model).subscribe(
        (next) => {
          this.router.navigate(['administration', 'news']);
        },
        (error) => {
          this.error = error;
        }
      );
    }
  }

}
