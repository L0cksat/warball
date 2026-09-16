import { AsyncPipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TeamService } from '../../services/team';
import { countryFlagSrc } from '../../utils/country-flags';

@Component({
  selector: 'app-team-list',
  imports: [RouterLink, AsyncPipe],
  templateUrl: './team-list.html',
  styleUrl: './team-list.css',
})
export class TeamListComponent {
  private teamService = inject(TeamService);
  teams$ = this.teamService.getTeams();

  private hiddenFlags = signal<ReadonlySet<string>>(new Set());

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
