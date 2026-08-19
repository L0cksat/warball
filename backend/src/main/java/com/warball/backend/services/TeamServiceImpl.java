package com.warball.backend.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.warball.backend.entities.Team;
import com.warball.backend.repositories.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepo;

    public TeamServiceImpl(TeamRepository teamRepo){
        this.teamRepo = teamRepo;
    }

    @Override
    public Team createTeam(Team team) {
       return teamRepo.save(team);
      
    }

    @Override
    public Optional<Team> updateTeam(Long id, Team team) {
        return teamRepo.findById(id)
                .map(existing -> {
                    existing.setTeamName(team.getTeamName());
                    existing.setCountry(team.getCountry());
                    existing.setProvince(team.getProvince());
                    existing.setHomePrimaryHex(team.getHomePrimaryHex());
                    existing.setHomeSecondaryHex(team.getHomeSecondaryHex());
                    existing.setAwayPrimaryHex(team.getAwayPrimaryHex());
                    existing.setAwaySecondaryHex(team.getAwaySecondaryHex());
                    return teamRepo.save(existing);
                });
    }
}
