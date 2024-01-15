package jkproject.soccer.api.dto.team.response;

import jkproject.soccer.domain.entity.team.Team;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamResponseDto {
	String name;
	String imagePath;

	public static TeamResponseDto from(Team team) {
		return TeamResponseDto.builder()
			.name(team.getName())
			.imagePath(team.getImagePath())
			.build();
	}
}
