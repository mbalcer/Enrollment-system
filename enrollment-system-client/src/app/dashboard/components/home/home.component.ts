import {Component, OnInit} from '@angular/core';
import {INews} from './news.model';
import {NewsService} from './news.service';
import {TokenStorageService} from '../../../user/auth/token-storage.service';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  news: INews[] = [];

  constructor(private newsService: NewsService,
              private tokenStorage: TokenStorageService,
              private notificationService: NotificationsService) { }

  ngOnInit(): void {
    this.getLatestNews();
  }

  getLatestNews() {
    this.newsService.getLatestNews(10).subscribe(result => {
      this.news = result;
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  deleteNews(newsItem: INews) {
    if (confirm('Are you sure you want to delete the news with the title "' + newsItem.title + '"?')) {
      this.newsService.deleteNews(newsItem.id).subscribe(result => {
        this.notificationService.success("Delete news", "The news with title '" + newsItem.title + "' was deleted")
        this.news.splice(this.news.indexOf(newsItem), 1);
      }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
    } else {
      this.notificationService.info("Delete news", "The news wasn't deleted");
    }
  }

  checkUserIsAdmin() {
    return this.tokenStorage.isRole('ADMIN');
  }
}
