import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {News} from '../dtos/news';
import {SimpleEvent} from '../dtos/simple-event';
import {SimpleNews} from '../dtos/simple-news';
import {SearchNews} from '../dtos/search-news';

@Injectable({
  providedIn: 'root'
})

export class NewsService {
  private newsBaseUri: string = this.globals.backendUri + '/news';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getUnseen(limit: number): Observable<News[]> {
    console.log('Load latest news that the customer has not seen yet.');
    let URL = this.newsBaseUri + '/unseen?limit=' + limit;
    if (!limit) {
      URL = this.newsBaseUri + '/unseen';
    }
    return this.httpClient.get<News[]>(URL);
  }

  getSeen(page: number, size: number): Observable<News[]> {
    console.log('Load latest news that the customer has not seen yet.');
    if (!page) {
      page = 0;
    }
    if (!size) {
      size = 0;
    }
    const URL = this.newsBaseUri + '/seen?page=' + page + '&size=' + size;
    console.log(URL);
    return this.httpClient.get<News[]>(URL);
  }

  getLatest(page: number, size: number): Observable<News[]> {
    console.log('Load latest news.');
    if (!page) {
      page = 0;
    }
    if (!size) {
      size = 0;
    }
    const URL = this.newsBaseUri + '/latest?page=' + page + '&size=' + size;
    console.log(URL);
    return this.httpClient.get<News[]>(URL);
  }

  getNewsEntry(newsCode: string): Observable<News> {
    console.log('Load news by newsCode.');
    return this.httpClient.get<News>(this.newsBaseUri + '/' + newsCode);
  }

  getAllSimpleNews(size: number) {
    console.log('Load with size ' + size);
    return this.httpClient.get<SimpleNews[]>(this.newsBaseUri + '/all' + '?size=' + size);
  }

  getSimpleNewsByParameters(searchNews: SearchNews, size: number) {
    const params = new HttpParams()
      .set('newsCode', searchNews.newsCode)
      .set('title', searchNews.title)
      .set('author', searchNews.author)
      .set('startRange', searchNews.publishedAtStartRange.toDateString())
      .set('endRange', searchNews.publishedAtEndRange.toDateString())
      .set('size', size.toString());
    return this.httpClient.get<SimpleNews[]>(this.newsBaseUri + '/', {params});
}

  save(news: News) {
    return this.httpClient.post<News>(this.newsBaseUri + '/', news);
  }
}
