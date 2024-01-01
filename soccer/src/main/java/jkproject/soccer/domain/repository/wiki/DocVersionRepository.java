package jkproject.soccer.domain.repository.wiki;

import org.springframework.data.jpa.repository.JpaRepository;

import jkproject.soccer.domain.entity.wiki.DocVersion;

public interface DocVersionRepository extends JpaRepository<DocVersion, Long> {
}
