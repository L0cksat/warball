import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { League } from '../models/league/league';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class LeagueService {
  private readonly apiUrl = "http://localhost:8080/api/v1/leagues"

  constructor(private httpClient: HttpClient) {}

  getLeagues(): Observable<League[]>{
    return this.httpClient.get<League[]>(this.apiUrl)
  }

  getLeague(id: number): Observable<League> {
    return this.httpClient.get<League>(`${this.apiUrl}/${id}`)
  }

  getLeaguesByCountry(country: string): Observable<League[]> {
    return this.httpClient.get<League[]>(`${this.apiUrl}/country/${encodeURIComponent(country)}`)
  }

  createLeague(league: League): Observable<League> {
    const { id, ...payload } = league
    return this.httpClient.post<League>(this.apiUrl, payload)
  }

  updateLeague(id: number, league: League): Observable<League>{
    const {  id: _ignored, ...payload } = league
    return this.httpClient.put<League>(`${this.apiUrl}/${id}`, payload)
  }

}