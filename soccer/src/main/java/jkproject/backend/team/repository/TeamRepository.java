package jkproject.backend.team.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.backend.team.data.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

	Optional<Team> findByCode(String code);

	Optional<Team> findByName(String name);

}
