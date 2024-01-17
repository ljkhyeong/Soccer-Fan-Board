package jkproject.soccer.team.service.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.soccer.player.repository.PlayerRepository;
import jkproject.soccer.team.repository.TeamRepository;
import jkproject.soccer.team.data.dto.player.response.PlayerListResponseDto;
import jkproject.soccer.team.data.entity.Team;
import jkproject.soccer.team.data.entity.player.Player;
import jkproject.soccer.common.exception.ApplicationException;
import jkproject.soccer.common.exception.enums.ErrorCode;
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
