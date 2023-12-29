package jkproject.soccer.api.dto.board.post.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.domain.entity.board.post.Post;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.web.common.exception.enums.ValidationMessage;
import lombok.Data;

@Data
public class PostCreateRequestDto {
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String title;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String content;

	public Post toEntity(User user) {
		return Post.builder()
			.title(title)
			.content(content)
			.writer(user.getNickname())
			.user(user)
			.viewCount(0L)
			.build();
	}
}
