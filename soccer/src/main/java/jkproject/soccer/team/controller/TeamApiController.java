package jkproject.soccer.team.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jkproject.soccer.common.data.dto.response.Response;
import jkproject.soccer.team.service.TeamService;
import jkproject.soccer.team.data.dto.response.TeamResponseDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TeamApiController {

	private final TeamService teamService;

	@GetMapping("/{teamCode}")
	public Response<TeamResponseDto> getTeamName(@PathVariable String teamCode) {
		TeamResponseDto responseDto = teamService.getTeamName(teamCode);
		return Response.success(responseDto);
	}

}
