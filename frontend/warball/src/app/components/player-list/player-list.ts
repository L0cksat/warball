import { Component, inject, signal } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Team } from '../../models/team/team';
import { Player } from '../../models/player/player';
import { TeamService } from '../../services/team';
import { PlayerService } from '../../services/player';
import { countryFlagSrc } from '../../utils/country-flags';

@Component({
  selector: 'app-player-list',
  imports: [AsyncPipe, RouterLink],
  templateUrl: './player-list.html',
  styleUrl: './player-list.css',
})
export class PlayerListComponent {
  teamId: number | null = null;
  players$!: Observable<Player[]>;
  team$!: Observable<Team>;

  private playerService = inject(PlayerService);
  private teamService = inject(TeamService);
  private route = inject(ActivatedRoute);
  private hiddenFlags = signal<ReadonlySet<string>>(new Set());

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.teamId = +idParam;
      this.team$ = this.teamService.getTeam(this.teamId);
      this.players$ = this.playerService.getRoster(this.teamId);
    }
  }

  flagSrc(country: string | null | undefined): string | null {
    if (!country || this.hiddenFlags().has(country)) {
      return null;
    }
    return countryFlagSrc(country);
  }

  hideFlag(country: string): void {
    const next = new Set(this.hiddenFlags());
    next.add(country);
    this.hiddenFlags.set(next);
  }
}
