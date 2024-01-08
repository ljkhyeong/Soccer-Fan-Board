package jkproject.soccer.api.controller.team;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jkproject.soccer.api.common.dto.response.Response;
import jkproject.soccer.api.dto.team.response.TeamNameResponseDto;
import jkproject.soccer.domain.service.team.TeamService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TeamApiController {

	private final TeamService teamService;

	@GetMapping("/{teamCode}")
	public Response<TeamNameResponseDto> getTeamName(@PathVariable String teamCode) {
		TeamNameResponseDto responseDto = teamService.getTeamName(teamCode);
		return Response.success(responseDto);
	}
	
}
