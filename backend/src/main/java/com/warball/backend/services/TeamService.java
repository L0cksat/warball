package com.warball.backend.services;

import java.util.Optional;

import com.warball.backend.entities.Team;

public interface TeamService {
    Team createTeam(Team team);
    Optional<Team> updateTeam(Long id, Team team);
}
