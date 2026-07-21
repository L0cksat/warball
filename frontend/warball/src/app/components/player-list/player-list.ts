import { Component, inject, OnInit } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Team } from '../../models/team/team';
import { Player } from '../../models/player/player';
import { TeamService } from '../../services/team';
import { PlayerService } from '../../services/player';

@Component({
  selector: 'app-player-list',
  imports: [AsyncPipe, RouterLink],
  templateUrl: './player-list.html',
  styleUrl: './player-list.css',
})
export class PlayerListComponent {
  teamId: number | null = null
  players$!: Observable<Player[]>
  team$!: Observable<Team>
  
  private playerService = inject(PlayerService)
  private teamService = inject(TeamService)
  private route = inject(ActivatedRoute)

  ngOnInit(): void{
    const idParam = this.route.snapshot.paramMap.get('id')
    if(idParam) {
      this.teamId = +idParam
      this.team$ = this.teamService.getTeam(this.teamId)
      this.players$ = this.playerService.getRoster(this.teamId)
      }
    }
}
