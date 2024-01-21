package jkproject.soccer.board.data.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.board.data.entity.comment.Comment;
import jkproject.soccer.board.data.entity.post.Post;
import jkproject.soccer.common.exception.enums.ValidationMessage;
import jkproject.soccer.user.data.entity.User;
import lombok.Data;

@Data
public class CommentCreateRequestDto {

	private Long parentId;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String comment;

	public Comment toEntity(Comment parent, User user, Post post) {
		Comment.CommentBuilder builder = Comment.builder()
			.commenter(user.getNickname())
			.comment(comment)
			.user(user)
			.post(post);

		if (parent != null) {
			builder.parent(parent);
		}

		Comment comment = builder.build();
		post.getComments().add(comment);
		return comment;
	}

}
