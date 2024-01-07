package jkproject.soccer.api.dto.wiki.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.domain.entity.team.Team;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.domain.entity.wiki.DocVersion;
import jkproject.soccer.domain.entity.wiki.WikiDoc;
import jkproject.soccer.web.common.exception.enums.ValidationMessage;
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
		return DocVersion.builder()
			.body(body)
			.version(version)
			.writer(user.getNickname())
			.wikiDoc(wikiDoc)
			.user(user)
			.build();
	}

}
