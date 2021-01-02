import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PdfService {
  private PDF_URL = environment.basePath + "/pdf/";

  constructor(private http: HttpClient) { }

  getPdfStream(id: number) {
    let headerOptions = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/pdf'
    });
    let requestOptions = { headers: headerOptions, responseType: 'blob' as 'blob' };
    return this.http.get(this.PDF_URL + id, requestOptions);
  }
}
