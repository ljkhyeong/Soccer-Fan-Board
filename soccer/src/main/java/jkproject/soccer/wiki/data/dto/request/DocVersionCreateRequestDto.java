package jkproject.soccer.wiki.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.common.exception.enums.ValidationMessage;
import jkproject.soccer.team.data.entity.Team;
import jkproject.soccer.user.data.entity.User;
import jkproject.soccer.wiki.data.entity.DocVersion;
import jkproject.soccer.wiki.data.entity.WikiDoc;
import lombok.Data;

@Data
public class DocVersionCreateRequestDto {

	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	String title;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	String body;

	public WikiDoc toWikiDocEntity(Team team) {
		return WikiDoc.builder()
			.title(title)
			.team(team)
			.build();
	}

	public DocVersion toDocVersionEntity(WikiDoc wikiDoc, Integer version, User user) {
		DocVersion docVersion = DocVersion.builder()
			.body(body)
			.version(version)
			.writer(user.getNickname())
			.wikiDoc(wikiDoc)
			.user(user)
			.build();

		wikiDoc.getDocVersions().add(docVersion);
		return docVersion;
	}

}
