package jkproject.soccer.api.dto.board.post.response;

import java.time.LocalDateTime;

import jkproject.soccer.domain.entity.board.post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListResponseDto {
	private Long postId;
	private String title;
	private String writer;
	private LocalDateTime createdAt;

	public static PostListResponseDto from(Post post) {
		return PostListResponseDto.builder()
			.postId(post.getPostId())
			.title(post.getTitle())
			.writer(post.getWriter())
			.createdAt(post.getCreatedAt())
			.build();
	}
	
}
