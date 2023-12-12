package jkproject.soccer.api.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.web.common.exception.enums.ValidationMessage;
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

	public User toEntity() {
		return User.builder()
			.loginId(loginId)
			.password(password)
			.nickname(nickname)
			.email(email)
			.phoneNumber(phoneNumber)
			.build();
	}

}
