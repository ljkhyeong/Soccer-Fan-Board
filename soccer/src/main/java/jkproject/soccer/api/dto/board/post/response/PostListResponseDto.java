package jkproject.soccer.api.dto.board.post.response;

import jkproject.soccer.domain.entity.board.post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListResponseDto {
	Long postId;
	String title;

	public static PostListResponseDto from(Post post) {
		return PostListResponseDto.builder()
			.postId(post.getPostId())
			.title(post.getTitle())
			.build();
	}
}
