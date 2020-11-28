import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {NewsService} from '../news.service';
import {News} from '../news.model';
import {AngularEditorConfig} from '@kolkov/angular-editor';

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

  constructor(private route: ActivatedRoute, private newsService: NewsService, private router: Router) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id != null) {
      this.isAdd = false;
      this.getNews(Number(id));
    }
  }

  getNews(id: number) {
    this.newsService.getNews(id).subscribe(result => {
      this.newsToEdit = result;
    }, error => console.log(error));
  }

  saveNews() {
    if (this.isAdd) {
      this.newsService.postNews(this.newsToEdit).subscribe(result => {
        this.newsToEdit = new News();
      }, error => console.log(error));
    } else {
      this.newsService.putNews(this.newsToEdit, this.newsToEdit.id).subscribe(result => {
        this.router.navigateByUrl('/dashboard/news/add');
      }, error => console.log(error));
    }
  }
}
