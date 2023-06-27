import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BookDTO} from "../../types/BookDTO";
import {AddBookDTO} from "../../types/AddBookDTO";
import {UpdateBookDTO} from "../../types/UpdateBookDTO";
import {BooksPage} from "../../types/BooksPage";

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiEndpointUrl = `${environment.apiUrl}/books`;

  constructor(private http: HttpClient) { }

  public getAll(page?: number, size?: number, sort?: string, search?: string): Observable<BooksPage> {
    return this.http.get<BooksPage>(this.apiEndpointUrl, {params: {
        ...(page && {page: page}),
        ...(size && {size: size}),
        ...(sort && {sort: sort}),
        ...(search && {search: search})
      }});
  }

  public getById(id: number): Observable<BookDTO> {
    return this.http.get<BookDTO>(`${this.apiEndpointUrl}/${id}`);
  }

  public add(dto: AddBookDTO): Observable<BookDTO> {
    return this.http.post<BookDTO>(this.apiEndpointUrl, dto);
  }

  public update(dto: UpdateBookDTO): Observable<BookDTO> {
    return this.http.put<BookDTO>(this.apiEndpointUrl, dto);
  }

  public delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiEndpointUrl}/${id}`);
  }
}
