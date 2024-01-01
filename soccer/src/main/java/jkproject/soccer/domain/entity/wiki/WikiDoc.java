package jkproject.soccer.domain.entity.wiki;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "WIKI_DOC_TB")
@NoArgsConstructor
@Getter
public class WikiDoc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wiki_doc_id")
	private Long wikiDocId;
	@Column(nullable = false)
	private String title;

	@OneToMany(mappedBy = "wikiDoc")
	private List<DocVersion> docVersions = new ArrayList<>();

	@Builder
	public WikiDoc(String title) {
		this.title = title;
	}
}
