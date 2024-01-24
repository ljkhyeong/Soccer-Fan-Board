package jkproject.soccer.board.data.dto.comment.request;

import org.springframework.lang.Nullable;

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
	private String tempNickname;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String comment;

	public Comment toEntity(@Nullable Comment parent, @Nullable User user, Post post, String ipAddress) {
		String commenter = (user != null) ? user.getNickname() :
			(tempNickname + ("(" + ipAddress.split(":")[0] + "." + ipAddress.split(":")[1]) + ")");

		Comment comment = Comment.builder()
			.commenter(commenter)
			.comment(this.comment)
			.ipAddress(ipAddress)
			.user(user)
			.post(post)
			.parent(parent)
			.build();

		post.getComments().add(comment);
		return comment;
	}

}
