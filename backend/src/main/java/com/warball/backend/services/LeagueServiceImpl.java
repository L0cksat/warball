package com.warball.backend.services;

import java.util.List;
import java.util.Optional;

import com.warball.backend.entities.League;
import com.warball.backend.repositories.LeagueRepository;

public class LeagueServiceImpl implements LeagueService{

    private final LeagueRepository leagueRepo;
    private final LeagueService leagueService;

    public LeagueServiceImpl(LeagueRepository leagueRepo, LeagueService leagueService){
        this.leagueRepo = leagueRepo;
        this.leagueService = leagueService;
    }

    @Override
    public League createLeague(League league) {
        return leagueRepo.save(league);
    }

    @Override
    public Optional<League> updateLeague(Long id, League league) {
        return leagueRepo.findById(id).map(existing -> {
            existing.setLeagueName(league.getLeagueName());
            existing.setLeagueCountry(league.getLeagueCountry());
            existing.setPromoteTo(league.getPromoteTo());
            existing.setRelegateTo(league.getRelegateTo());
            return leagueRepo.save(existing);
        });
    }

    @Override
    public List<League> findLeagueByCountry(String leagueCountry) {
        return leagueService.findLeagueByCountry(leagueCountry);
    } 
}
