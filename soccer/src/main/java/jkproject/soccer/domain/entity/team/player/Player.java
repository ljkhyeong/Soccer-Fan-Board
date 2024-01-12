package jkproject.soccer.domain.entity.team.player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jkproject.soccer.domain.entity.team.Team;
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
	@Column(nullable = false)
	private String name;
	private String position;
	@Column(nullable = false)
	private String nationality;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	@Builder
	public Player(String name, String position, String nationality, Team team) {
		this.name = name;
		this.position = position;
		this.nationality = nationality;
		this.team = team;
	}
}
