package com.warball.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data 
@Entity 
@Table(name = "leagues")
public class League {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

    @Column (name = "LEAGUE_NAME")
    private String leagueName;

    @Column (name = "LEAGUE_COUNTRY")
    private String leagueCountry;

    @Column (name = "PROMOTE_TO")
    private Integer promoteTo;

    @Column (name = "RELEGATE_TO")
    private Integer relegateTo;
 
}
