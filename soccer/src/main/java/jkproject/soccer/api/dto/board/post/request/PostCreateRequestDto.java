package jkproject.soccer.api.dto.board.post.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.domain.entity.board.post.Post;
import jkproject.soccer.domain.entity.user.User;
import lombok.Data;

@Data
public class PostCreateRequestDto {
	@NotBlank
	private String title;
	@NotBlank
	private String content;

	public Post toEntity(User user) {
		return Post.builder()
			.title(title)
			.content(content)
			.writer(user.getNickname())
			.user(user)
			.build();
	}
}
