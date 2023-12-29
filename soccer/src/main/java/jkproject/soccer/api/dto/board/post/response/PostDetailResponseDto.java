package jkproject.soccer.api.dto.board.post.response;

import java.time.LocalDateTime;

import jkproject.soccer.domain.entity.board.post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailResponseDto {
	private Long postId;
	private String title;
	private String content;
	private String writer;
	private Long viewCount;
	private LocalDateTime createdAt;

	public static PostDetailResponseDto from(Post post) {
		return PostDetailResponseDto.builder()
			.postId(post.getPostId())
			.title(post.getTitle())
			.content(post.getContent())
			.writer(post.getWriter())
			.viewCount(post.getViewCount())
			.createdAt(post.getCreatedAt())
			.build();
	}
}
