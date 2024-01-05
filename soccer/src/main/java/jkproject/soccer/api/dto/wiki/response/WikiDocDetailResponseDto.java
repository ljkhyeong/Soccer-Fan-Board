package jkproject.soccer.api.dto.wiki.response;

import java.time.LocalDateTime;

import jkproject.soccer.domain.entity.wiki.DocVersion;
import jkproject.soccer.domain.entity.wiki.WikiDoc;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WikiDocDetailResponseDto {

	private Long wikiDocId;
	private String title;
	private String body;
	private Integer version;
	private LocalDateTime createdAt;

	public static WikiDocDetailResponseDto from(WikiDoc wikiDoc, DocVersion docVersion) {
		return WikiDocDetailResponseDto.builder()
			.wikiDocId(wikiDoc.getWikiDocId())
			.title(wikiDoc.getTitle())
			.body(docVersion.getBody())
			.version(docVersion.getVersion())
			.createdAt(docVersion.getCreatedAt())
			.build();
	}
}
