import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Player } from '../models/player/player';


@Injectable({
  providedIn: 'root',
})
export class PlayerService {
  private readonly apiUrl = "http://localhost:8080/api/v1"

  constructor(private httpClient: HttpClient){}

  getRoster(teamId: number): Observable<Player[]>{
    return this.httpClient.get<Player[]>(`${this.apiUrl}/teams/${teamId}/players`)
  }

  getPlayer(id: number): Observable<Player>{
    return this.httpClient.get<Player>(`${this.apiUrl}/players/${id}`)
  }

  createPlayer(player: Player): Observable<Player>{
    const { id, ...payload } = player
    return this.httpClient.post<Player>(`${this.apiUrl}/players`, payload)
  }

  updatePlayer(id: number, player: Player): Observable<Player>{
    const { id: _ignored, ...payload } = player
    return this.httpClient.put<Player>(`${this.apiUrl}/players/${id}`, payload)
  }
}
