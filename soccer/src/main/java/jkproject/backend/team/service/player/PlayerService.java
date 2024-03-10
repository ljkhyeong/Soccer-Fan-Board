package jkproject.backend.team.service.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jkproject.backend.common.exception.ApplicationException;
import jkproject.backend.common.exception.enums.ErrorCode;
import jkproject.backend.team.data.dto.player.response.PlayerListResponseDto;
import jkproject.backend.team.data.entity.Team;
import jkproject.backend.team.data.entity.player.Player;
import jkproject.backend.team.repository.TeamRepository;
import jkproject.backend.team.repository.player.PlayerRepository;
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
