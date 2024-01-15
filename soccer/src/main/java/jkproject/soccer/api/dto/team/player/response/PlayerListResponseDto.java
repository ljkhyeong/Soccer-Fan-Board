package jkproject.soccer.api.dto.team.player.response;

import jkproject.soccer.domain.entity.team.player.Player;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlayerListResponseDto {
	private Long playerId;
	private String infoLink;
	private String imagePath;
	private String name;
	private String position;
	private String country;

	public static PlayerListResponseDto from(Player player) {
		return PlayerListResponseDto.builder()
			.playerId(player.getPlayerId())
			.infoLink(player.getInfoLink())
			.imagePath(player.getImagePath())
			.name(player.getName())
			.position(player.getPosition())
			.country(player.getCountry())
			.build();
	}
}
