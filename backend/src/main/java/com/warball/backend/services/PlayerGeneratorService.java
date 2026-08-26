package com.warball.backend.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.warball.backend.entities.Player;
import com.warball.backend.entities.Team;
import com.warball.backend.repositories.PlayerRepository;
import com.warball.backend.repositories.TeamRepository;

@Service
public class PlayerGeneratorService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final Random random = new Random();

    private static final String[] FIRST_NAMES = {"Richard", "Mira", "Emily", "Andy", "Peter"};

    private static final String[] LAST_NAMES = {"Jones", "Jameson", "Hampton", "Lecroux", "Munez", "Ogdile"};

    private static final String[] HOBBIES = {"fishing", "sewing", "gambling", "exercise", "sleeping", "drinking mead", "horse riding", "setting things on fire", "tinkering with machines", "eating"};


    public PlayerGeneratorService(PlayerRepository playerRepository,
    TeamRepository teamRepository){
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    private String pick(String[] options){
        return options[random.nextInt(options.length)];
    }

    private Integer pickFreeShirtNumber(Long teamId){
        List<Player> roster = playerRepository.findByTeam_Id(teamId);
        Set<Integer> taken = new HashSet<>();
        for (Player p : roster){
            if (p.getShirtNumber() != null){
                taken.add(p.getShirtNumber());
            }
        }
        List<Integer> free = new ArrayList<>();
        for (int n = 1; n <= 99; n++){
            if(!taken.contains(n)){
                free.add(n);
            }
        }
        if (free.size() <= 0){
            return null;
        }
        return free.get(random.nextInt(free.size()));

    }

    public Player generateForTeam(Long teamId){

       Team team = teamRepository.findById(teamId).orElseThrow();

       Player player = new Player();
       player.setTeam(team);

        player.setPlayerId("p_" + (random.nextInt(9000) + 1000) + "_hum");
        player.setFirstName(pick(FIRST_NAMES));
        player.setLastName(pick(LAST_NAMES));
        player.setAge(18 + random.nextInt(23));
        player.setRace("Human");
        player.setSubRace(null);
        player.setPosition("Defender");
        player.setHobbies(List.of(pick(HOBBIES)));
        player.setShirtNumber(pickFreeShirtNumber(teamId));
        player.setTraits(null);
        player.setAttributes(null);
        player.setStatusEffects(null);

       return playerRepository.save(player);
       

    }
}
