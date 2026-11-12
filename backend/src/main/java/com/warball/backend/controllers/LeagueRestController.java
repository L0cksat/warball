package com.warball.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warball.backend.entities.League;
import com.warball.backend.repositories.LeagueRepository;
import com.warball.backend.services.LeagueService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@CrossOrigin(origins = "http://localhost:4200")
@RestController 
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

@GetMapping("/leagues/country/{leagueCountry}")
public ResponseEntity <List<League>> findLeaguesByCountry(@PathVariable String leagueCountry){
return ResponseEntity.ok(leagueService.findByLeagueCountry(leagueCountry));
}

@PostMapping("/leagues")
public ResponseEntity<League> createLeague(@RequestBody League league) {
    return new ResponseEntity<>(leagueService.createLeague(league), HttpStatus.CREATED);
}

@PutMapping("/leagues/{id}")
public ResponseEntity<?> updateLeague(@PathVariable Long id, @RequestBody League league) {
    return leagueService.updateLeague(id, league)
    .map(updated -> ResponseEntity.ok(updated))
    .orElse(ResponseEntity.notFound().build());
}

}


