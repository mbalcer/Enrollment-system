import {Component, OnInit} from '@angular/core';
import {INews} from './news.model';
import {NewsService} from './news.service';
import {TokenStorageService} from '../../../user/auth/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  news: INews[] = [];

  constructor(private newsService: NewsService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.getLatestNews();
  }

  getLatestNews() {
    this.newsService.getLatestNews(10).subscribe(result => {
      this.news = result;
    }, error => console.log(error));
  }

  deleteNews(newsItem: INews) {
    if (confirm('Are you sure you want to delete the news with the title "' + newsItem.title + '"')) {
      this.newsService.deleteNews(newsItem.id).subscribe(result => {
        this.news.splice(this.news.indexOf(newsItem), 1);
      });
    }
  }

  checkUserIsAdmin() {
    return this.tokenStorage.isRole('ADMIN');
  }
}
