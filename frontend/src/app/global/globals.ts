import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Globals {
  readonly backendUri: string = 'http://localhost:8080/api/v1';

  private choosenTab = 1;
  private profileChoosenTab = 1;

  get getChoosenTab(): number {
    return this.choosenTab;
  }

  set setChoosenTab(value: number) {
    this.choosenTab = value;
  }

  get getProfileChoosenTab(): number {
    return this.profileChoosenTab;
  }

  set setProfileChoosenTab(value: number) {
    this.profileChoosenTab = value;
  }

}
