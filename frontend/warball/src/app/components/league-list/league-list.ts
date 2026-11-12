import { Component, inject, signal } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { map } from 'rxjs';
import { RouterLink } from '@angular/router';
import { LeagueService } from '../../services/league';
import { countryFlagSrc } from '../../utils/country-flags';


@Component({
  selector: 'app-league-list',
  imports: [RouterLink, AsyncPipe],
  templateUrl: './league-list.html',
  styleUrl: './league-list.css',
})
export class LeagueListComponent {
  private leagueService = inject(LeagueService);
  leagues$ = this.leagueService.getLeagues();

 countries$ = this.leagues$.pipe(
  map((leagues) => {
    const groupedLeagues = new Map<string, Set<string>>()

    leagues.forEach((league) => {
      if (!groupedLeagues.has(league.leagueCountry)) {
        groupedLeagues.set(league.leagueCountry, new Set<string>())
      }
      groupedLeagues.get(league.leagueCountry)!.add(league.leagueName)
    })
    return Array.from(groupedLeagues, ([leagueCountry, leaguesSet]) => ({
      leagueCountry: leagueCountry,
      leagues: Array.from(leaguesSet)
    }))
  })
 ) 

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
