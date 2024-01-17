package jkproject.soccer.team.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.team.data.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

	Optional<Team> findByCode(String code);

	Optional<Team> findByName(String name);

}
