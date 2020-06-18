import {Injectable, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class Background implements OnInit {

  private topTenUrl: string;

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  defineBackground() {
    if (this.router.url !== '/top-ten-events') {
      document.body.style.background = null;
      document.body.style.background = '#0c0d0f';
      document.body.style.backgroundImage = 'url("assets/images/bg.png")';
      document.body.style.backgroundRepeat = 'no-repeat';
      document.body.style.backgroundPosition = 'top';
      document.body.style.backgroundSize = '100%';
      console.log('setting regular background');
    }
  }

  defineTopTenBackground() {
    if (this.router.url === '/top-ten-events') {
      document.body.style.background = null;
      document.body.style.background = '#0c0d0f';
      document.body.style.backgroundImage = 'linear-gradient(148deg, #2771BB 0%, #FF91BE 100%)';
      document.body.style.backgroundRepeat = 'no-repeat';
      document.body.style.backgroundPosition = 'top';
      document.body.style.backgroundSize = '100% 3400px';
    }
  }
}
