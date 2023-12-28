package jkproject.soccer.api.dto.board.comment.response;

import java.time.LocalDateTime;

import jkproject.soccer.domain.entity.board.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentListResponseDto {

	private Long commentId;
	private String commenter;
	private String comment;
	private LocalDateTime createdAt;

	public static CommentListResponseDto from(Comment comment) {
		return CommentListResponseDto.builder()
			.commentId(comment.getCommentId())
			.commenter(comment.getCommenter())
			.comment(comment.getComment())
			.createdAt(comment.getCreatedAt())
			.build();
	}
}
