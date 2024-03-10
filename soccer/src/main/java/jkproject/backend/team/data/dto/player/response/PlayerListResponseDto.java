package jkproject.backend.team.data.dto.player.response;

import jkproject.backend.team.data.entity.player.Player;
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
