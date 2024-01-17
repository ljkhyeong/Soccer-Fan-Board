package jkproject.soccer.team.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.team.repository.TeamRepository;
import jkproject.soccer.team.data.dto.response.TeamResponseDto;
import jkproject.soccer.team.data.entity.Team;
import jkproject.soccer.common.exception.ApplicationException;
import jkproject.soccer.common.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

	private final TeamRepository teamRepository;

	public TeamResponseDto getTeamName(String teamCode) {
		Team team = teamRepository.findByCode(teamCode)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_TEAM_CODE));

		return TeamResponseDto.from(team);
	}

}
