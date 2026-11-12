package com.warball.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.warball.backend.entities.League;
import com.warball.backend.repositories.LeagueRepository;

@Service 
public class LeagueServiceImpl implements LeagueService{

    private final LeagueRepository leagueRepo;
    

    public LeagueServiceImpl(LeagueRepository leagueRepo){
        this.leagueRepo = leagueRepo;
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
    public List<League> findByLeagueCountry(String leagueCountry) {
        return leagueRepo.findByLeagueCountry(leagueCountry);
    }
}
