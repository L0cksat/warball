package com.warball.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warball.backend.entities.Player;
import com.warball.backend.entities.Team;
import com.warball.backend.repositories.PlayerRepository;
import com.warball.backend.repositories.TeamRepository;
import com.warball.backend.services.PlayerService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class PlayerRestController {
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;
    private final TeamRepository teamRepository;

    public PlayerRestController(PlayerRepository playerRepository, TeamRepository teamRepository, PlayerService playerService){
        this.playerRepository = playerRepository;
        this.playerService = playerService;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<?> getOnePlayer(@PathVariable Long id){
        return playerRepository.findById(id)
                .map(player -> ResponseEntity.ok(player))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createNewPlayer(@RequestBody Player player) {
        Long teamId = player.getTeam().getId();
        Team team = teamRepository.findById(teamId).orElseThrow();
        player.setTeam(team);
       return new ResponseEntity<>(playerService.createPlayer(player), HttpStatus.CREATED);
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable Long id, @RequestBody Player player){
        Long teamId = player.getTeam().getId();
        Team team = teamRepository.findById(teamId).orElseThrow();
        player.setTeam(team);
        return playerService.updatePlayer(id, player)
                .map(updated -> ResponseEntity.ok(updated))
                .orElse(ResponseEntity.notFound().build());
    }
    
    
}
