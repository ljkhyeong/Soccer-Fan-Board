package jkproject.soccer.board.data.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.board.data.entity.comment.Comment;
import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.user.data.entity.User;
import jkproject.soccer.common.exception.enums.ValidationMessage;
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
