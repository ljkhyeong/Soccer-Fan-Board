package jkproject.backend.team.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TEAM_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id")
	private Long teamId;
	@Column(unique = true, nullable = false)
	private String code;
	@Column(unique = true, nullable = false)
	private String name;
	private String imagePath;

}
