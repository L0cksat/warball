package com.warball.backend.controllers;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.warball.backend.repositories.TeamRepository;
import com.warball.backend.services.TeamService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import  org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.warball.backend.entities.Team;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class TeamRestController {
    
    private final TeamRepository teamRepo;
    private final TeamService teamService;

    public TeamRestController(TeamRepository teamRepo, TeamService teamService){
        this.teamRepo = teamRepo;
        this.teamService = teamService;
    }


    @GetMapping("/teams")
    public ResponseEntity<List<Team>> allTeams() {
        List<Team> listTeams = teamRepo.findAll();
        return ResponseEntity.ok(listTeams);
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<?> findOneTeam(@PathVariable Long id){
        return teamRepo.findById(id)
                .map(team -> ResponseEntity.ok(team))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/teams")
    public ResponseEntity<Team> createTeam(@RequestBody Team team)  {
        return new ResponseEntity<>(teamService.createTeam(team), HttpStatus.CREATED);
    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<?> updateTeam(@PathVariable Long id, @RequestBody Team team){
        return teamService.updateTeam(id, team)
                .map(updated-> ResponseEntity.ok(updated))
                .orElse(ResponseEntity.notFound().build());
    }
  
} 

