import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Team } from '../models/team/team';


@Injectable({
  providedIn: 'root',
})
export class TeamService {
  private readonly apiUrl = 'http://localhost:8080/api/v1/teams'

  constructor(private httpClient: HttpClient) {}

  getTeams(): Observable<Team[]>{
    return this.httpClient.get<Team[]>(this.apiUrl)
  }

  getTeam(id: number): Observable<Team> {
    return this.httpClient.get<Team>(`${this.apiUrl}/${id}`)
  }

  createTeam(team: Team): Observable<Team> {
    const { id, ...payload } = team
    return this.httpClient.post<Team>(this.apiUrl, payload)
  }

  updateTeam(id: number, team: Team): Observable<Team> {
    const { id: _ignored, ...payload } = team
    return this.httpClient.put<Team>(`${this.apiUrl}/${id}`, payload)
  }
}
