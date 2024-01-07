package jkproject.soccer.api.dto.wiki.response;

import java.time.LocalDateTime;

import jkproject.soccer.domain.entity.wiki.DocVersion;
import jkproject.soccer.domain.entity.wiki.WikiDoc;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocVersionDetailResponseDto {

	private Long wikiDocId;
	private String title;
	private String body;
	private Integer version;
	private String writer;
	private LocalDateTime createdAt;

	public static DocVersionDetailResponseDto from(WikiDoc wikiDoc, DocVersion docVersion) {
		return DocVersionDetailResponseDto.builder()
			.wikiDocId(wikiDoc.getWikiDocId())
			.title(wikiDoc.getTitle())
			.body(docVersion.getBody())
			.version(docVersion.getVersion())
			.writer(docVersion.getWriter())
			.createdAt(docVersion.getCreatedAt())
			.build();
	}
}
