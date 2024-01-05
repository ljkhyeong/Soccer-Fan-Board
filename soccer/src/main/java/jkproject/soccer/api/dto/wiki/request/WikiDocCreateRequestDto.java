package jkproject.soccer.api.dto.wiki.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.domain.entity.wiki.DocVersion;
import jkproject.soccer.domain.entity.wiki.WikiDoc;
import jkproject.soccer.web.common.exception.enums.ValidationMessage;

public class WikiDocCreateRequestDto {

	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	String body;

	public DocVersion toEntity(WikiDoc wikiDoc, Integer version, User user) {
		return DocVersion.builder()
			.body(body)
			.version(version)
			.wikiDoc(wikiDoc)
			.user(user)
			.build();
	}

}
