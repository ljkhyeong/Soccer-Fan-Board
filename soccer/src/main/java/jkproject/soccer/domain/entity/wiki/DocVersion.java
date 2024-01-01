package jkproject.soccer.domain.entity.wiki;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jkproject.soccer.domain.entity.CreationTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "DOC_VERSION_TB")
@Getter
public class DocVersion extends CreationTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_version_id")
	private Long docVersionId;
	private String body;
	private Integer version;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wiki_doc_id")
	private WikiDoc wikiDoc;
}
