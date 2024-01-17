package jkproject.soccer.user.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.common.exception.enums.ValidationMessage;
import lombok.Data;

@Data
public class UserUpdateRequestDto {

	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String password;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String nickname;
}
