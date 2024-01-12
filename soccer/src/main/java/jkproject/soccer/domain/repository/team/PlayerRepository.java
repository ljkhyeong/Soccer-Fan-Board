package jkproject.soccer.domain.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.domain.entity.team.player.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
