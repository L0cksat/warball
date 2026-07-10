import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TeamService {
  private apiUrl = 'http://localhost:8080/api/v1/teams'

  constructor(private httpClient: HttpClient) {}

  getTeams(): Observable<any>{
    return this.httpClient.get<any>(this.apiUrl)
  }
}
