package jkproject.soccer.api.dto.user.request;


import jakarta.validation.constraints.NotBlank;
import jkproject.soccer.domain.entity.user.User;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import lombok.Data;


@Data
public class UserCreateRequestDto {

	@NotBlank
	private String loginId;
	@NotBlank
	private String password;
	@NotBlank
	private String nickname;
	@NotBlank
	private String email;
	@NotBlank
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
