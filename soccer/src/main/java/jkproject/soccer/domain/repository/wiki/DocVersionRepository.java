package jkproject.soccer.domain.repository.wiki;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jkproject.soccer.domain.entity.wiki.DocVersion;
import jkproject.soccer.domain.entity.wiki.WikiDoc;

public interface DocVersionRepository extends JpaRepository<DocVersion, Long> {
	@Query("SELECT dv FROM DocVersion dv WHERE dv.wikiDoc = :wikiDoc "
		+ "AND dv.version = (SELECT MAX(dv2.version) FROM DocVersion dv2 WHERE dv2.wikiDoc = :wikiDoc)")
	Optional<DocVersion> findTopDocByWikiDoc(WikiDoc wikiDoc);

	@Query("SELECT MAX(dv.version) FROM DocVersion dv WHERE dv.wikiDoc = :wikiDoc")
	Optional<Integer> findTopVersionByWikiDoc(WikiDoc wikiDoc);
}
