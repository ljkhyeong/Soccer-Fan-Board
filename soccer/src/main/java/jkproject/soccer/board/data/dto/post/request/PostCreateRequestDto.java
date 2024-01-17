package jkproject.soccer.board.data.dto.post.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.common.exception.enums.ValidationMessage;
import jkproject.soccer.team.data.entity.Team;
import jkproject.soccer.user.data.entity.User;
import lombok.Data;

@Data
public class PostCreateRequestDto {
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String title;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String content;

	public Post toEntity(Team team, User user) {
		return Post.builder()
			.title(title)
			.content(content)
			.writer(user.getNickname())
			.team(team)
			.user(user)
			.viewCount(0L)
			.heartCount(0L)
			.build();
	}
}
