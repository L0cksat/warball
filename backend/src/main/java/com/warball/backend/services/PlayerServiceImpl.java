package com.warball.backend.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.warball.backend.entities.Player;
import com.warball.backend.repositories.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService{
    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Optional<Player> updatePlayer(Long id, Player player) {
        return playerRepository.findById(id)
        .map(existing -> {
          existing.setPlayerId(player.getPlayerId());
          existing.setFirstName(player.getFirstName());
          existing.setLastName(player.getLastName());
          existing.setTeam(player.getTeam());
          existing.setRace(player.getRace());
          existing.setSubRace(player.getSubRace());
          existing.setPosition(player.getPosition());
          existing.setAttributes(player.getAttributes());
          existing.setTraits(player.getTraits());
          existing.setStatusEffects(player.getStatusEffects());
          return playerRepository.save(existing);
        });
    }
    
}
