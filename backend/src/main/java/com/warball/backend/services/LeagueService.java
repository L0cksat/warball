package com.warball.backend.services;

import java.util.List;
import java.util.Optional;

import com.warball.backend.entities.League;

public interface LeagueService {
    League createLeague(League league);
    Optional<League> updateLeague(Long id, League league);
    List<League> findByLeagueCountry(String leagueCountry); 
}
