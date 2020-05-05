import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';
import { AlphaNumericCombination } from './combinations/combinations';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const apiUrl = 'http://localhost:80';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  getCombinations(phoneNumber: string, pageNumber: number, limit: string): Observable<AlphaNumericCombination> {
    const url = `${apiUrl}/phone/number/${phoneNumber}/combinations?pageNumber=${pageNumber}&limit=${limit}`;
    return this.http.get<AlphaNumericCombination>(url)
      .pipe(
        tap(_ => console.log(_)),
        catchError(this.handleError<AlphaNumericCombination>(`getCasesById`))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
