package com.warball.backend.services;

import java.util.Optional;

import com.warball.backend.entities.Player;

public interface PlayerService {
    Player createPlayer(Player player);
    Optional<Player> updatePlayer(Long id, Player player);
}
