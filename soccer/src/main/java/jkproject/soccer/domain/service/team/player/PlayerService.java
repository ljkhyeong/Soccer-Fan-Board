package jkproject.soccer.domain.service.team.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.api.dto.team.player.response.PlayerListResponseDto;
import jkproject.soccer.domain.entity.team.Team;
import jkproject.soccer.domain.entity.team.player.Player;
import jkproject.soccer.domain.repository.team.PlayerRepository;
import jkproject.soccer.domain.repository.team.TeamRepository;
import jkproject.soccer.web.common.exception.ApplicationException;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerService {

	private final TeamRepository teamRepository;
	private final PlayerRepository playerRepository;

	public Page<PlayerListResponseDto> lookupAllPlayers(String teamCode, Pageable pageable) {
		Team team = teamRepository.findByCode(teamCode)
			.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_TEAM_CODE));
		Page<Player> players = playerRepository.findAllByTeam(team, pageable);

		return players.map(PlayerListResponseDto::from);
	}
}
