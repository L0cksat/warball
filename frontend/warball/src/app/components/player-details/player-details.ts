import { Component, inject } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { RouterLink, ActivatedRoute, Router } from '@angular/router';
import { PlayerService } from '../../services/player';
import { TeamService } from '../../services/team';
import { Observable } from 'rxjs';
import { Player } from '../../models/player/player';
import { Team } from '../../models/team/team';

@Component({
  selector: 'app-player-details',
  imports: [AsyncPipe, RouterLink],
  templateUrl: './player-details.html',
  styleUrl: './player-details.css',
})
export class PlayerDetailsComponent {
  playerId: number | null = null
  teamId: number | null = null
  player$!: Observable<Player>
  teams$!: Observable<Team>

  private playerService = inject(PlayerService)
  private teamService = inject(TeamService)
  private route = inject(ActivatedRoute) 

  ngOnInit(): void{
    const idParam = this.route.snapshot.paramMap.get('id')
    const idPlayer = this.route.snapshot.paramMap.get('playerId')
    if(idParam){
      this.teamId = +idParam
      this.teams$ = this.teamService.getTeam(this.teamId)
    }
    if(idPlayer){
      this.playerId = +idPlayer
      this.player$ = this.playerService.getPlayer(this.playerId)
    }
      
  }
}
