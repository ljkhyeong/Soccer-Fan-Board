package jkproject.soccer.board.data.dto.post.response;

import java.time.LocalDateTime;

import jkproject.soccer.board.data.entity.post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListResponseDto {
	private Long postId;
	private String title;
	private String writer;
	private Long viewCount;
	private LocalDateTime createdAt;

	public static PostListResponseDto from(Post post) {
		return PostListResponseDto.builder()
			.postId(post.getPostId())
			.title(post.getTitle())
			.writer(post.getWriter())
			.viewCount(post.getViewCount())
			.createdAt(post.getCreatedAt())
			.build();
	}

}
