package com.warball.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warball.backend.entities.Player;

public interface PlayerRepository extends JpaRepository<Player, Long>{
    List<Player> findByTeam_Id(Long teamId);

}

