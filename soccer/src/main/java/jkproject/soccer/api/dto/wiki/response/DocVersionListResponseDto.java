package jkproject.soccer.api.dto.wiki.response;

import java.time.LocalDateTime;

import jkproject.soccer.domain.entity.wiki.DocVersion;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocVersionListResponseDto {
	private Long docVersionId;
	private String writer;
	private Integer version;
	private LocalDateTime createdAt;

	public static DocVersionListResponseDto from(DocVersion docVersion) {
		return DocVersionListResponseDto.builder()
			.docVersionId(docVersion.getDocVersionId())
			.writer(docVersion.getWriter())
			.version(docVersion.getVersion())
			.createdAt(docVersion.getCreatedAt())
			.build();
	}
	
}
