package jkproject.soccer.api.dto.board.comment.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.domain.entity.board.comment.Comment;
import jkproject.soccer.domain.entity.board.post.Post;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.web.common.exception.enums.ValidationMessage;
import lombok.Data;

@Data
public class CommentCreateRequestDto {
	
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String comment;

	public Comment toEntity(User user, Post post) {
		return Comment.builder()
			.commenter(user.getNickname())
			.comment(comment)
			.user(user)
			.post(post)
			.build();
	}
}
