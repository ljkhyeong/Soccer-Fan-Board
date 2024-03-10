package jkproject.backend.user.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.backend.common.exception.enums.ValidationMessage;
import lombok.Data;

@Data
public class UserUpdateRequestDto {

	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String type;
	private String currentPassword;
	private String newPassword;
	private String confirmNewPassword;
	private String nickname;
}
