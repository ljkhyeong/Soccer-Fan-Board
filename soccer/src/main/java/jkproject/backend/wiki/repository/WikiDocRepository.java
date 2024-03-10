package jkproject.backend.wiki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.backend.team.data.entity.Team;
import jkproject.backend.wiki.data.entity.WikiDoc;

public interface WikiDocRepository extends JpaRepository<WikiDoc, Long> {
	Optional<WikiDoc> findByTeam(Team team);
}
