package jkproject.soccer.api.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.web.common.exception.enums.ValidationMessage;
import lombok.Data;

@Data
public class LoginRequestDto {
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String id;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String password;
}
