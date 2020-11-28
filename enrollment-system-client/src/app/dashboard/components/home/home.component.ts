import {Component, OnInit} from '@angular/core';
import {INews} from './news.model';
import {NewsService} from './news.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  news: INews[] = [];

  constructor(private newsService: NewsService) { }

  ngOnInit(): void {
    this.getLatestNews();
  }

  getLatestNews() {
    this.newsService.getLatestNews(10).subscribe(result => {
      this.news = result;
    }, error => console.log(error));
  }

}
