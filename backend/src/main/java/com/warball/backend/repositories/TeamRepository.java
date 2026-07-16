package com.warball.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warball.backend.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{
    
    
}
