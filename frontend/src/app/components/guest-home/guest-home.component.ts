import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-guest-home',
  templateUrl: './guest-home.component.html',
  styleUrls: ['./guest-home.component.scss']
})
export class GuestHomeComponent implements OnInit {

  constructor(public authService: AuthService) {

    document.body.style.background = '#0c0d0f';
    document.body.style.backgroundImage = 'url("img_1.png")';
    document.body.style.backgroundSize = '100%';
    document.body.style.backgroundRepeat = 'no-repeat';
    document.body.style.backgroundPosition = 'center';
    document.body.style.backgroundSize = 'cover';
  }

  ngOnInit() {
    document.body.style.backgroundImage = null;
    document.body.style.backgroundColor = '#DEDEDE';
  }

}
