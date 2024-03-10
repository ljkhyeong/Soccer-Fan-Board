package jkproject.backend.board.data.dto.comment.request;

import org.springframework.lang.Nullable;

import jakarta.validation.constraints.NotBlank;
import jkproject.backend.board.data.entity.comment.Comment;
import jkproject.backend.board.data.entity.post.Post;
import jkproject.backend.common.exception.enums.ValidationMessage;
import jkproject.backend.user.data.entity.User;
import lombok.Data;

@Data
public class CommentCreateRequestDto {

	private boolean loginState;
	private Long parentId;
	private String tempNickname;
	private String password;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String comment;

	public Comment toEntity(@Nullable Comment parent, @Nullable User user, Post post, String ipAddress) {
		// TODO 로컬테스트 환경에서는 IPv6 반환 -> IPv4로 코드 수정 필요
		String commenter = (user != null) ? user.getNickname() :
			(tempNickname + ("(" + ipAddress.split("\\.")[0] + ipAddress.split("\\.")[1]) + ")");

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
