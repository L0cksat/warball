package com.warball.backend.entities;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.warball.backend.embeddables.PlayerAttributes;
import com.warball.backend.embeddables.StatusEffect;
import com.warball.backend.embeddables.Trait;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PLAYER_ID")
    private String playerId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "RACE")
    private String race;

    @Column(name = "SUB_RACE")
    private String subRace;

    @Column(name = "PLAYER_POSITION")
    private String position;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "ATTRIBUTES")
    private PlayerAttributes attributes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "TRAITS")
    private List<Trait> traits;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "STATUS_EFFECTS")
    private List<StatusEffect> statusEffects;
    
}
