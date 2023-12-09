package jkproject.soccer.api.dto.board.comment.request;

import jkproject.soccer.domain.entity.board.comment.Comment;
import jkproject.soccer.domain.entity.board.post.Post;
import jkproject.soccer.domain.entity.user.User;
import lombok.Data;

@Data
public class CommentCreateRequestDto {
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
