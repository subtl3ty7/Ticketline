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

  getUnseen(limit: number): Observable<News[]> {
    console.log('Load latest news that the customer has not seen yet.');
    let URL = this.eventBaseUri + '/unseen?limit=' + limit;
    if (!limit) {
      URL = this.eventBaseUri + '/unseen';
    }
    return this.httpClient.get<News[]>(URL);
  }

  getSeen(limit: number): Observable<News[]> {
    console.log('Load latest news that the customer has not seen yet.');
    let URL = this.eventBaseUri + '/seen?limit=' + limit;
    if (!limit) {
      URL = this.eventBaseUri + '/seen';
    }
    return this.httpClient.get<News[]>(URL);
  }

  getLatest(limit: number): Observable<News[]> {
    console.log('Load latest news.');
    let URL = this.eventBaseUri + '/latest?limit=' + limit;
    if (!limit) {
      URL = this.eventBaseUri + '/latest';
    }
    return this.httpClient.get<News[]>(URL);
  }

  getNewsEntry(newsCode: string): Observable<News> {
    console.log('Load news by newsCode.');
    return this.httpClient.get<News>(this.eventBaseUri + '/' + newsCode);
  }
}
