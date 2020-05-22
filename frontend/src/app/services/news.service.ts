import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {News} from '../dtos/news';

@Injectable({
  providedIn: 'root'
})

export class NewsService {
  private eventBaseUri: string = this.globals.backendUri + '/news';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getLatestUnseen(): Observable<News[]> {
    console.log('Load latest 6 news that the customer has not seen yet.');
    return this.httpClient.get<News[]>(this.eventBaseUri + '/latest-unseen');
  }

  getNewsEntry(newsCode: string): Observable<News> {
    console.log('Load news by newsCode.');
    return this.httpClient.get<News>(this.eventBaseUri + '/' + newsCode);
  }
}
