package jkproject.backend.board.data.dto.comment.request;

import lombok.Data;

@Data
public class CommentDeleteRequestDto {
	private boolean nonUserComment;
	private String password;
}
