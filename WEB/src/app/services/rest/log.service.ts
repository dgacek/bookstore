import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.development";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LogService {

  private apiEndpointUrl = `${environment.apiUrl}/logs`;
  constructor(private http: HttpClient) { }

  public getRequestCount(): Observable<number> {
    return this.http.get<number>(this.apiEndpointUrl);
  }
}
