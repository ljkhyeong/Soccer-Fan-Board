package jkproject.soccer.domain.repository.team;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.domain.entity.team.Team;
import jkproject.soccer.domain.entity.team.player.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

	Page<Player> findAllByTeam(Team team, Pageable pageable);
}
