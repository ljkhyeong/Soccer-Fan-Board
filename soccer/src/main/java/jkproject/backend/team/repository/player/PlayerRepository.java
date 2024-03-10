package jkproject.backend.team.repository.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.backend.team.data.entity.Team;
import jkproject.backend.team.data.entity.player.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

	Page<Player> findAllByTeam(Team team, Pageable pageable);
}
