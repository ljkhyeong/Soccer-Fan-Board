package jkproject.backend.board.data.dto.comment.response;

import java.time.LocalDateTime;

import jkproject.backend.board.data.entity.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentListResponseDto {

	private Long commentId;
	private String commenter;
	private String comment;
	private boolean isReply;
	private boolean isRemoved;
	private LocalDateTime createdAt;
	private boolean nonUserComment;

	public static CommentListResponseDto from(Comment comment) {

		return CommentListResponseDto.builder()
			.commentId(comment.getCommentId())
			.commenter(comment.getCommenter())
			.comment(comment.isRemoved() ? "삭제된 댓글입니다." : comment.getComment())
			.isReply(comment.isReply())
			.isRemoved(comment.isRemoved())
			.createdAt(comment.getCreatedAt())
			.nonUserComment(comment.getCommenter().contains("(") ? true : false)
			.build();

	}

}
