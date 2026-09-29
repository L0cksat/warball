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
import com.warball.backend.embeddables.Trait;
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
    private List<Trait> traitCatalog;
    private List<BirthplaceEntry> birthplaces;


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

        resource = new ClassPathResource("generator/traits.json");
        try (InputStream in = resource.getInputStream()){
            traitCatalog = objectMapper.readValue(in, new TypeReference<List<Trait>>() {});
        }

        resource = new ClassPathResource("generator/birthplaces.json");
        try (InputStream in = resource.getInputStream()){
            birthplaces = objectMapper.readValue(in, new TypeReference<List<BirthplaceEntry>>() {});
        }

    }

    public record RaceEntry(String key, String label) {}

    public record SexedNames(List<String> male, List<String> female) {}

    public record Hobbies(List<String> verbs, List<String> hobbies) {}

    public record BirthplaceEntry(String place, String country) {}


    private String pick(List<String> options){
        if (options == null || options.isEmpty()){
            return null;
        }
        return options.get(random.nextInt(options.size()));
    }

    private Double roll(){
        return random.nextInt(61) /10.0 - 3.0;
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

    private double clamp(double value) {
        double capped = Math.max(-3.0, Math.min(3.0, value)) * 10; 
        return Math.round(capped) / 10.0;
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
        if (birthplaces != null && !birthplaces.isEmpty()) {
            BirthplaceEntry birth = birthplaces.get(random.nextInt(birthplaces.size()));
            player.setBirthplace(birth.place());
            player.setBirthCountry(birth.country());
        }

        String verb = pick(hobbyCatalog.verbs());
        String hobby = pick(hobbyCatalog.hobbies());
        player.setHobbies(List.of(verb + " " + hobby));

        player.setShirtNumber(pickFreeShirtNumber(teamId));
        

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

        List<Trait> picked = new ArrayList<>();
        if (traitCatalog != null && !traitCatalog.isEmpty()) {
            List<Trait> pool = new ArrayList<>(traitCatalog);
            int count = 1 + random.nextInt(2);
            if (count > pool.size()) {
                count = pool.size();
            }
            for (int i = 0; i < count; i++){
                int index = random.nextInt(pool.size());
                picked.add(pool.remove(index));
            }
        }
        for (Trait trait : picked ) {
            if (trait.getModifiers() == null){
                continue;
            }
            for (Map.Entry<String, Double> entry : trait.getModifiers().entrySet()){
                String key = entry.getKey();
                double delta = entry.getValue();
                switch(key) {
                    case "heft" -> physical.setHeft(physical.getHeft() + delta);
                    case "bulwark" -> physical.setBulwark(physical.getBulwark() + delta);
                    case "paranoia" -> physical.setParanoia(physical.getParanoia() + delta);
                    case "slipperiness" -> physical.setSlipperiness(physical.getSlipperiness() + delta);
                    case "stickyFingers" -> physical.setStickyFingers(physical.getStickyFingers() + delta);
                    case "castleThirst" -> technical.setCastleThirst(technical.getCastleThirst() + delta);
                    case "magnetism" -> technical.setMagnetism(technical.getMagnetism() + delta);
                    case "siege" -> technical.setSiege(technical.getSiege() + delta);
                    case "threading" -> technical.setThreading(technical.getThreading() + delta);
                    case "trajectory" -> technical.setTrajectory(technical.getTrajectory() + delta);
                    case "arcaneSpark" -> mystical.setArcaneSpark(mystical.getArcaneSpark() + delta);
                    case "restraint" -> mystical.setRestraint(mystical.getRestraint() + delta);
                    case "ward" -> mystical.setWard(mystical.getWard() + delta);
                    case "dramaticFlair" -> vibe.setDramaticFlair(vibe.getDramaticFlair() + delta);
                    case "moxie" -> vibe.setMoxie(vibe.getMoxie() + delta);
                    case "trashTalk" -> vibe.setTrashTalk(vibe.getTrashTalk() + delta);
                    default -> { }
                }
            }
        }

        physical.setHeft(clamp(physical.getHeft()));
        physical.setBulwark(clamp(physical.getBulwark()));
        physical.setParanoia(clamp(physical.getParanoia()));
        physical.setSlipperiness(clamp(physical.getSlipperiness()));
        physical.setStickyFingers(clamp(physical.getStickyFingers()));
        technical.setCastleThirst(clamp(technical.getCastleThirst()));
        technical.setMagnetism(clamp(technical.getMagnetism()));
        technical.setSiege(clamp(technical.getSiege()));
        technical.setThreading(clamp(technical.getThreading()));
        technical.setTrajectory(clamp(technical.getTrajectory()));
        mystical.setArcaneSpark(clamp(mystical.getArcaneSpark()));
        mystical.setRestraint(clamp(mystical.getRestraint()));
        mystical.setWard(clamp(mystical.getWard()));
        vibe.setDramaticFlair(clamp(vibe.getDramaticFlair()));
        vibe.setMoxie(clamp(vibe.getMoxie()));
        vibe.setTrashTalk(clamp(vibe.getTrashTalk()));

        player.setTraits(picked);
        player.setStatusEffects(List.of());

        player.setAttributes(attributes);

       return playerRepository.save(player);
       
    }
}
