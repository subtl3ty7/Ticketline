import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Globals {
  readonly backendUri: string = 'http://localhost:8080/api/v1';

  private choosenTab = 'users';
  private profileChoosenTab = 'my-info';

  get getChoosenTab(): string {
    return this.choosenTab;
  }

  set setChoosenTab(value: string) {
    this.choosenTab = value;
  }

  get getProfileChoosenTab(): string {
    return this.profileChoosenTab;
  }

  set setProfileChoosenTab(value: string) {
    this.profileChoosenTab = value;
  }

}
