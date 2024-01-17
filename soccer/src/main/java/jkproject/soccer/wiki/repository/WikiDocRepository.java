package jkproject.soccer.wiki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.team.data.entity.Team;
import jkproject.soccer.wiki.data.entity.WikiDoc;

public interface WikiDocRepository extends JpaRepository<WikiDoc, Long> {
	Optional<WikiDoc> findByTeam(Team team);
}
