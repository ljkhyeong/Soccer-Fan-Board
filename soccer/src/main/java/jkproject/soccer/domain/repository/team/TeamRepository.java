package jkproject.soccer.domain.repository.team;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.domain.entity.team.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

	Optional<Team> findByCode(String code);

	Optional<Team> findByName(String name);

}
