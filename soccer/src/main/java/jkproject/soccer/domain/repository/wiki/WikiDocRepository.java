package jkproject.soccer.domain.repository.wiki;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.domain.entity.wiki.WikiDoc;

public interface WikiDocRepository extends JpaRepository<WikiDoc, Long> {
	Optional<WikiDoc> findByTeamId(Long teamId);
}
