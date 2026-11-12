package com.warball.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warball.backend.entities.League;

public interface LeagueRepository extends JpaRepository<League, Long>{
   List<League> findByLeagueCountry(String leagueCountry); 
}
