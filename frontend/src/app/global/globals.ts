import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Globals {
  readonly backendUri: string = 'http://localhost:8080/api/v1';

  private choosenTab = 1;

  get getChoosenTab(): number {
    return this.choosenTab;
  }

  set setChoosenTab(value: number) {
    this.choosenTab = value;
  }

}
