package jkproject.backend.wiki.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jkproject.backend.common.data.entity.CreationTimeEntity;
import jkproject.backend.user.data.entity.User;
import lombok.Builder;
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
	@Column(nullable = false, columnDefinition = "TEXT")
	private String body;
	@Column(nullable = false)
	private Integer version;
	@Column(nullable = false)
	private String writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wiki_doc_id")
	private WikiDoc wikiDoc;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public DocVersion(String body, Integer version, String writer, WikiDoc wikiDoc, User user) {
		this.body = body;
		this.version = version;
		this.writer = writer;
		this.wikiDoc = wikiDoc;
		this.user = user;
	}
}
