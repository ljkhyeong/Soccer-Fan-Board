package jkproject.soccer.wiki.data.dto.response;

import java.time.LocalDateTime;

import jkproject.soccer.wiki.data.entity.DocVersion;
import jkproject.soccer.wiki.data.entity.WikiDoc;
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
