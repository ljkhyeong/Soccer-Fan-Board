package jkproject.soccer.api.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.web.common.exception.enums.ValidationMessage;
import lombok.Data;

@Data
public class UserUpdateRequestDto {

	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String password;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String nickname;
}
