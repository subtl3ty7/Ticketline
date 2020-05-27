import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Background} from '../../utils/background';

@Component({
  selector: 'app-faq',
  templateUrl: './faq.component.html',
  styleUrls: ['./faq.component.css']
})
export class FaqComponent implements OnInit {

  constructor(private authService: AuthService,
              private background: Background) {
  this.background.defineBackground();
  }

  ngOnInit(): void {
  }

}
