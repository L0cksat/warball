package com.warball.backend.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warball.backend.embeddables.MysticalAttributes;
import com.warball.backend.embeddables.PhysicalAttributes;
import com.warball.backend.embeddables.PlayerAttributes;
import com.warball.backend.embeddables.TechnicalAttributes;
import com.warball.backend.embeddables.VibeAttributes;
import com.warball.backend.entities.Player;
import com.warball.backend.entities.Team;
import com.warball.backend.repositories.PlayerRepository;
import com.warball.backend.repositories.TeamRepository;

import jakarta.annotation.PostConstruct;

@Service
public class PlayerGeneratorService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    private List<RaceEntry> races;
    private Map<String, List<String>> subraces;
    private Map<String, SexedNames> firstNames;
    private Map<String, List<String>> lastNames;
    private Hobbies hobbyCatalog;
    private List<String> positions;


    public PlayerGeneratorService(PlayerRepository playerRepository,
            TeamRepository teamRepository,
            ObjectMapper objectMapper) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    private void loadCatalogs() throws IOException {
        // Load the four generator/*.json files into the fields above.
        // Pattern: ClassPathResource + objectMapper.readValue(inputStream, type)
        // races      -> new TypeReference<List<RaceEntry>>() {}
        // subraces   -> new TypeReference<Map<String, List<String>>>() {}
        // firstNames -> new TypeReference<Map<String, SexedNames>>() {}
        // lastNames  -> new TypeReference<Map<String, List<String>>>() {}
        ClassPathResource resource = new ClassPathResource("generator/races.json");
        try (InputStream in = resource.getInputStream()){
            races = objectMapper.readValue(in, new TypeReference<List<RaceEntry>>() {});
        }

        resource = new ClassPathResource("generator/subraces.json");
        try (InputStream in = resource.getInputStream()){
            subraces = objectMapper.readValue(in, new TypeReference<Map<String, List<String>>>() {});
        }

         resource = new ClassPathResource("generator/first-names.json");
        try (InputStream in = resource.getInputStream()){
            firstNames = objectMapper.readValue(in, new TypeReference<Map<String, SexedNames>>(){});
        }

         resource = new ClassPathResource("generator/last-names.json");
        try (InputStream in = resource.getInputStream()){
            lastNames = objectMapper.readValue(in, new TypeReference<Map<String, List<String>>>() {});
        }

        resource = new ClassPathResource("generator/hobbies.json");
        try (InputStream in = resource.getInputStream()){
            hobbyCatalog = objectMapper.readValue(in, Hobbies.class);
        }

        resource = new ClassPathResource("generator/positions.json");
        try (InputStream in = resource.getInputStream()){
            positions = objectMapper.readValue(in, new TypeReference<List<String>>() {});
        }

    }

    public record RaceEntry(String key, String label) {}

    public record SexedNames(List<String> male, List<String> female) {}

    public record Hobbies(List<String> verbs, List<String> hobbies) {}


    private String pick(List<String> options){
        if (options == null || options.isEmpty()){
            return null;
        }
        return options.get(random.nextInt(options.size()));
    }

    private Double roll(){
        return random.nextInt(31) /10.0;
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

        RaceEntry race = (races.get(random.nextInt(races.size())));
        player.setPlayerId("p_" + (random.nextInt(9000) + 1000) + "_" + race.key().substring(0, 3));
        player.setRace(race.label());
        player.setSubRace(pick(subraces.get(race.key())));
        String sex = random.nextBoolean() ? "Male" : "Female";
        player.setSex(sex);
        SexedNames names = firstNames.get(race.key());
        if(names == null){
            names = firstNames.get("generic");
        }
        if ("Male".equals(sex)) {
            player.setFirstName(pick(names.male()));
        } else {
            player.setFirstName(pick(names.female()));
        }
        String lastName = pick(lastNames.get(race.key()));
        if (lastName == null){
            lastName = pick(lastNames.get("generic"));
        }
        player.setLastName(lastName);
        player.setAge(18 + random.nextInt(23));
        player.setPosition(pick(positions));

        String verb = pick(hobbyCatalog.verbs());
        String hobby = pick(hobbyCatalog.hobbies());
        player.setHobbies(List.of(verb + " " + hobby));

        player.setShirtNumber(pickFreeShirtNumber(teamId));
        player.setTraits(null);

        PhysicalAttributes physical = new PhysicalAttributes();
        physical.setHeft(roll());
        physical.setBulwark(roll());
        physical.setParanoia(roll());
        physical.setSlipperiness(roll());
        physical.setStickyFingers(roll());

        TechnicalAttributes technical = new TechnicalAttributes();
        technical.setCastleThirst(roll());
        technical.setMagnetism(roll());
        technical.setSiege(roll());
        technical.setThreading(roll());
        technical.setTrajectory(roll());

        MysticalAttributes mystical = new MysticalAttributes();
        mystical.setArcaneSpark(roll());
        mystical.setRestraint(roll());
        mystical.setWard(roll());

        VibeAttributes vibe = new VibeAttributes();
        vibe.setDramaticFlair(roll());
        vibe.setMoxie(roll());
        vibe.setTrashTalk(roll());

        PlayerAttributes attributes = new PlayerAttributes();
        attributes.setPhysicalAttributes(physical);
        attributes.setTechnicalAttributes(technical);
        attributes.setMysticalAttributes(mystical);
        attributes.setVibeAttributes(vibe);

        player.setAttributes(attributes);
        player.setStatusEffects(null);

       return playerRepository.save(player);
       
    }
}
