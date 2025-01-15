import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as Papa from 'papaparse';

@Injectable({
  providedIn: 'root',
})
export class CsvService {
  constructor(private http: HttpClient) {}

  loadCsv(filePath: string): Observable<any[]> {
    return new Observable((observer) => {
      this.http.get(filePath, { responseType: 'text' }).subscribe({
        next: (data) => {
          Papa.parse(data, {
            header: true,
            complete: (result) => {
              observer.next(result.data);
              observer.complete();
            },
            error: (error: any) => {
              observer.error(error);
            },
          });
        },
        error: (error) => observer.error(error),
      });
    });
  }
}
