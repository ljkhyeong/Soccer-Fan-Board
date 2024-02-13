package jkproject.soccer.team.repository.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.team.data.entity.Team;
import jkproject.soccer.team.data.entity.player.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

	Page<Player> findAllByTeam(Team team, Pageable pageable);
}
