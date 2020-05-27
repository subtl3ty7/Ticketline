import { Component, OnInit } from '@angular/core';
import {Background} from '../../utils/background';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor(private background: Background) {
    this.background.defineBackground();
  }

  ngOnInit() {
  }

}
