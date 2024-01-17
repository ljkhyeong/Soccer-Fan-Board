package jkproject.soccer.user.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.user.data.enums.UserRole;
import jkproject.soccer.user.data.entity.User;
import jkproject.soccer.common.exception.enums.ValidationMessage;
import lombok.Data;

@Data
public class UserCreateRequestDto {

	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String loginId;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String password;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String nickname;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String email;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	private String phoneNumber;
	private UserRole role;

	public User toEntity() {
		return User.builder()
			.loginId(loginId)
			.password(password)
			.nickname(nickname)
			.email(email)
			.phoneNumber(phoneNumber)
			.role(role)
			.build();
	}

}
