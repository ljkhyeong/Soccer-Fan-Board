package jkproject.backend.user.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jkproject.backend.common.exception.enums.ValidationMessage;
import lombok.Data;

@Data
public class UserFindPasswordRequestDto {
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	@Email
	private String email;
}
