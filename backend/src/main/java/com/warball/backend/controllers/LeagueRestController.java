package com.warball.backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.warball.backend.entities.League;
import com.warball.backend.repositories.LeagueRepository;
import com.warball.backend.services.LeagueService;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping ("/api/v1")

public class LeagueRestController {
    private final LeagueService leagueService;
    private final LeagueRepository leagueRepo;

    public LeagueRestController(LeagueService leagueService, LeagueRepository leagueRepo) {
    this.leagueService = leagueService;
    this.leagueRepo = leagueRepo;
 }

 @GetMapping ("/leagues")
 public ResponseEntity<List<League>> AllLeagues() {
    List<League> listLeagues = leagueRepo.findAll();
    return ResponseEntity.ok(listLeagues); 
}

@GetMapping ("/leagues/{id}")
public ResponseEntity<?> findLeagueById(@PathVariable Long id) {
    return leagueRepo.findById(id)
    .map(league -> ResponseEntity.ok(league))
    .orElse(ResponseEntity.notFound().build());
}


}