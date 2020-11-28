import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {INews} from './news.model';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private NEWS_URL = environment.basePath + '/news';

  constructor(private httpClient: HttpClient) { }

  getAllNews(): Observable<INews[]> {
    return this.httpClient.get<INews[]>(this.NEWS_URL);
  }

  getLatestNews(limit: number): Observable<INews[]> {
    return this.httpClient.get<INews[]>(this.NEWS_URL + '/latest/' + limit);
  }

  getNews(id: number): Observable<INews> {
    return this.httpClient.get<INews>(this.NEWS_URL + '/' + id);
  }

  postNews(news: INews): Observable<INews> {
    return this.httpClient.post<INews>(this.NEWS_URL, news);
  }

  putNews(news: INews, id: number): Observable<INews> {
    return this.httpClient.put<INews>(this.NEWS_URL + '/' + id, news);
  }

  deleteNews(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.NEWS_URL + '/' + id);
  }
}
