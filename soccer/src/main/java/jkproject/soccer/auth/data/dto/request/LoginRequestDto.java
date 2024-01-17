package jkproject.soccer.auth.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.common.exception.enums.ValidationMessage;
import lombok.Data;

@Data
public class LoginRequestDto {
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String loginId;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String password;
}
