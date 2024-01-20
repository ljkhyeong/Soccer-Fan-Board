package jkproject.soccer.user.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jkproject.soccer.common.exception.enums.ValidationMessage;
import jkproject.soccer.user.data.entity.User;
import jkproject.soccer.user.data.enums.UserRole;
import lombok.Data;

@Data
public class UserCreateRequestDto {

	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	@Pattern(regexp = "(^$|^[a-zA-Z0-9]{4,20}$)", message = ValidationMessage.Messages.LOGINID_REQUIREMENTS)
	private String loginId;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	@Pattern(regexp = "(^$|(?=.*[0-9])(?=.*[a-zA-z])(?=.*\\W)(?=\\S+$).{8,16})", message = ValidationMessage.Messages.PASSWORD_REQUIREMENTS)
	private String password;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	@Pattern(regexp = "(^$|^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$)", message = ValidationMessage.Messages.NICKNAME_REQUIREMENTS)
	private String nickname;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	@Pattern(regexp = "(^$|[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6})", message = ValidationMessage.Messages.EMAIL_REQUIREMENTS)
	private String email;
	@NotBlank(message = ValidationMessage.Messages.NOT_NULL)
	@Pattern(regexp = "(^$|^\\+?\\d{2,3}[-\\.]?\\d{3,4}[-\\.]?\\d{4}$)", message = ValidationMessage.Messages.PHONENUMBER_REQUIREMENTS)
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
