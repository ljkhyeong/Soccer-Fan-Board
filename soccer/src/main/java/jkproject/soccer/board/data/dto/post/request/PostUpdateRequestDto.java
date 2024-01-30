package jkproject.soccer.board.data.dto.post.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.common.exception.enums.ValidationMessage;
import lombok.Data;

@Data
public class PostUpdateRequestDto {
	private boolean nonUserPost;
	private String password;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String title;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String content;

}
