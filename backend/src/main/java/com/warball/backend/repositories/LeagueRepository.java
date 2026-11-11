package com.warball.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warball.backend.entities.League;

public interface LeagueRepository extends JpaRepository<League, Long>{
    
}
