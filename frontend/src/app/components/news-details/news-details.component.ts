import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {Background} from '../../utils/background';

@Component({
  selector: 'app-news-details',
  templateUrl: './news-details.component.html',
  styleUrls: ['./news-details.component.css']
})
export class NewsDetailsComponent implements OnInit {

  constructor(private background: Background) {
    this.background.defineBackground();
  }

  ngOnInit(): void {
  }
}
