package jkproject.soccer.api.dto.board.comment.response;

import jkproject.soccer.domain.entity.board.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentListResponseDto {

	private String commenter;
	private String comment;

	public static CommentListResponseDto from(Comment comment) {
		return CommentListResponseDto.builder()
			.commenter(comment.getCommenter())
			.comment(comment.getComment())
			.build();
	}
}
