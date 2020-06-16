import { Component, OnInit } from '@angular/core';
import {Background} from '../../utils/background';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private background: Background, private authService: AuthService) {
    this.background.defineBackground();
  }

  ngOnInit() {
  }

}
