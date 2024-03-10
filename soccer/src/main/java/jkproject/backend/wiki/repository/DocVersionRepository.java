package jkproject.backend.wiki.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jkproject.backend.wiki.data.entity.DocVersion;
import jkproject.backend.wiki.data.entity.WikiDoc;

public interface DocVersionRepository extends JpaRepository<DocVersion, Long> {
	@Query("SELECT dv FROM DocVersion dv WHERE dv.wikiDoc = :wikiDoc "
		+ "AND dv.version = (SELECT MAX(dv2.version) FROM DocVersion dv2 WHERE dv2.wikiDoc = :wikiDoc)")
	Optional<DocVersion> findTopDocByWikiDoc(WikiDoc wikiDoc);

	@Query("SELECT MAX(dv.version) FROM DocVersion dv WHERE dv.wikiDoc = :wikiDoc")
	Optional<Integer> findTopVersionByWikiDoc(WikiDoc wikiDoc);

	Optional<DocVersion> findByWikiDocAndVersion(WikiDoc wikiDoc, Integer version);

	@Query("SELECT dv FROM DocVersion dv WHERE dv.wikiDoc.wikiDocId = :wikiDocId")
	Page<DocVersion> findAllByWikiDocId(Long wikiDocId, Pageable pageable);
}
