package jkproject.soccer.api.dto.team.response;

import jkproject.soccer.domain.entity.team.Team;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamNameResponseDto {
	String name;

	public static TeamNameResponseDto from(Team team) {
		return TeamNameResponseDto.builder()
			.name(team.getName())
			.build();
	}
}
