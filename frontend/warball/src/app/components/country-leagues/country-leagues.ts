import { Component, inject } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { LeagueService } from '../../services/league';
import { Observable } from 'rxjs';
import { League } from '../../models/league/league';

@Component({
  selector: 'app-country-leagues',
  imports: [AsyncPipe, RouterLink],
  templateUrl: './country-leagues.html',
  styleUrl: './country-leagues.css',
})
export class CountryLeaguesComponent {
  leagues$!: Observable<League[]>

  private leagueService = inject(LeagueService)
  private route = inject(ActivatedRoute)

  ngOnInit(): void {
    const countryParam = this.route.snapshot.paramMap.get('country');
    if(countryParam) {
      this.leagues$ = this.leagueService.getLeaguesByCountry(countryParam)
    }
  }
}
