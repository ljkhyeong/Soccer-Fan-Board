package jkproject.soccer.team.data.entity.player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jkproject.soccer.team.data.entity.Team;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PLAYER_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "player_id")
	private Long playerId;
	private String infoLink;
	private String imagePath;
	@Column(nullable = false)
	private String name;
	private String position;
	@Column(nullable = false)
	private String country;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	@Builder
	public Player(String infoLink, String imagePath, String name, String position, String country, Team team) {
		this.infoLink = infoLink;
		this.imagePath = imagePath;
		this.name = name;
		this.position = position;
		this.country = country;
		this.team = team;
	}
}
