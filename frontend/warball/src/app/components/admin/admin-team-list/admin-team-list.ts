import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { TeamService } from '../../../services/team';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-admin-team-list',
  imports: [AsyncPipe, RouterLink],
  templateUrl: './admin-team-list.html',
  styleUrl: './admin-team-list.css',
})
export class AdminTeamListComponent {

  private teamService = inject(TeamService)
  teams$ = this.teamService.getTeams()
}
