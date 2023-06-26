import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthorDTO} from "../../types/AuthorDTO";
import {environment} from "../../../environments/environment.development";
import {AddAuthorDTO} from "../../types/AddAuthorDTO";
import {AuthorsPage} from "../../types/AuthorsPage";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private apiEndpointUrl = `${environment.apiUrl}/authors`;

  constructor(private http: HttpClient) { }

  public getAll(page: number, size: number, sort: string, search?: string): Observable<AuthorsPage> {
    return this.http.get<AuthorsPage>(this.apiEndpointUrl, {params: {
        page: page,
        size: size,
        sort: sort,
        ...(search && {search: search})
      }});
  }

  public getById(id: number): Observable<AuthorDTO> {
    return this.http.get<AuthorDTO>(`${this.apiEndpointUrl}/${id}`);
  }

  public add(dto: AddAuthorDTO): Observable<AuthorDTO> {
    return this.http.post<AuthorDTO>(this.apiEndpointUrl, dto);
  }

  public update(dto: AuthorDTO): Observable<AuthorDTO> {
    return this.http.put<AuthorDTO>(this.apiEndpointUrl, dto);
  }

  public delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiEndpointUrl}/${id}`);
  }
}
