import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Background {
  defineBackground() {
    document.body.style.background = null;
    document.body.style.background = '#0c0d0f';
    document.body.style.backgroundImage = 'url("assets/images/bg.png")';
    document.body.style.backgroundRepeat = 'no-repeat';
    document.body.style.backgroundPosition = 'top';
    document.body.style.backgroundSize = '100%';
  }
}
