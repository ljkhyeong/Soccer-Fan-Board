package jkproject.backend.board.data.dto.post.response;

import java.time.LocalDateTime;

import jkproject.backend.board.data.entity.post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListResponseDto {
	private Long postId;
	private String title;
	private String writer;
	private Long viewCount;
	private Long heartCount;
	private LocalDateTime createdAt;

	public static PostListResponseDto from(Post post) {
		return PostListResponseDto.builder()
			.postId(post.getPostId())
			.title(post.getTitle())
			.writer(post.getWriter())
			.viewCount(post.getViewCount())
			.heartCount(post.getHeartCount())
			.createdAt(post.getCreatedAt())
			.build();
	}

}
