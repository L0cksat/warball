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
@Table(name = "teams")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TEAM_NAME")
	private String teamName;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "PROVINCE")
	private String province;

	@Column(name = "HOME_PRIMARY_HEX")
	private String homePrimaryHex;

	@Column(name = "HOME_SECONDARY_HEX")
	private String homeSecondaryHex;

	@Column(name = "AWAY_PRIMARY_HEX")
	private String awayPrimaryHex;

	@Column(name = "AWAY_SECONDARY_HEX")
	private String awaySecondaryHex;

}
