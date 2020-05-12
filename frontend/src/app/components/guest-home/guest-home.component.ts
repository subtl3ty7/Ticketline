import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './guest-home.component.html',
  styleUrls: ['./guest-home.component.scss']
})
export class GuestHomeComponent implements OnInit {

  constructor(public authService: AuthService) { }

  ngOnInit() {
    document.body.style.backgroundImage = null;
    document.body.style.backgroundColor = '#DEDEDE';
  }

}
