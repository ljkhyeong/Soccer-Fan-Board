package jkproject.soccer.wiki.data.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jkproject.soccer.team.data.entity.Team;
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
	@Column(nullable = false, unique = true)
	private String title;

	@OneToOne
	@JoinColumn(name = "team_id")
	private Team team;
	@OneToMany(mappedBy = "wikiDoc", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<DocVersion> docVersions = new ArrayList<>();

	@Builder
	public WikiDoc(String title, Team team) {
		this.title = title;
		this.team = team;
	}
}
