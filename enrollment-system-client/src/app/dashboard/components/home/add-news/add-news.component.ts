import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {NewsService} from '../news.service';
import {News} from '../news.model';
import {AngularEditorConfig} from '@kolkov/angular-editor';
import {NgForm} from '@angular/forms';
import {NotificationsService} from 'angular2-notifications';
import {TokenStorageService} from '../../../../user/auth/token-storage.service';

@Component({
  selector: 'app-add-news',
  templateUrl: './add-news.component.html',
  styleUrls: ['./add-news.component.scss']
})
export class AddNewsComponent implements OnInit {
  isAdd = true;
  newsToEdit = new News();
  editorConfig: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    defaultParagraphSeparator: 'p',
    defaultFontName: 'Arial',
    placeholder: 'Enter the news description...',
    toolbarHiddenButtons: [
      [
        'undo',
        'redo',
        'subscript',
        'superscript',
        'indent',
        'outdent',
        'unlink',
        'insertImage',
        'insertVideo',
      ]
    ]
  };

  constructor(private route: ActivatedRoute,
              private newsService: NewsService,
              private router: Router,
              private notificationService: NotificationsService,
              private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id != null) {
      this.isAdd = false;
      this.getNews(Number(id));
    } else {
      this.setAuthorNews();
    }
  }

  getNews(id: number) {
    this.newsService.getNews(id).subscribe(result => {
      this.newsToEdit = result;
      this.setAuthorNews();
    }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
  }

  setAuthorNews() {
    const username = this.tokenStorage.getUser().username;
    this.newsToEdit.author.username = username;
  }

  saveNews(form: NgForm) {
    if (this.isAdd) {
      this.newsService.postNews(this.newsToEdit).subscribe(result => {
        this.notificationService.success('New news', 'You added news with the title: ' + result.title);
      }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
    } else {
      this.newsService.putNews(this.newsToEdit, this.newsToEdit.id).subscribe(result => {
        this.notificationService.success('Edit news', 'You updated news with the title: ' + result.title);
        this.router.navigateByUrl('/dashboard/news/add');
      }, err => this.notificationService.error(err.status + ': ' + err.error.status, err.error.message));
    }
    form.resetForm();
  }
}
