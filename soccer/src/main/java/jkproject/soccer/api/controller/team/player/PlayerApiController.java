package jkproject.soccer.api.controller.team.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.api.dto.team.player.response.PlayerListResponseDto;
import jkproject.soccer.domain.service.team.player.PlayerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PlayerApiController {

	private final PlayerService playerService;

	@GetMapping("/{teamCode}/players")
	public Response<Page<PlayerListResponseDto>> lookupAllPlayers(@PathVariable String teamCode,
		@PageableDefault(sort = "playerId", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<PlayerListResponseDto> playerDtoList = playerService.lookupAllPlayers(teamCode, pageable);
		return Response.success(playerDtoList);
	}
}
