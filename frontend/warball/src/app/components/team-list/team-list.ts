import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TeamService } from '../../services/team';

@Component({
  selector: 'app-team-list',
  imports: [RouterLink, AsyncPipe],
  templateUrl: './team-list.html',
  styleUrl: './team-list.css',
})
export class TeamListComponent {
  private teamService = inject(TeamService);
  teams$ = this.teamService.getTeams();
}
