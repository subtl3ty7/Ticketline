import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor() {
    document.body.style.background = '#0c0d0f';
    document.body.style.backgroundImage = 'url("assets/images/bg.png")';
    document.body.style.backgroundRepeat = 'no-repeat';
    document.body.style.backgroundPosition = 'top';
    document.body.style.backgroundSize = '100%';
  }

  ngOnInit() {
  }

}
